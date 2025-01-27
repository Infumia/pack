package net.infumia.pack;

import java.io.File;
import java.io.InputStream;

public interface Entry {
    String name();

    String rootRelativeName();

    default String simplifiedName() {
        String creatorName = this.rootRelativeName().replace(File.separatorChar, '/');
        creatorName = creatorName.startsWith("/") ? creatorName.substring(1) : creatorName;
        return creatorName.substring(0, creatorName.length() - ".yml".length());
    }

    boolean isRegularFile();

    boolean hasExtension(String extension);

    boolean is(String path);

    InputStream asInputStream();
}
