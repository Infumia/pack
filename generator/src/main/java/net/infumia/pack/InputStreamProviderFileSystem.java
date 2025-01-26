package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class InputStreamProviderFileSystem implements InputStreamProvider {

    private final Path root;

    public InputStreamProviderFileSystem(final Path root) {
        this.root = root;
    }

    @Override
    public InputStream provide(final String path) throws IOException {
        return Files.newInputStream(this.root.resolve(path));
    }

    @Override
    public List<Entry> provideAll(final Predicate<Entry> filter) throws IOException {
        try (final Stream<Path> files = Files.walk(this.root)) {
            return files
                .map(path -> new EntryPath(this.root, path))
                .filter(filter)
                .collect(Collectors.toList());
        }
    }
}
