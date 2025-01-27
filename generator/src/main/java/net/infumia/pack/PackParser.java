package net.infumia.pack;

import java.util.Comparator;
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
        final PackMeta packMeta = meta.parsePackMeta(context.readerSettings().serializer());
        context.resourcePack().packMeta(packMeta);
        if (meta.addBlankSlot()) {
            final int customModelData;
            final Integer cmd = meta.blankSlotCustomModelData();
            if (cmd == null) {
                customModelData = 1;
            } else {
                customModelData = cmd;
            }
            pack.with(BlankSlot.get(customModelData));
        }
        if (meta.addSpaces()) {
            if (packMeta.formats().min() >= 9) {
                pack.withMojangSpaces();
            } else {
                pack.withBitmapSpaces();
            }
        }
    }

    private static void parseParts(final PackGeneratorContext context) {
        context
            .packPartReferences()
            .stream()
            .sorted(Comparator.comparing(part -> part.extractKey(context)))
            .forEach(part -> part.add(context));
    }

    private PackParser() {
        throw new IllegalStateException("Utility class");
    }
}
