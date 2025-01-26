package net.infumia.pack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
            final InputStream stream = inputStreamProvider.provide(
                packReferenceMetaFileName
            )
        ) {
            packReferenceMeta = mapper.readValue(stream, Internal.PACK_META_TYPE);
        }

        final List<Entry> parts = inputStreamProvider.provideAll(
            PackReader.IS_REGULAR_FILE.and(this.settings.readFilter()).and(
                entry -> !entry.is(packReferenceMetaFileName)
            )
        );

        final ObjectReader reader = mapper.readerFor(Internal.PACK_PART_TYPE);
        final List<PackReferencePart> packReferenceParts = parts
            .stream()
            .flatMap(entry -> {
                try (
                    final MappingIterator<PackReferencePart> iterator = reader.readValues(stream)
                ) {
                    return iterator.readAll().stream();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());

        return new PackGeneratorContext(
            ResourcePack.resourcePack(),
            this.base,
            packReferenceMeta,
            packReferenceParts,
            inputStreamProvider,
            this.settings.serializer()
        );
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
