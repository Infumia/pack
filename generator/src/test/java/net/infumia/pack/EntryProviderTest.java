package net.infumia.pack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

final class EntryProviderTest {

    private final Predicate<Entry> isMetaEntry = entry -> entry.is("pack.yml");
    private final Predicate<Entry> hasYmlExtension = entry -> entry.hasExtension("yml");
    private final Predicate<Entry> isRegularFile = Entry::isRegularFile;

    @ParameterizedTest
    @MethodSource("inputStreamProviderFactory")
    void provide(final EntryProvider provider) throws IOException {
        final Entry metaEntry = provider.provide("pack.yml");

        final ObjectMapper mapper = new YAMLMapper();
        final PackReferenceMeta meta = mapper.readValue(
            metaEntry.asInputStream(),
            PackReferenceMeta.class
        );

        assertEquals(6, meta.format());
        assertEquals("Test Resource Pack", meta.description());
        Assertions.assertTrue(meta.addSpaces());
        Assertions.assertTrue(meta.addBlankSlot());
        assertEquals(150, meta.blankSlotCustomModelData());
        assertEquals("test", meta.defaultNamespace());
    }

    @ParameterizedTest
    @MethodSource("inputStreamProviderFactory")
    void provideAll(final EntryProvider provider) throws IOException {
        final Collection<Entry> partEntries = provider.provideAll(
            this.isRegularFile.and(this.hasYmlExtension).and(this.isMetaEntry.negate())
        );

        final Collection<String> actualPartNames = partEntries
            .stream()
            .map(Entry::simplifiedName)
            .collect(Collectors.toSet());
        final Collection<String> expectedPartNames = new HashSet<>(
            Arrays.asList("general", "security/help", "security/option/option")
        );

        assertEquals(expectedPartNames, actualPartNames);

        final ObjectMapper mapper = new YAMLMapper();
        final ObjectReader reader = mapper.readerFor(PackReferencePart.class);

        for (final Entry entry : partEntries) {
            reader.readValues(entry.asInputStream()).readAll();
        }
    }

    private static Stream<EntryProvider> inputStreamProviderFactory() throws IOException {
        return Stream.of(
            new EntryProviderFileSystem(Paths.get("src/test/resources/pack-resources")),
            new EntryProviderJarFile(
                new JarFile(Paths.get("src/test/resources/test.jar").toFile()),
                "pack-resources/"
            ),
            new EntryProviderJarFile(
                new JarFile(Paths.get("src/test/resources/test.jar").toFile()),
                "pack-resources"
            ),
            new EntryProviderJarFile(
                new JarFile(Paths.get("src/test/resources/test.jar").toFile()),
                "pack-resources\\"
            )
        );
    }
}
