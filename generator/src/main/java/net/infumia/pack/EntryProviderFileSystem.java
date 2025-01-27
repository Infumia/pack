package net.infumia.pack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EntryProviderFileSystem implements EntryProvider {

    final Path root;

    public EntryProviderFileSystem(final Path root) {
        this.root = root;
    }

    @Override
    public Entry provide(final String path) {
        return new EntryPath(this, this.root.resolve(path));
    }

    @Override
    public Collection<Entry> provideAll(final Predicate<Entry> filter) throws IOException {
        try (final Stream<Path> files = Files.walk(this.root)) {
            return files
                .map(path -> new EntryPath(this, path))
                .filter(filter)
                .collect(Collectors.toList());
        }
    }
}
