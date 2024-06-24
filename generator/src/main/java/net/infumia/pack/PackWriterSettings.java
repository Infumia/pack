package net.infumia.pack;

import team.unnamed.creative.serialize.ResourcePackWriter;
import team.unnamed.creative.serialize.minecraft.fs.FileTreeWriter;

/**
 * Settings for writing a resource pack.
 */
public final class PackWriterSettings {

    private final ResourcePackWriter<FileTreeWriter> writer;

    /**
     * Ctor.
     *
     * @param writer the resource pack writer. Cannot be null.
     */
    public PackWriterSettings(final ResourcePackWriter<FileTreeWriter> writer) {
        this.writer = writer;
    }

    /**
     * Returns the resource pack writer.
     *
     * @return the resource pack writer.
     */
    public ResourcePackWriter<FileTreeWriter> writer() {
        return this.writer;
    }
}
