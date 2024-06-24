package net.infumia.pack;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.serialize.ResourcePackWriter;
import team.unnamed.creative.serialize.minecraft.fs.FileTreeWriter;

final class PackWriter {

    private final PackWriterSettings settings;

    PackWriter(final PackWriterSettings settings) {
        this.settings = settings;
    }

    PackGeneratedContext write(final PackGeneratorContext context) {
        final ResourcePackWriter<FileTreeWriter> writer = this.settings.writer();
        final Path outputDirectory = context.outputDirectory();
        final ResourcePack resourcePack = context.resourcePack();
        if (outputDirectory != null) {
            writer.write(FileTreeWriter.directory(outputDirectory.toFile()), resourcePack);
        }
        final Path outputFile = context.outputFile();
        if (outputFile != null) {
            try (
                final ZipOutputStream outputStream = new ZipOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(outputFile))
                )
            ) {
                writer.write(FileTreeWriter.zip(outputStream), resourcePack);
            } catch (final FileNotFoundException e) {
                throw new IllegalStateException(
                    "Failed to write resource pack to zip file: File not found: " + outputFile,
                    e
                );
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return new PackGeneratedContext(resourcePack, context.pack(), outputDirectory, outputFile);
    }
}
