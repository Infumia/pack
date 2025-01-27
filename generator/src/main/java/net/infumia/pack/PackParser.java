package net.infumia.pack;

import java.util.Comparator;
import team.unnamed.creative.metadata.pack.PackMeta;

/**
 * Utility class for parsing packs.
 */
public final class PackParser {

    /**
     * Parses the given pack read context.
     *
     * @param context the pack read context. Cannot be null.
     * @param pack    the pack. Cannot be null.
     */
    public static void parse(final PackReadContext context, final Pack pack) {
        PackParser.parseMeta(context, pack);
        PackParser.parseParts(context, pack);
    }

    private static void parseMeta(final PackReadContext context, final Pack pack) {
        final PackReferenceMeta meta = context.packReference();
        final PackMeta packMeta = meta.parsePackMeta(context.readerSettings().serializer());
        pack.with(FileResources.meta(packMeta));
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

    private static void parseParts(final PackReadContext context, final Pack pack) {
        context
            .packPartReferences()
            .stream()
            .sorted(Comparator.comparing(part -> part.extractKey(context)))
            .forEach(part -> part.add(context, pack));
    }

    private PackParser() {
        throw new IllegalStateException("Utility class");
    }
}
