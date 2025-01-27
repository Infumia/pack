package net.infumia.pack;

import java.nio.file.Path;
import java.util.StringJoiner;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;

/**
 * Settings for writing a resource pack.
 */
public final class PackWriterSettings {

    private final MinecraftResourcePackWriter writer;
    private final Path outputDirectory;
    private final Path outputFile;

    /**
     * Ctor.
     *
     * @param writer          the resource pack writer. Cannot be null.
     * @param outputDirectory the output directory. Can be null.
     * @param outputFile      the output file. Can be null.
     */
    public PackWriterSettings(
        final MinecraftResourcePackWriter writer,
        final Path outputDirectory,
        final Path outputFile
    ) {
        this.writer = writer;
        this.outputDirectory = outputDirectory;
        this.outputFile = outputFile;
    }

    /**
     * Returns the resource pack writer.
     *
     * @return the resource pack writer.
     */
    public MinecraftResourcePackWriter writer() {
        return this.writer;
    }

    /**
     * Returns the output directory.
     *
     * @return the output directory. Can be null.
     */
    public Path outputDirectory() {
        return this.outputDirectory;
    }

    /**
     * Returns the output file.
     *
     * @return the output file. Can be null.
     */
    public Path outputFile() {
        return this.outputFile;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackWriterSettings.class.getSimpleName() + "[", "]")
            .add("writer=" + this.writer)
            .toString();
    }
}
