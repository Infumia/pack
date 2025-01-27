package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
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
    public InputStream provide(final String path) throws IOException {
        return this.jarFile.getInputStream(this.jarFile.getEntry(path));
    }

    @Override
    public Collection<Entry> provideAll(final Predicate<Entry> filter) {
        final Collection<JarEntry> allEntries =
            this.jarFile.stream()
                .filter(entry -> entry.getName().startsWith(this.rootPathAsString))
                .collect(Collectors.toList());

        // Girişleri ağaç yapısına dönüştür
        final Map<String, EntryJarEntry> entryMap = new HashMap<>();
        for (final JarEntry jarEntry : allEntries) {
            final String entryName = jarEntry.getName();
            final Collection<Entry> children = new HashSet<>();
            if (entryName.contains("/")) {
                final String parentPath = entryName.substring(0, entryName.lastIndexOf('/') + 1);
                final EntryJarEntry parent = entryMap.get(parentPath);
                if (parent != null && !parent.isRegularFile()) {
                    children.add(entryMap.get(entryName));
                }
            }
            entryMap.put(entryName, new EntryJarEntry(this.jarFile, jarEntry, children));
        }

        // Klasörlere alt öğelerini ekle
        for (final JarEntry jarEntry : allEntries) {
            final String entryName = jarEntry.getName();
            if (entryName.contains("/")) {
                final String parentPath = entryName.substring(0, entryName.lastIndexOf('/') + 1);
                final EntryJarEntry parent = entryMap.get(parentPath);
                if (parent != null && !parent.isRegularFile()) {
                    parent.children().add(entryMap.get(entryName));
                }
            }
        }

        // Filtre uygula ve sadece kök girişleri döndür
        return entryMap
            .values()
            .stream()
            .filter(entry -> !entry.name().contains("/"))
            .filter(filter)
            .collect(Collectors.toList());
    }
}
