package net.infumia.pack;

import net.kyori.adventure.key.Key;

/**
 * Utility class for creating and retrieving blank slot file resources.
 */
public final class BlankSlot {

    /**
     * Retrieves a pre-configured blank slot file resource.
     *
     * @param customModelData the custom model data of the paper model.
     *
     * @return A {@link FileResource} representing the blank slot.
     */
    public static FileResource get(final int customModelData) {
        return ResourceProducers.item(
            Key.key(Internal.DEFAULT_NAMESPACE, "blank_slot"),
            Key.key("item/paper"),
            Internal.resourceFromJar("blank_slot.png"),
            customModelData
        );
    }

    private BlankSlot() {
        throw new IllegalStateException("Utility class");
    }
}
