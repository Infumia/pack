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
        final PackReader reader = new PackReader(readerSettings, base);
        final PackWriter writer = new PackWriter(writerSettings);
        final PackGeneratorContext context = reader.read();
        final PackGeneratorContext parsed = PackParser.parse(context);
        return writer.write(parsed);
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

    private PackGenerator() {
        throw new IllegalStateException("Utility class");
    }
}
