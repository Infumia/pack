package net.infumia.pack;

import java.io.InputStream;

public interface Entry {
    boolean isRegularFile();

    boolean hasExtension(String extension);

    boolean is(String path);

    InputStream asInputStream();
}
