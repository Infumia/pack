package net.infumia.pack;

import java.nio.file.Path;
import java.util.StringJoiner;

/**
 * Context for generated resource pack.
 */
public final class PackGeneratedContext {

    private final Path outputDirectory;
    private final Path outputFile;

    /**
     * Ctor.
     *
     * @param outputDirectory the output directory. Can be null.
     * @param outputFile      the output file. Can be null.
     */
    PackGeneratedContext(final Path outputDirectory, final Path outputFile) {
        this.outputDirectory = outputDirectory;
        this.outputFile = outputFile;
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
        return new StringJoiner(", ", PackGeneratedContext.class.getSimpleName() + "[", "]")
            .add("outputDirectory=" + this.outputDirectory)
            .add("outputFile=" + this.outputFile)
            .toString();
    }
}
