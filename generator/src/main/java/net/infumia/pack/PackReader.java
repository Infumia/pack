package net.infumia.pack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

final class PackReader {

    private final PackReaderSettings settings;
    private final Pack base;

    private File packReferenceFile;
    private ObjectReader packReader;
    private ObjectReader packPartReader;

    PackReader(final PackReaderSettings settings, final Pack base) {
        this.settings = settings;
        this.base = base;
    }

    PackGeneratorContext read() throws IOException, URISyntaxException {
        this.prepare();

        FileVisitOption[] visitOptions = this.settings.visitOptions();
        if (visitOptions == null) {
            visitOptions = new FileVisitOption[0];
        }

        final ClassLoader classLoader = this.settings.classLoader();
        final String rootPathAsString = this.settings.rootPathAsString();
        final URL rootUrl = classLoader.getResource(rootPathAsString);

        if (rootUrl == null) {
            throw new IllegalStateException("Root path does not exist: " + rootPathAsString);
        }

        final String protocol = rootUrl.getProtocol();

        final Stream<Path> paths;
        if (protocol.equals("jar")) {
            final String jarPath = rootUrl.getPath().substring(5, rootUrl.getPath().indexOf("!"));
            try (final JarFile jarFile = new JarFile(jarPath)) {
                paths = jarFile
                    .stream()
                    .filter(entry -> entry.getName().startsWith(rootPathAsString))
                    .filter(entry -> !entry.isDirectory())
                    .map(entry -> Paths.get(entry.getName()))
                    .filter(Files::isRegularFile);
            }
        } else if (protocol.equals("file")) {
            final Path rootPath = Paths.get(rootUrl.toURI());
            paths = Files.walk(rootPath, this.settings.visitOptions());
        } else {
            throw new UnsupportedOperationException("Unsupported protocol: " + protocol);
        }

        try {
            return this.read0(paths);
        } finally {
            paths.close();
        }
    }

    private PackGeneratorContext read0(@NotNull final Stream<Path> walking) throws IOException {
        final PackReferenceMeta packReference = this.packReader.readValue(this.packReferenceFile);
        final Collection<PackReferencePart> packPartReferences = walking
            .filter(Files::isRegularFile)
            .filter(this.settings.readFilter())
            .map(Path::toFile)
            .filter(file -> !this.packReferenceFile.equals(file))
            .map(file -> {
                try (
                    final MappingIterator<PackReferencePart> iterator =
                        this.packPartReader.readValues(file)
                ) {
                    final Path path = file.getParentFile().toPath();
                    if (path.equals(this.settings.root())) {
                        return iterator.readAll();
                    } else {
                        return iterator
                            .readAll()
                            .stream()
                            .map(part -> part.directory(path))
                            .collect(Collectors.toList());
                    }
                } catch (final IOException e) {
                    throw new RuntimeException("Error processing file: " + file, e);
                }
            })
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return new PackGeneratorContext(
            ResourcePack.resourcePack(),
            this.base,
            packReference,
            packPartReferences,
            this.settings.classLoader(),
            this.settings.rootPathAsString(),
            this.settings.serializer()
        );
    }

    private void prepare() {
        final Path root = this.settings.root();
        final Path packReferenceFile = root.resolve(this.settings.packReferenceFileName());
        if (Files.notExists(packReferenceFile)) {
            throw new IllegalStateException(
                "Pack reference file does not exist: " + packReferenceFile
            );
        }
        this.packReferenceFile = packReferenceFile.toFile();

        final ObjectMapper mapper = this.settings.mapper();
        this.packReader = mapper.readerFor(Internal.PACK_TYPE);
        this.packPartReader = mapper.readerFor(Internal.PACK_PART_TYPE);
    }

    private static final class Internal {

        private static final TypeReference<PackReferenceMeta> PACK_TYPE = new TypeReference<
            PackReferenceMeta
        >() {};
        private static final TypeReference<PackReferencePart> PACK_PART_TYPE = new TypeReference<
            PackReferencePart
        >() {};
    }
}
