package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

final class EntryPath implements Entry {

    private final Path root;
    private final Path path;

    EntryPath(final Path root, final Path path) {
        this.root = root;
        this.path = path;
    }

    @Override
    public boolean isRegularFile() {
        return Files.isRegularFile(this.path);
    }

    @Override
    public boolean hasExtension(final String extension) {
        return this.path.getFileName().toString().endsWith(extension);
    }

    @Override
    public boolean is(final String path) {
        return this.path.equals(this.root.resolve(path));
    }

    @Override
    public InputStream asInputStream() {
        try {
            return Files.newInputStream(this.path);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to get input stream from path!", e);
        }
    }
}
