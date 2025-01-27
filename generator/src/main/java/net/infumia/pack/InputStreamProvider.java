package net.infumia.pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;

public interface InputStreamProvider {
    InputStream provide(String path) throws IOException;

    Collection<Entry> provideAll(Predicate<Entry> filter) throws IOException;
}
