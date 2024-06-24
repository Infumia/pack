package net.infumia.pack;

import team.unnamed.creative.serialize.ResourcePackWriter;
import team.unnamed.creative.serialize.minecraft.fs.FileTreeWriter;

public final class PackWriterSettings {

    private final ResourcePackWriter<FileTreeWriter> writer;

    public PackWriterSettings(final ResourcePackWriter<FileTreeWriter> writer) {
        this.writer = writer;
    }

    public ResourcePackWriter<FileTreeWriter> writer() {
        return this.writer;
    }
}
