package net.infumia.pack;

import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an interface for a glyph collection that supports multi-character glyphs.
 */
public interface ResourceProducerImageMultichar extends ResourceProducer {
    /**
     * Translates a character to a GlyphAppendable with the specified color.
     *
     * @param character The character to translate.
     * @param color     The color of the glyph.
     * @return A GlyphAppendable representing the translated character.
     * @throws IllegalArgumentException If translation fails.
     */
    GlyphAppendable translate(char character, TextColor color) throws IllegalArgumentException;

    /**
     * Translates a string of text to a list of GlyphAppendable with the specified color.
     *
     * @param text  The text to translate.
     * @param color The color of the glyphs.
     * @return A list of GlyphAppendable representing the translated text.
     * @throws IllegalArgumentException If translation fails.
     */
    default List<GlyphAppendable> translate(final String text, final TextColor color) throws IllegalArgumentException {
        final List<GlyphAppendable> glyphs = new ArrayList<>();
        for (final char character : text.toCharArray()) {
            if (character == ' ') {
                glyphs.add(Glyphs.space());
            } else {
                glyphs.add(this.translate(character, color));
            }
        }
        return glyphs;
    }

    /**
     * Translates a character to a GlyphAppendable without specifying a color.
     *
     * @param character The character to translate.
     * @return A GlyphAppendable representing the translated character.
     * @throws IllegalArgumentException If translation fails.
     */
    default GlyphAppendable translate(final char character) throws IllegalArgumentException {
        return this.translate(character, null);
    }

    /**
     * Translates a string of text to a list of GlyphAppendable without specifying a color.
     *
     * @param text The text to translate.
     * @return A list of GlyphAppendable representing the translated text.
     * @throws IllegalArgumentException If translation fails.
     */
    default List<GlyphAppendable> translate(final String text) throws IllegalArgumentException {
        return this.translate(text, null);
    }
}
