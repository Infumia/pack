package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

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
    public Collection<Entry> provideAll(final Predicate<Entry> filter) throws IOException {
        return Collections.emptyList();
    }
}
