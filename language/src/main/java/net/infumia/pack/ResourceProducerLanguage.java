package net.infumia.pack;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * Represents an interface for a glyph collection that supports language-specific glyphs.
 */
public interface ResourceProducerLanguage extends ResourceProducer {
    /**
     * Translates a character to a GlyphAppendable with specified height, ascent, and color.
     *
     * @param height    The height of the glyph.
     * @param ascent    The ascent of the glyph.
     * @param character The character to translate.
     * @param color     The color of the glyph.
     * @return A GlyphAppendable representing the translated character.
     * @throws IllegalArgumentException If translation fails.
     */
    GlyphAppendable translate(int height, int ascent, char character, TextColor color)
        throws IllegalArgumentException;

    /**
     * Translates a string of text to a list of GlyphAppendable with specified height, ascent, and color.
     *
     * @param height The height of the glyphs.
     * @param ascent The ascent of the glyphs.
     * @param text   The text to translate.
     * @param color  The color of the glyphs.
     * @return A list of GlyphAppendable representing the translated text.
     * @throws IllegalArgumentException If translation fails.
     */
    List<GlyphAppendable> translate(int height, int ascent, String text, TextColor color)
        throws IllegalArgumentException;

    /**
     * Translates a Component to a list of GlyphAppendable with specified height and ascent.
     *
     * @param height    The height of the glyphs.
     * @param ascent    The ascent of the glyphs.
     * @param component The component to translate.
     * @return A list of GlyphAppendable representing the translated component.
     * @throws IllegalArgumentException If translation fails.
     */
    List<GlyphAppendable> translate(int height, int ascent, Component component)
        throws IllegalArgumentException;

    /**
     * Translates a character to a GlyphAppendable with specified height and ascent, without color.
     *
     * @param height    The height of the glyph.
     * @param ascent    The ascent of the glyph.
     * @param character The character to translate.
     * @return A GlyphAppendable representing the translated character.
     * @throws IllegalArgumentException If translation fails.
     */
    default GlyphAppendable translate(final int height, final int ascent, final char character)
        throws IllegalArgumentException {
        return this.translate(height, ascent, character, null);
    }

    /**
     * Translates a string of text to a list of GlyphAppendable with specified height and ascent, without color.
     *
     * @param height The height of the glyphs.
     * @param ascent The ascent of the glyphs.
     * @param text   The text to translate.
     * @return A list of GlyphAppendable representing the translated text.
     * @throws IllegalArgumentException If translation fails.
     */
    default List<GlyphAppendable> translate(final int height, final int ascent, final String text)
        throws IllegalArgumentException {
        return this.translate(height, ascent, text, null);
    }
}
