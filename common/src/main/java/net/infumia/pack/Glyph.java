package net.infumia.pack;

import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.text.Component;

/**
 * Represents a glyph that can be converted to an adventure component and provides width information.
 */
public interface Glyph {
    /**
     * Converts the glyph into an adventure component.
     *
     * @return The adventure component representing this glyph.
     * @throws ResourceNotProducedException if the glyph cannot be converted to an adventure component.
     */
    Component toAdventure() throws ResourceNotProducedException;

    /**
     * Returns the width of the glyph.
     *
     * @return The width of the glyph as an integer.
     */
    int width();
}
