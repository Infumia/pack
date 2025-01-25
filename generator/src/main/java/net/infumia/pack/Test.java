package net.infumia.pack;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public final class Test {

    public static List<PackReferencePart> read(
        final List<InputStream> partStreams,
        final ObjectMapper mapper
    ) {
        final ObjectReader reader = mapper.readerFor(PackReferencePart.class);
        return partStreams
            .stream()
            .flatMap(stream -> {
                try (
                    final MappingIterator<PackReferencePart> iterator = reader.readValues(stream)
                ) {
                    return iterator.readAll().stream();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
    }
}
