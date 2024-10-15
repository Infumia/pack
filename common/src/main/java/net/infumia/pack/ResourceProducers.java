package net.infumia.pack;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.model.ItemOverride;
import team.unnamed.creative.model.ItemPredicate;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;
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
    public static ResourceProducerSpaces spacesBitmap(
        final Key fontKey,
        final Key textureKey,
        final Writable spacesWritable
    ) {
        return new ResourceProducerSpacesBitmap(fontKey, textureKey, spacesWritable);
    }

    /**
     * Creates a {@link ResourceProducerSpaces} instance with default parameters.
     *
     * @return A {@link ResourceProducerSpaces} instance.
     */
    public static ResourceProducerSpaces spacesBitmap() {
        return ResourceProducers.spacesBitmap(
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
     * @param fontKey    The font key associated with the glyph image. Cannot be null.
     * @param texture    The texture of the glyph image. Cannot be null.
     * @param properties The properties of the glyph image. Cannot be null.
     * @return A {@link GlyphImage} instance.
     */
    public static GlyphImage image(
        final Key fontKey,
        final Texture texture,
        final TextureProperties properties
    ) {
        return new GlyphImageImpl(fontKey, texture, properties);
    }

    /**
     * Creates an item resource with the specified parameters.
     *
     * @param itemKey           The key for the item. Cannot be null.
     * @param overriddenItemKey The key for the base model. Cannot be null.
     * @param itemImage         The writable image for the blank slot. Cannot be null.
     * @param customModelData   The custom model data value.
     * @return A {@link FileResource} representing the created item.
     */
    public static FileResource item(
        final Key itemKey,
        final Key overriddenItemKey,
        final Writable itemImage,
        final int customModelData
    ) {
        return FileResources.all(
            FileResources.model(
                Model.model()
                    .key(itemKey)
                    .parent(Model.ITEM_GENERATED)
                    .textures(
                        ModelTextures.builder()
                            .layers(ModelTexture.ofKey(Internal.toItemKey(itemKey)))
                            .build()
                    )
                    .build()
            ),
            FileResources.model(
                Model.model()
                    .key(overriddenItemKey)
                    .parent(Model.ITEM_GENERATED)
                    .textures(
                        ModelTextures.builder()
                            .layers(ModelTexture.ofKey(overriddenItemKey))
                            .build()
                    )
                    .overrides(
                        ItemOverride.of(itemKey, ItemPredicate.customModelData(customModelData))
                    )
                    .build()
            ),
            FileResources.texture(Texture.texture(Internal.toTextureKey(itemKey), itemImage))
        );
    }

    /**
     * Creates a model resource with the specified parameters.
     *
     * @param key           The key for the item. Cannot be null.
     * @return A {@link FileResource} representing the created model.
     */
    public static FileResource model(final Key key) {
        return FileResources.model(Model.model().key(key).build());
    }

    private ResourceProducers() {
        throw new IllegalStateException("Utility class");
    }
}
