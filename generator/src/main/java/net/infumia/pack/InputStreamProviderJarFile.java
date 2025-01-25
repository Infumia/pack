package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public final class InputStreamProviderJarFile implements InputStreamProvider {

    private final JarFile jarFile;
    private final String rootPathAsString;

    public InputStreamProviderJarFile(final JarFile jarFile, final String rootPathAsString) {
        this.jarFile = jarFile;
        this.rootPathAsString = rootPathAsString;
    }

    @Override
    public InputStream provideFileStream(final String path) throws IOException {
        return this.jarFile.getInputStream(this.jarFile.getEntry(path));
    }

    @Override
    public List<InputStream> provideAll(final Predicate<Entry> filter) {
        return this.jarFile.stream()
            .filter(entry -> entry.getName().startsWith(this.rootPathAsString))
            .map(entry -> new EntryJarEntry(this.jarFile, entry))
            .filter(filter)
            .map(EntryJarEntry::asInputStream)
            .collect(Collectors.toList());
    }
}
