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
     * Creates a simple PackCompiler instance using a default character factory.
     * <p>
     * Uses an instance of {@link ArbitraryCharacterFactoryReserved} as the default character factory.
     *
     * @return A newly created {@link FileResourceCompiler} instance configured with a default character factory.
     */
    public static FileResourceCompiler simple() {
        return FileResourceCompilers.simple(new ArbitraryCharacterFactoryReserved());
    }

    private FileResourceCompilers() {
        throw new IllegalStateException("Utility class");
    }
}
