package net.infumia.pack;

/**
 * Utility class providing factory methods to create instances of {@link FileResourceCompiler}.
 */
public final class FileResourceCompilers {

    /**
     * Creates a simple {@link FileResourceCompiler} instance with a custom character factory.
     *
     * @param characterFactory The character factory to be used in the compilation. Cannot be null.
     * @return A newly created {@link FileResourceCompiler} instance configured with the provided character factory.
     */
    public static FileResourceCompiler simple(final ArbitraryCharacterFactory characterFactory) {
        return new FileResourceCompilerDefault(characterFactory);
    }

    /**
     * Creates a default {@link FileResourceCompiler} instance using a reserved character factory.
     *
     * @return A newly created {@link FileResourceCompiler} instance configured with the default character factory.
     */
    public static FileResourceCompiler simple() {
        return Internal.DEFAULT_COMPILER.get();
    }

    private FileResourceCompilers() {
        throw new IllegalStateException("Utility class");
    }

    private static final class Internal {

        private static final Lazy<ArbitraryCharacterFactory> CHARACTER_FACTORY = Lazy.of(
            ArbitraryCharacterFactoryReserved::new
        );
        private static final Lazy<FileResourceCompiler> DEFAULT_COMPILER = Lazy.of(
            () -> FileResourceCompilers.simple(Internal.CHARACTER_FACTORY.get())
        );
    }
}
