package net.infumia.pack;

/**
 * Represents an interface for a colored glyph image that can be appended and has color attributes.
 */
public interface GlyphImageColored extends GlyphAppendable, GlyphColorable {
    /**
     * Retrieves the original glyph image associated with this colored glyph image.
     *
     * @return The original glyph image.
     */
    GlyphImage original();
}
