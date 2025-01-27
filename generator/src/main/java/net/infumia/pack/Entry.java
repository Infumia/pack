package net.infumia.pack;

import java.io.InputStream;

public interface Entry {
    String name();

    String rootRelativeName();

    String simplifiedName();

    boolean isRegularFile();

    boolean hasExtension(String extension);

    boolean is(String path);

    InputStream asInputStream();
}
