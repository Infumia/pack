package net.infumia.pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;
import net.kyori.adventure.text.serializer.ComponentSerializer;

/**
 * Settings for reading a pack.
 */
public final class PackReaderSettings {

    private final Path root;
    private final FileVisitOption[] visitOptions;
    private final String packReferenceFileName;
    private final Path outputDirectory;
    private final Path outputFile;
    private final ObjectMapper mapper;
    private final ComponentSerializer<?, ?, String> serializer;

    /**
     * Ctor.
     *
     * @param root                  the root path.
     * @param visitOptions          the visit options. Can be null.
     * @param packReferenceFileName the pack reference file name. Cannot be null.
     * @param outputDirectory       the directory name. Can be null.
     * @param outputFile            the zip file name. Can be null.
     * @param mapper                the object mapper to read pack and pack part reference files. Cannot be null.
     * @param serializer            the serializer to serialize components when needed. Cannot be null.
     */
    public PackReaderSettings(
        final Path root,
        final FileVisitOption[] visitOptions,
        final String packReferenceFileName,
        final Path outputDirectory,
        final Path outputFile,
        final ObjectMapper mapper,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this.root = root;
        this.visitOptions = visitOptions;
        this.packReferenceFileName = packReferenceFileName;
        this.outputDirectory = outputDirectory;
        this.outputFile = outputFile;
        this.mapper = mapper;
        this.serializer = serializer;
    }

    /**
     * Ctor.
     *
     * @param root                  the root path.
     * @param packReferenceFileName the pack reference file name. Cannot be null.
     * @param outputDirectory       the directory name. Can be null.
     * @param outputFile            the zip file name. Can be null.
     * @param mapper                the object mapper to read pack and pack part reference files. Cannot be null.
     */
    public PackReaderSettings(
        final Path root,
        final String packReferenceFileName,
        final Path outputDirectory,
        final Path outputFile,
        final ObjectMapper mapper,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this(root, null, packReferenceFileName, outputDirectory, outputFile, mapper, serializer);
    }

    /**
     * Returns the root path.
     *
     * @return the root path.
     */
    public Path root() {
        return this.root;
    }

    /**
     * Returns the visit options.
     *
     * @return the visit options.
     */
    public FileVisitOption[] visitOptions() {
        return this.visitOptions;
    }

    /**
     * Returns the pack reference file name.
     *
     * @return the pack reference file name.
     */
    public String packReferenceFileName() {
        return this.packReferenceFileName;
    }

    /**
     * Returns the output directory.
     *
     * @return the directory output. Can be null.
     */
    public Path outputDirectory() {
        return this.outputDirectory;
    }

    /**
     * Returns the zip file.
     *
     * @return the zip file. Can be null.
     */
    public Path outputFile() {
        return this.outputFile;
    }

    /**
     * Returns the object mapper.
     *
     * @return the object mapper.
     */
    public ObjectMapper mapper() {
        return this.mapper;
    }

    /**
     * Returns the component serializer.
     *
     * @return the component serializer.
     */
    public ComponentSerializer<?, ?, String> serializer() {
        return this.serializer;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReaderSettings.class.getSimpleName() + "[", "]")
            .add("root=" + this.root)
            .add("visitOptions=" + Arrays.toString(this.visitOptions))
            .add("packReferenceFileName='" + this.packReferenceFileName + "'")
            .add("outputDirectory=" + this.outputDirectory)
            .add("outputFile=" + this.outputFile)
            .add("mapper=" + this.mapper)
            .add("serializer=" + this.serializer)
            .toString();
    }
}
