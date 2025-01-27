package net.infumia.pack;

import java.io.IOException;
import java.util.Comparator;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.metadata.pack.PackMeta;

/**
 * Utility class for parsing packs.
 */
public final class PackParser {

    /**
     * Reads the pack based on the provided settings and base pack.
     *
     * @param readerSettings the pack reader settings. Cannot be null.
     * @return the pack read context.
     * @throws IOException if an I/O error is thrown when accessing the starting file.
     */
    public static PackReadContext read(final PackReaderSettings readerSettings) throws IOException {
        return new PackReader(readerSettings).read();
    }

    /**
     * Writes the pack based on the provided settings and context.
     *
     * @param writerSettings the pack writer settings. Cannot be null.
     * @param resourcePack   the resource pack to write. Cannot be null.
     * @return the generated pack context.
     */
    public static PackWriteContext write(
        final PackWriterSettings writerSettings,
        final ResourcePack resourcePack
    ) {
        return new PackWriter(writerSettings).write(resourcePack);
    }

    /**
     * Writes the pack based on the provided settings and context.
     *
     * @param writerSettings the pack writer settings. Cannot be null.
     * @return the generated pack context.
     */
    public static PackWriteContext write(final PackWriterSettings writerSettings) {
        return PackParser.write(writerSettings, ResourcePack.resourcePack());
    }

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
