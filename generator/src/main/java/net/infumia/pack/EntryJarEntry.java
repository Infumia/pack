package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;

final class EntryJarEntry implements Entry {

    private final InputStreamProviderJarFile provider;
    private final JarEntry jarEntry;

    EntryJarEntry(final InputStreamProviderJarFile provider, final JarEntry jarEntry) {
        Objects.requireNonNull(provider, "provider is null");
        Objects.requireNonNull(jarEntry, "jarEntry is null");
        this.provider = provider;
        this.jarEntry = jarEntry;
    }

    @Override
    public String name() {
        return this.jarEntry.getName();
    }

    @Override
    public String rootRelativeName() {
        return this.name().substring(this.provider.rootPathAsString.length());
    }

    @Override
    public boolean isRegularFile() {
        return !this.jarEntry.isDirectory();
    }

    @Override
    public boolean hasExtension(final String extension) {
        return this.jarEntry.getName().endsWith(extension);
    }

    @Override
    public boolean is(final String path) {
        final ZipEntry entry = Objects.requireNonNull(
            this.provider.jarFile.getEntry(this.provider.rootPathAsString + path),
            "Entry not found in jar file: " + path
        );
        return this.jarEntry.getName().equals(entry.getName());
    }

    @Override
    public InputStream asInputStream() {
        try {
            return this.provider.jarFile.getInputStream(this.jarEntry);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to get input stream from jar entry!", e);
        }
    }
}
