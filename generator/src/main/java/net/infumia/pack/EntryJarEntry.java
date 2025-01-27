package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

final class EntryJarEntry implements Entry {

    private final JarFile jarFile;
    private final JarEntry jarEntry;
    private final Collection<Entry> children;

    EntryJarEntry(
        final JarFile jarFile,
        final JarEntry jarEntry,
        final Collection<Entry> children
    ) {
        Objects.requireNonNull(jarFile, "jarFile is null");
        Objects.requireNonNull(jarEntry, "jarEntry is null");
        Objects.requireNonNull(children, "children is null");
        this.jarFile = jarFile;
        this.jarEntry = jarEntry;
        this.children = Collections.unmodifiableCollection(children);
    }

    @Override
    public String name() {
        return this.jarFile.getName();
    }

    @Override
    public Collection<Entry> children() {
        return this.children;
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
