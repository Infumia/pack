package net.infumia.pack;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Predicate;

public interface InputStreamProvider {
    Entry provide(String path);

    Collection<Entry> provideAll(Predicate<Entry> filter) throws IOException;
}
