package net.infumia.pack;

import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import team.unnamed.creative.texture.Texture;

/**
 * Represents an interface for a glyph image that can be appended and serves as a resource producer.
 */
public interface GlyphImage extends GlyphAppendable, ResourceProducer {
    /**
     * Retrieves the character associated with this glyph image.
     *
     * @return The character of the glyph image.
     * @throws ResourceNotProducedException If the glyph image is not produced.
     */
    char character() throws ResourceNotProducedException;

    /**
     * Retrieves the texture associated with this glyph image.
     *
     * @return The texture of the glyph image.
     */
    Texture texture();

    /**
     * Creates a colored version of this glyph image with the specified text color.
     *
     * @param color The text color for the colored glyph image. Cannot be null.
     * @return A colored GlyphImageColored instance.
     */
    default GlyphImageColored withColor(final TextColor color) {
        return new GlyphImageColoredImpl(this, color);
    }

    /**
     * Converts this glyph image to an Adventure Component.
     *
     * @return The Adventure Component representing this glyph image.
     * @throws ResourceNotProducedException If the glyph image is not produced.
     */
    default Component toAdventure() throws ResourceNotProducedException {
        return Component.text(this.character()).font(this.key());
    }
}
