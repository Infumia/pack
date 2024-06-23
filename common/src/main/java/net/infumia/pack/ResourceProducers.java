package net.infumia.pack;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

import java.util.List;

/**
 * Utility class for creating instances of {@link ResourceProducerSpaces}.
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
     * Creates a Mojang-specific space producer with the specified key.
     *
     * @param key The key for the Mojang space producer. Cannot be null.
     * @return A {@link ResourceProducerSpaces} instance for Mojang.
     */
    public static ResourceProducerSpaces spacesMojang(final Key key) {
        return new ResourceProducerSpacesMojang(key);
    }

    /**
     * Creates a Mojang-specific space producer with default parameters.
     *
     * @return A {@link ResourceProducerSpaces} instance for Mojang.
     */
    public static ResourceProducerSpaces spacesMojang() {
        return ResourceProducers.spacesMojang(Internal.DEFAULT_SPACES_FONT_KEY);
    }

    /**
     * Creates a {@link GlyphImage} instance with the specified key, texture, and properties.
     *
     * @param key        The key associated with the glyph image. Cannot be null.
     * @param texture    The texture of the glyph image. Cannot be null.
     * @param properties The properties of the glyph image. Cannot be null.
     * @return A {@link GlyphImage} instance.
     */
    public static GlyphImage image(final Key key, final Texture texture, final TextureProperties properties) {
        return new GlyphImageImpl(key, texture, properties);
    }

    /**
     * Creates a {@link ResourceProducerLanguage} instance with the specified parameters.
     *
     * @param fontKey           The key associated with the font. Cannot be null.
     * @param texture           The texture of the glyph collection. Cannot be null.
     * @param propertiesList    The list of texture properties for the glyphs. Cannot be null.
     * @param charactersMapping The list of character mappings for the glyphs. Cannot be null.
     * @return A {@link ResourceProducerLanguage} instance.
     */
    public static ResourceProducerLanguage language(
        final Key fontKey,
        final Texture texture,
        final List<TextureProperties> propertiesList,
        final List<String> charactersMapping
    ) {
        return new ResourceProducerLanguageImpl(fontKey, texture, propertiesList, charactersMapping);
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
        return new ResourceProducerImageMulticharImpl(fontKey, texture, properties, charactersMapping);
    }

    private ResourceProducers() {
        throw new IllegalStateException("Utility class");
    }
}
