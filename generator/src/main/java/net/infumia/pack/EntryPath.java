package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

final class EntryPath implements Entry {

    private final InputStreamProviderFileSystem provider;
    private final Path path;

    EntryPath(final InputStreamProviderFileSystem provider, final Path path) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(path, "path");
        this.provider = provider;
        this.path = path;
    }

    @Override
    public String name() {
        return this.path.toString();
    }

    @Override
    public String rootRelativeName() {
        return this.name().substring(this.provider.root.toString().length());
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
        return this.path.equals(this.provider.root.resolve(path));
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
