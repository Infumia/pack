package net.infumia.pack;

import java.io.InputStream;

public interface Entry {
    String name();

    String rootRelativeName();

    boolean isRegularFile();

    boolean hasExtension(String extension);

    boolean is(String path);

    InputStream asInputStream();
}
