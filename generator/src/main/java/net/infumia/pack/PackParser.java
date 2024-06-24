package net.infumia.pack;

import java.util.Collection;
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
        PackParser.parseMeta(context);
        PackParser.parseParts(context);
        context.pack().writeAll(context.resourcePack());
        return context;
    }

    private static void parseMeta(final PackGeneratorContext context) {
        final Pack pack = context.pack();
        final PackReferenceMeta meta = context.packReference();
        final PackMeta packMeta = meta.parsePackMeta(context.serializer());
        context.resourcePack().packMeta(packMeta);
        if (meta.addBlankSlot()) {
            pack.with(BlankSlot.get());
        }
        if (meta.addSpaces()) {
            if (packMeta.formats().isInRange(9)) {
                pack.withMojangSpaces();
            } else {
                pack.withBitmapSpaces();
            }
        }
    }

    private static void parseParts(final PackGeneratorContext context) {
        final Pack pack = context.pack();
        final Collection<PackReferencePart> parts = context.packPartReferences();
        for (final PackReferencePart part : parts) {
            pack.with(part.toResource());
        }
    }

    private PackParser() {
        throw new IllegalStateException("Utility class");
    }
}
