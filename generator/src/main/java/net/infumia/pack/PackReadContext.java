package net.infumia.pack;

import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the pack read context.
 */
public final class PackReadContext {

    private final AtomicInteger lastCustomModelData = new AtomicInteger();
    private final PackReferenceMeta packReference;
    private final Collection<PackReferencePart> packPartReferences;
    private final PackReaderSettings readerSettings;

    /**
     * Ctor.
     *
     * @param packReference      the pack file reference. Cannot be null.
     * @param packPartReferences the pack part references. Cannot be null.
     * @param readerSettings     the reader settings. Cannot be null.
     */
    PackReadContext(
        final PackReferenceMeta packReference,
        final Collection<PackReferencePart> packPartReferences,
        final PackReaderSettings readerSettings
    ) {
        this.packReference = packReference;
        this.packPartReferences = Collections.unmodifiableCollection(packPartReferences);
        this.readerSettings = readerSettings;
    }

    /**
     * Returns the pack reference.
     *
     * @return the pack reference.
     */
    public PackReferenceMeta packReference() {
        return this.packReference;
    }

    /**
     * Returns the pack part references.
     *
     * @return the pack part references.
     */
    public Collection<PackReferencePart> packPartReferences() {
        return this.packPartReferences;
    }

    /**
     * Returns the reader settings.
     *
     * @return the reader settings.
     */
    public PackReaderSettings readerSettings() {
        return this.readerSettings;
    }

    /**
     * Returns the last custom model data.
     *
     * @return the last custom model data.
     */
    public AtomicInteger lastCustomModelData() {
        return this.lastCustomModelData;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReadContext.class.getSimpleName() + "[", "]")
            .add("lastCustomModelData=" + this.lastCustomModelData)
            .add("packReference=" + this.packReference)
            .add("packPartReferences=" + this.packPartReferences)
            .add("readerSettings=" + this.readerSettings)
            .toString();
    }
}
