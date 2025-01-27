package net.infumia.pack;

import java.util.Collection;
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
    public Entry provide(final String path) {
        return new EntryJarEntry(this, this.jarFile.getJarEntry(this.rootPathAsString + path));
    }

    @Override
    public Collection<Entry> provideAll(final Predicate<Entry> filter) {
        return this.jarFile.stream()
            .filter(entry -> entry.getName().startsWith(this.rootPathAsString))
            .map(entry -> new EntryJarEntry(this, entry))
            .filter(filter)
            .collect(Collectors.toSet());
    }
}
