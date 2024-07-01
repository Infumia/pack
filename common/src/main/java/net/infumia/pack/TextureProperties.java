package net.infumia.pack;

import java.util.Objects;

/**
 * Represents the properties of a texture, including its height and ascent.
 */
public final class TextureProperties {

    private final int height;
    private final int ascent;

    /**
     * Ctor.
     *
     * @param height The height of the texture.
     * @param ascent The ascent of the texture.
     */
    public TextureProperties(final int height, final int ascent) {
        this.height = height;
        this.ascent = ascent;
    }

    /**
     * Retrieves the height of the texture.
     *
     * @return The height of the texture.
     */
    public int height() {
        return this.height;
    }

    /**
     * Retrieves the ascent of the texture.
     *
     * @return The ascent of the texture.
     */
    public int ascent() {
        return this.ascent;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TextureProperties that = (TextureProperties) o;
        return this.height == that.height && this.ascent == that.ascent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.height, this.ascent);
    }

    @Override
    public String toString() {
        return (
            "TextureProperties[" + "height=" + this.height + ", " + "ascent=" + this.ascent + ']'
        );
    }
}
