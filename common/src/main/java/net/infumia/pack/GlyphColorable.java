package net.infumia.pack;

import net.kyori.adventure.text.format.TextColor;

/**
 * Represents an interface for objects that can be colored with text color.
 */
public interface GlyphColorable {
    /**
     * Retrieves the current text color of the object.
     *
     * @return The current text color.
     */
    TextColor color();

    /**
     * Updates the text color of the object.
     *
     * @param color The new text color to set.
     */
    void updateColor(TextColor color);
}
