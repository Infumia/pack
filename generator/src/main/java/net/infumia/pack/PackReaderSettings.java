package net.infumia.pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import net.kyori.adventure.text.serializer.ComponentSerializer;

/**
 * Settings for reading a pack.
 */
public final class PackReaderSettings {

    private final Path root;
    private final FileVisitOption[] visitOptions;
    private final String packReferenceFileName;
    private final String directoryName;
    private final String zipFileName;
    private final ObjectMapper mapper;
    private final ComponentSerializer<?, ?, String> serializer;

    /**
     * Constructs a new PackReaderSettings.
     *
     * @param root                  the root path.
     * @param visitOptions          the visit options. Can be null.
     * @param packReferenceFileName the pack reference file name. Cannot be null.
     * @param directoryName         the directory name. Can be null.
     * @param zipFileName           the zip file name. Can be null.
     * @param mapper                the object mapper to read pack and pack part reference files. Cannot be null.
     * @param serializer         the serializer to serialize components when needed. Cannot be null.
     */
    public PackReaderSettings(
        final Path root,
        final FileVisitOption[] visitOptions,
        final String packReferenceFileName,
        final String directoryName,
        final String zipFileName,
        final ObjectMapper mapper,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this.root = root;
        this.visitOptions = visitOptions;
        this.packReferenceFileName = packReferenceFileName;
        this.directoryName = directoryName;
        this.zipFileName = zipFileName;
        this.mapper = mapper;
        this.serializer = serializer;
    }

    /**
     * Constructs a new PackReaderSettings.
     *
     * @param root                  the root path.
     * @param packReferenceFileName the pack reference file name. Cannot be null.
     * @param directoryName         the directory name. Can be null.
     * @param zipFileName           the zip file name. Can be null.
     * @param mapper                the object mapper to read pack and pack part reference files. Cannot be null.
     */
    public PackReaderSettings(
        final Path root,
        final String packReferenceFileName,
        final String directoryName,
        final String zipFileName,
        final ObjectMapper mapper,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this(root, null, packReferenceFileName, directoryName, zipFileName, mapper, serializer);
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
     * Returns the directory name.
     *
     * @return the directory name. Can be null.
     */
    public String directoryName() {
        return this.directoryName;
    }

    /**
     * Returns the zip file name.
     *
     * @return the zip file name. Can be null.
     */
    public String zipFileName() {
        return this.zipFileName;
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
}
