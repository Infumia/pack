package net.infumia.pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.StringJoiner;
import java.util.function.Predicate;
import net.kyori.adventure.text.serializer.ComponentSerializer;

/**
 * Settings for reading a pack.
 */
public final class PackReaderSettings {

    private final EntryProvider entryProvider;
    private final PackKeyExtractorReferencePart keyExtractor;
    private final String packReferenceMetaFileName;
    private final ObjectMapper mapper;
    private final Predicate<Entry> readFilter;
    private final ComponentSerializer<?, ?, String> serializer;

    /**
     * Ctor.
     *
     * @param entryProvider             the entry provider. Cannot be null.
     * @param keyExtractor              the pack key extractor. Cannot be null.
     * @param packReferenceMetaFileName the pack reference meta file name. Cannot be null.
     * @param mapper                    the object mapper to read pack and pack part reference files. Cannot be null.
     * @param readFilter                the read filter to filter entries {@link EntryProvider#provideAll(Predicate)}.
     * @param serializer                the serializer to serialize components when needed. Cannot be null.
     * @see PackReadFilters
     */
    public PackReaderSettings(
        final EntryProvider entryProvider,
        final PackKeyExtractorReferencePart keyExtractor,
        final String packReferenceMetaFileName,
        final ObjectMapper mapper,
        final Predicate<Entry> readFilter,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this.entryProvider = entryProvider;
        this.keyExtractor = keyExtractor;
        this.packReferenceMetaFileName = packReferenceMetaFileName;
        this.mapper = mapper;
        this.readFilter = readFilter;
        this.serializer = serializer;
    }

    /**
     * Returns the entry provider.
     *
     * @return the entry provider.
     */
    public EntryProvider entryProvider() {
        return this.entryProvider;
    }

    /**
     * Returns the pack key extractor.
     *
     * @return the pack key extractor.
     */
    public PackKeyExtractorReferencePart keyExtractor() {
        return this.keyExtractor;
    }

    /**
     * Returns the pack reference meta file name.
     *
     * @return the pack reference meta file name.
     */
    public String packReferenceMetaFileName() {
        return this.packReferenceMetaFileName;
    }

    /**
     * Returns the object mapper.
     *
     * @return the object mapper.
     */
    public ObjectMapper mapper() {
        return this.mapper;
    }

    /**
     * Returns the read filter.
     *
     * @return the read filter.
     */
    public Predicate<Entry> readFilter() {
        return this.readFilter;
    }

    /**
     * Returns the component serializer.
     *
     * @return the component serializer.
     */
    public ComponentSerializer<?, ?, String> serializer() {
        return this.serializer;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReaderSettings.class.getSimpleName() + "[", "]")
            .add("inputStreamProvider=" + this.entryProvider)
            .add("packReferenceMetaFileName=" + this.packReferenceMetaFileName)
            .add("mapper=" + this.mapper)
            .add("readFilter=" + this.readFilter)
            .add("serializer=" + this.serializer)
            .toString();
    }
}
