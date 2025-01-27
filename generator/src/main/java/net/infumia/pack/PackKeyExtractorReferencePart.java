package net.infumia.pack;

import net.kyori.adventure.key.Key;

/**
 * Extracts the key from the given pack reference part.
 */
public interface PackKeyExtractorReferencePart {
    /**
     * Creates a new simple pack key extractor reference part.
     *
     * @return the created simple pack key extractor reference part.
     */
    static PackKeyExtractorReferencePart simple() {
        return PackKeyExtractorReferencePartImpl.INSTANCE;
    }

    /**
     * Extracts the key from the given pack reference part.
     *
     * @param context the pack read context. Cannot be null.
     * @param part    the pack reference part. Cannot be null.
     * @return the extracted key.
     */
    Key extract(PackReadContext context, PackReferencePart part);
}
