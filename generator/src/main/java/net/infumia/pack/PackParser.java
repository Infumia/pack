package net.infumia.pack;

import java.util.Collection;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.metadata.pack.PackMeta;

/**
 * Utility class for parsing packs.
 */
public final class PackParser {

    /**
     * Parses the given pack generator context.
     *
     * @param context the pack generator context. Cannot be null.
     * @return the updated pack generator context.
     */
    public static PackGeneratorContext parse(final PackGeneratorContext context) {
        final PackReferenceMeta packReference = context.packReference();
        final Collection<PackReferencePart> partReferences = context.packPartReferences();
        final ResourcePack resourcePack = context.resourcePack();
        final PackMeta packMeta = packReference.parsePackMeta(context.serializer());
        resourcePack.packMeta(packMeta);
        final Pack pack = context.pack();
        // TODO: portlek, Parse partReferences here.
        if (packReference.addBlankSlot()) {
            pack.with(BlankSlot.get());
        }
        if (packReference.addSpaces()) {
            if (packMeta.formats().isInRange(9)) {
                pack.withMojangSpaces();
            } else {
                pack.withBitmapSpaces();
            }
        }
        pack.writeAll(resourcePack);
        return context;
    }

    private PackParser() {
        throw new IllegalStateException("Utility class");
    }
}
