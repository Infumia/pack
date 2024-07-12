package net.infumia.pack;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import team.unnamed.creative.base.Writable;

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
        return ResourceProducers.item(
            Key.key(namespace, itemId),
            baseKey,
            blankSlotImage,
            customModelData
        );
    }

    private BlankSlot() {
        throw new IllegalStateException("Utility class");
    }

    private static final class Internal0 {

        private static final Lazy<FileResource> BLANK_SLOT = Lazy.of(
            () ->
                BlankSlot.create(
                    Internal.DEFAULT_NAMESPACE,
                    "blank_slot",
                    Key.key("item/paper"),
                    Internal.resourceFromJar("blank_slot.png"),
                    1
                )
        );
    }
}
