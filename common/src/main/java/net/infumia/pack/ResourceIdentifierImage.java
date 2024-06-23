package net.infumia.pack;

/**
 * Represents a resource identifier specifically for {@link GlyphImage} instances.
 */
public interface ResourceIdentifierImage extends ResourceIdentifier<GlyphImage> {
    /**
     * Retrieves the type of resource this identifier represents, which is {@link GlyphImage}.
     *
     * @return The class object representing {@link GlyphImage}.
     */
    @Override
    default Class<GlyphImage> type() {
        return GlyphImage.class;
    }
}
