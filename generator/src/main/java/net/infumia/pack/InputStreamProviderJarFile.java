package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class InputStreamProviderJarFile implements InputStreamProvider {

    final JarFile jarFile;
    final String rootPathAsString;

    public InputStreamProviderJarFile(final JarFile jarFile, final String rootPathAsString) {
        this.jarFile = jarFile;
        this.rootPathAsString = rootPathAsString.endsWith("/")
            ? rootPathAsString
            : rootPathAsString + "/";
    }

    @Override
    public InputStream provide(final String path) throws IOException {
        return this.jarFile.getInputStream(this.jarFile.getEntry(this.rootPathAsString + path));
    }

    @Override
    public Collection<Entry> provideAll(final Predicate<Entry> filter) {
        return this.jarFile.stream()
            .filter(entry -> entry.getName().startsWith(this.rootPathAsString))
            .map(entry -> new EntryJarEntry(this, entry, Collections.emptyList()))
            .filter(filter)
            .collect(Collectors.toSet());
    }
}
