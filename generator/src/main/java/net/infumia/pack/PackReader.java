package net.infumia.pack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

final class PackReader {

    private static final Predicate<Entry> IS_REGULAR_FILE = Entry::isRegularFile;

    private final PackReaderSettings settings;

    PackReader(final PackReaderSettings settings) {
        this.settings = settings;
    }

    PackReadContext read() throws IOException {
        final ObjectMapper mapper = this.settings.mapper();
        final EntryProvider entryProvider = this.settings.entryProvider();
        final String packReferenceMetaFileName = this.settings.packReferenceMetaFileName();

        final Entry metaEntry = entryProvider.provide(packReferenceMetaFileName);
        final PackReferenceMeta packReferenceMeta;
        try (final InputStream metaStream = metaEntry.asInputStream()) {
            packReferenceMeta = mapper.readValue(metaStream, Internal.PACK_META_TYPE);
        }

        final Collection<Entry> parts = entryProvider.provideAll(
            PackReader.IS_REGULAR_FILE.and(entry -> !entry.is(packReferenceMetaFileName)).and(
                this.settings.readFilter()
            )
        );

        final ObjectReader reader = mapper.readerFor(Internal.PACK_PART_TYPE);
        final List<PackReferencePart> packReferenceParts = parts
            .stream()
            .flatMap(entry -> {
                try (
                    final InputStream stream = entry.asInputStream();
                    final MappingIterator<PackReferencePart> iterator = reader.readValues(stream)
                ) {
                    return iterator.readAll().stream().peek(part -> part.prepareCreator(entry));
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());

        return new PackReadContext(packReferenceMeta, packReferenceParts, this.settings);
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
