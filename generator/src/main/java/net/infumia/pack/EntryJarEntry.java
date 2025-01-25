package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

final class EntryJarEntry implements Entry {

    private final JarFile jarFile;
    private final JarEntry jarEntry;

    EntryJarEntry(final JarFile jarFile, final JarEntry jarEntry) {
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
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
            this.jarFile.getEntry(path),
            "Entry not found in jar file: " + path
        );
        return this.jarEntry.getName().equals(entry.getName());
    }

    @Override
    public InputStream asInputStream() {
        try {
            return this.jarFile.getInputStream(this.jarEntry);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to get input stream from jar entry!", e);
        }
    }
}
