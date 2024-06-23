package net.infumia.pack;

import java.util.List;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

/**
 * Utility class for creating instances of {@link ResourceProducer}.
 */
public final class ResourceProducers {

    /**
     * Creates a space producer with specified font key, texture key, and writable.
     *
     * @param fontKey        The font key. Cannot be null.
     * @param textureKey     The texture key. Cannot be null.
     * @param spacesWritable The writable instance for spaces. Cannot be null.
     * @return A {@link ResourceProducerSpaces} instance.
     */
    public static ResourceProducerSpaces spaces(
        final Key fontKey,
        final Key textureKey,
        final Writable spacesWritable
    ) {
        return new ResourceProducerSpacesDefault(fontKey, textureKey, spacesWritable);
    }

    /**
     * Creates a ResourceProducerSpaces instance with default parameters.
     *
     * @return A ResourceProducerSpaces instance.
     */
    public static ResourceProducerSpaces spaces() {
        return ResourceProducers.spaces(
            Font.MINECRAFT_DEFAULT,
            Internal.DEFAULT_SPACES_TEXTURE_KEY,
            Internal.resourceFromJar("space.png")
        );
    }

    /**
     * Creates a Mojang-specific space producer with the specified key.
     *
     * @param fontKey The font key for the Mojang space producer. Cannot be null.
     * @return A {@link ResourceProducerSpaces} instance for Mojang.
     */
    public static ResourceProducerSpaces spacesMojang(final Key fontKey) {
        return new ResourceProducerSpacesMojang(fontKey);
    }

    /**
     * Creates a Mojang-specific space producer with default parameters.
     *
     * @return A {@link ResourceProducerSpaces} instance for Mojang.
     */
    public static ResourceProducerSpaces spacesMojang() {
        return ResourceProducers.spacesMojang(Font.MINECRAFT_DEFAULT);
    }

    /**
     * Creates a {@link GlyphImage} instance with the specified key, texture, and properties.
     *
     * @param key        The key associated with the glyph image. Cannot be null.
     * @param texture    The texture of the glyph image. Cannot be null.
     * @param properties The properties of the glyph image. Cannot be null.
     * @return A {@link GlyphImage} instance.
     */
    public static GlyphImage image(
        final Key key,
        final Texture texture,
        final TextureProperties properties
    ) {
        return new GlyphImageImpl(key, texture, properties);
    }

    /**
     * Creates a {@link ResourceProducerImageMultichar} instance with the specified parameters.
     *
     * @param fontKey           The key associated with the font. Cannot be null.
     * @param texture           The texture of the glyph collection. Cannot be null.
     * @param properties        The properties of the texture. Cannot be null.
     * @param charactersMapping The list of character mappings for the glyphs. Cannot be null.
     * @return A {@link ResourceProducerImageMultichar} instance.
     */
    static ResourceProducerImageMultichar multichar(
        final Key fontKey,
        final Texture texture,
        final TextureProperties properties,
        final List<String> charactersMapping
    ) {
        return new ResourceProducerImageMulticharImpl(
            fontKey,
            texture,
            properties,
            charactersMapping
        );
    }

    private ResourceProducers() {
        throw new IllegalStateException("Utility class");
    }
}
