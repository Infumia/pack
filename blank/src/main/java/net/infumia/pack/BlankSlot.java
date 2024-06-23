package net.infumia.pack;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import team.unnamed.creative.atlas.Atlas;
import team.unnamed.creative.atlas.AtlasSource;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.model.ItemOverride;
import team.unnamed.creative.model.ItemPredicate;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;
import team.unnamed.creative.texture.Texture;

/**
 * Utility class for creating and retrieving blank slot file resources.
 */
public final class BlankSlot {
    /**
     * Retrieves a pre-configured blank slot file resource.
     *
     * @return A {@link FileResource} representing the blank slot.
     */
    public static FileResource get() {
        return Internal0.BLANK_SLOT.get();
    }

    /**
     * Creates a blank slot file resource with the specified parameters.
     *
     * @param namespace       The namespace for the model. Cannot be null.
     * @param itemId          The id for the item. Cannot be null.
     * @param baseKey         The key for the base model. Cannot be null.
     * @param blankSlotImage  The writable image for the blank slot. Cannot be null.
     * @param customModelData The custom model data value.
     * @return A {@link FileResource} representing the created blank slot.
     */
    public static FileResource create(
        @KeyPattern.Namespace final String namespace,
        @KeyPattern.Value final String itemId,
        final Key baseKey,
        final Writable blankSlotImage,
        final int customModelData
    ) {
        final Key itemKey = Key.key(namespace, itemId);
        final ModelTextures itemModelTexture = ModelTextures.builder()
            .layers(ModelTexture.ofKey(itemKey))
            .build();
        final Model itemModel = Model.model()
            .key(itemKey)
            .parent(Model.ITEM_GENERATED)
            .textures(itemModelTexture)
            .build();

        final ModelTextures baseModelTexture = ModelTextures.builder()
            .layers(ModelTexture.ofKey(baseKey))
            .build();
        final ItemOverride baseOverride = ItemOverride.of(itemKey, ItemPredicate.customModelData(customModelData));
        final Model baseItemModel = Model.model()
            .key(baseKey)
            .parent(Model.ITEM_GENERATED)
            .textures(baseModelTexture)
            .overrides(baseOverride)
            .build();

        final Texture texture = Texture.texture(Internal.keyWithPngExtension(itemKey), blankSlotImage);
        final Atlas atlas = Atlas.atlas()
            .key(Atlas.BLOCKS)
            .sources(AtlasSource.directory(namespace, namespace + "/"))
            .build();

        return FileResources.all(
            FileResources.model(itemModel),
            FileResources.model(baseItemModel),
            FileResources.texture(texture),
            FileResources.atlas(atlas)
        );
    }

    private BlankSlot() {
        throw new IllegalStateException("Utility class");
    }

    private static final class Internal0 {
        private static final Lazy<FileResource> BLANK_SLOT = Lazy.of(() ->
            BlankSlot.create(
                Internal.DEFAULT_NAMESPACE,
                "blank_slot",
                Key.key("item/paper"),
                Internal.resourceFromJar("pack-resources/blank_slot.png"),
                1
            )
        );
    }
}
