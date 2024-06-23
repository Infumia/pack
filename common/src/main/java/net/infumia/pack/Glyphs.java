package net.infumia.pack;

/**
 * Utility class for working with glyphs.
 */
public final class Glyphs {

    /**
     * Returns an empty glyph instance.
     *
     * @return The empty glyph.
     */
    public static Glyph empty() {
        return GlyphEmpty.INSTANCE;
    }

    /**
     * Returns a space glyph instance.
     *
     * @return The space glyph.
     */
    public static GlyphAppendable space() {
        return GlyphSpaces.DEFAULT;
    }

    private Glyphs() {
        throw new IllegalStateException("Utility class");
    }
}
