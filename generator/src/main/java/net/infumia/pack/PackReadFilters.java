package net.infumia.pack;

import java.util.function.Predicate;

/**
 * Utility class for creating read filters for pack reading.
 */
public final class PackReadFilters {

    /**
     * Creates a predicate that filters paths by the specified file extension.
     *
     * @param extension the file extension to filter by. Cannot be null.
     * @return a predicate that returns true for paths with the specified extension.
     */
    public static Predicate<Entry> withExtension(final String extension) {
        return path -> path.hasExtension(extension);
    }

    private PackReadFilters() {
        throw new IllegalStateException("Utility class");
    }
}
