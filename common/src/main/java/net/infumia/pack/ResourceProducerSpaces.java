package net.infumia.pack;

import net.infumia.pack.exception.ResourceNotProducedException;

/**
 * Represents a resource producer that generates space glyphs.
 */
public interface ResourceProducerSpaces extends ResourceProducer {
    /**
     * Translates the specified length into a space glyph.
     *
     * @param length The length of the space glyph to produce.
     * @return The space glyph of the specified length.
     * @throws ResourceNotProducedException if the space glyph cannot be produced.
     */
    Glyph translate(int length) throws ResourceNotProducedException;
}
