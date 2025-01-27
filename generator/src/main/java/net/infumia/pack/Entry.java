package net.infumia.pack;

import java.io.InputStream;
import java.util.Collection;

public interface Entry {
    String name();

    String rootRelativeName();

    Collection<Entry> children();

    boolean isRegularFile();

    boolean hasExtension(String extension);

    boolean is(String path);

    InputStream asInputStream();
}
