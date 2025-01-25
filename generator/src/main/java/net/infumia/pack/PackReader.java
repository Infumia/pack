package net.infumia.pack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import team.unnamed.creative.ResourcePack;

final class PackReader {

    private static final Predicate<Entry> IS_REGULAR_FILE = Entry::isRegularFile;

    private final PackReaderSettings settings;
    private final Pack base;

    PackReader(final PackReaderSettings settings, final Pack base) {
        this.settings = settings;
        this.base = base;
    }

    PackGeneratorContext read() throws IOException {
        final ObjectMapper mapper = this.settings.mapper();
        final InputStreamProvider inputStreamProvider = this.settings.inputStreamProvider();
        final String packReferenceMetaFileName = this.settings.packReferenceMetaFileName();

        final PackReferenceMeta packReferenceMeta;
        try (
            final InputStream stream = inputStreamProvider.provideFileStream(
                packReferenceMetaFileName
            )
        ) {
            packReferenceMeta = mapper.readValue(stream, Internal.PACK_META_TYPE);
        }

        inputStreamProvider.provideAll(
            PackReader.IS_REGULAR_FILE.and(this.settings.readFilter()).and(
                entry -> !entry.is(packReferenceMetaFileName)
            )
        );

        return new PackGeneratorContext(
            ResourcePack.resourcePack(),
            this.base,
            packReferenceMeta,
            packReferenceParts,
            this.settings.serializer()
        );
    }

    private PackGeneratorContext read0(final Stream<Path> walking) {
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
                    final List<PackReferencePart> read = iterator.readAll();
                    if (path.equals(this.settings.root())) {
                        return read;
                    } else {
                        return read
                            .stream()
                            .map(part -> part.directory(path))
                            .collect(Collectors.toList());
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private static final class Internal {

        private static final TypeReference<PackReferenceMeta> PACK_META_TYPE = new TypeReference<
            PackReferenceMeta
        >() {};
        private static final TypeReference<PackReferencePart> PACK_PART_TYPE = new TypeReference<
            PackReferencePart
        >() {};
    }
}
