package net.infumia.pack;

import java.io.IOException;
import team.unnamed.creative.ResourcePack;

/**
 * Utility class for generating resource packs.
 */
public final class PackGenerator {

    /**
     * Generates a resource pack based on the provided settings and base pack.
     *
     * @param readerSettings the pack reader settings. Cannot be null.
     * @param writerSettings the pack writer settings. Cannot be null.
     * @param base           the base pack. Cannot be null.
     * @return the generated pack context.
     * @throws IOException if an I/ O error is thrown when accessing the starting file.
     */
    public static PackGeneratedContext generate(
        final PackReaderSettings readerSettings,
        final PackWriterSettings writerSettings,
        final Pack base
    ) throws IOException {
        final ResourcePack resourcePack = ResourcePack.resourcePack();
        PackParser.parse(PackGenerator.read(readerSettings), base);
        base.writeAll(resourcePack);
        return PackGenerator.write(writerSettings, resourcePack);
    }

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
    public static PackGeneratedContext write(
        final PackWriterSettings writerSettings,
        final ResourcePack resourcePack
    ) {
        return new PackWriter(writerSettings).write(resourcePack);
    }

    private PackGenerator() {
        throw new IllegalStateException("Utility class");
    }
}
