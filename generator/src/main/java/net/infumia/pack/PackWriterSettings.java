package net.infumia.pack;

import java.util.StringJoiner;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;

/**
 * Settings for writing a resource pack.
 */
public final class PackWriterSettings {

    private final MinecraftResourcePackWriter writer;

    /**
     * Ctor.
     *
     * @param writer the resource pack writer. Cannot be null.
     */
    public PackWriterSettings(final MinecraftResourcePackWriter writer) {
        this.writer = writer;
    }

    /**
     * Returns the resource pack writer.
     *
     * @return the resource pack writer.
     */
    public MinecraftResourcePackWriter writer() {
        return this.writer;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackWriterSettings.class.getSimpleName() + "[", "]")
            .add("writer=" + this.writer)
            .toString();
    }
}
