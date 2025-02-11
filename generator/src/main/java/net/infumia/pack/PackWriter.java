package net.infumia.pack;

import java.nio.file.Path;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;

final class PackWriter {

    private final PackWriterSettings settings;

    PackWriter(final PackWriterSettings settings) {
        this.settings = settings;
    }

    PackWriteContext write(final ResourcePack resourcePack) {
        final MinecraftResourcePackWriter writer = this.settings.writer();
        final Path outputDirectory = this.settings.outputDirectory();
        if (outputDirectory != null) {
            writer.writeToDirectory(outputDirectory.toFile(), resourcePack);
        }
        final Path outputFile = this.settings.outputFile();
        if (outputFile != null) {
            writer.writeToZipFile(outputFile, resourcePack);
        }
        return new PackWriteContext(resourcePack, outputDirectory, outputFile);
    }
}
