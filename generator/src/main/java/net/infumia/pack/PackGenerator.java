package net.infumia.pack;

import java.io.IOException;

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
        return PackGenerator.write(
            writerSettings,
            PackParser.parse(PackGenerator.read(readerSettings, base))
        );
    }

    /**
     * Generates a resource pack based on the provided settings with a default base pack.
     *
     * @param readerSettings the pack reader settings. Cannot be null.
     * @param writerSettings the pack writer settings. Cannot be null.
     * @return the generated pack context.
     * @throws IOException if an I/ O error is thrown when accessing the starting file.
     */
    public static PackGeneratedContext generate(
        final PackReaderSettings readerSettings,
        final PackWriterSettings writerSettings
    ) throws IOException {
        return PackGenerator.generate(readerSettings, writerSettings, Packs.create());
    }

    /**
     * Reads the pack based on the provided settings and base pack.
     *
     * @param readerSettings the pack reader settings. Cannot be null.
     * @param base           the base pack. Cannot be null.
     * @return the pack generation context.
     * @throws IOException if an I/O error is thrown when accessing the starting file.
     */
    public static PackGeneratorContext read(
        final PackReaderSettings readerSettings,
        final Pack base
    ) throws IOException {
        return new PackReader(readerSettings, base).read();
    }

    /**
     * Writes the pack based on the provided settings and context.
     *
     * @param writerSettings the pack writer settings. Cannot be null.
     * @param context        the pack generator context. Cannot be null.
     * @return the generated pack context.
     */
    public static PackGeneratedContext write(
        final PackWriterSettings writerSettings,
        final PackGeneratorContext context
    ) {
        return new PackWriter(writerSettings).write(context);
    }

    private PackGenerator() {
        throw new IllegalStateException("Utility class");
    }
}
