package net.infumia.pack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
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

    PackGeneratorContext read() throws IOException {
        this.prepare();

        FileVisitOption[] visitOptions = this.settings.visitOptions();
        if (visitOptions == null) {
            visitOptions = new FileVisitOption[0];
        }
        try (final Stream<Path> walking = Files.walk(this.settings.root(), visitOptions)) {
            return this.read0(walking);
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
                            .collect(Collectors.toSet());
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        return new PackGeneratorContext(
            ResourcePack.resourcePack(),
            this.base,
            packReference,
            packPartReferences,
            this.settings.root(),
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
