package net.infumia.pack;

/**
 * Utility class for creating instances of {@link Pack}.
 */
public final class Packs {

    /**
     * Creates a new Pack instance with the specified file resource compiler.
     *
     * @param compiler The file resource compiler to use. Cannot be null.
     * @param merger The merger to be used to merge same file resources. Cannot be null.
     * @return A new Pack instance.
     */
    public static Pack create(
        final FileResourceCompiler compiler,
        final FileResourceMerger merger
    ) {
        return new PackDefault(compiler, merger);
    }

    /**
     * Creates a new Pack instance with a default simple file resource compiler.
     *
     * @return A new Pack instance.
     */
    public static Pack create() {
        return Packs.create(FileResourceCompilers.simple(), FileResourceMergers.simple());
    }

    private Packs() {
        throw new IllegalStateException("Utility class");
    }
}
