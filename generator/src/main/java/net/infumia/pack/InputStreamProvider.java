package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;

public interface InputStreamProvider {
    InputStream provideFileStream(String path) throws IOException;

    List<InputStream> provideAll(Predicate<Entry> filter) throws IOException;
}
