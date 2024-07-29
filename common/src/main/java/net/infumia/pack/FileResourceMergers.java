package net.infumia.pack;

/**
 * Utility class for providing various implementations of {@link FileResourceMerger}.
 */
public final class FileResourceMergers {

    /**
     * Returns a simple file resource merger implementation.
     *
     * @return A {@link FileResourceMerger} representing the simple file resource merger.
     */
    public static FileResourceMerger simple() {
        return FileResourceMergerDefault.INSTANCE;
    }

    private FileResourceMergers() {
        throw new IllegalStateException("Utility class");
    }
}
