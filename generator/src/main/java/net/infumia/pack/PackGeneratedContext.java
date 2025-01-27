package net.infumia.pack;

import java.nio.file.Path;
import java.util.StringJoiner;
import team.unnamed.creative.ResourcePack;

/**
 * Context for generated resource pack.
 */
public final class PackGeneratedContext {

    private final ResourcePack resourcePack;
    private final Path outputDirectory;
    private final Path outputFile;

    /**
     * Ctor.
     *
     * @param resourcePack    the resource pack. Cannot be null.
     * @param outputDirectory the output directory. Can be null.
     * @param outputFile      the output file. Can be null.
     */
    PackGeneratedContext(
        final ResourcePack resourcePack,
        final Path outputDirectory,
        final Path outputFile
    ) {
        this.resourcePack = resourcePack;
        this.outputDirectory = outputDirectory;
        this.outputFile = outputFile;
    }

    /**
     * Returns the resource pack.
     *
     * @return the resource pack.
     */
    public ResourcePack resourcePack() {
        return this.resourcePack;
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
            .add("resourcePack=" + this.resourcePack)
            .add("outputDirectory=" + this.outputDirectory)
            .add("outputFile=" + this.outputFile)
            .toString();
    }
}
