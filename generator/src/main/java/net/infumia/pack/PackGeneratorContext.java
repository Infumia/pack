package net.infumia.pack;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import team.unnamed.creative.ResourcePack;

/**
 * Context for resource pack generator.
 */
public final class PackGeneratorContext {

    private final ResourcePack resourcePack;
    private final Pack pack;
    private final PackReferenceMeta packReference;
    private final Collection<PackReferencePart> packPartReferences;
    private final Path rootDirectory;
    private final Path outputDirectory;
    private final Path outputFile;
    private final ComponentSerializer<?, ?, String> serializer;

    /**
     * Ctor.
     *
     * @param resourcePack       the resource pack. Cannot be null.
     * @param pack               the pack. Cannot be null.
     * @param packReference      the pack file reference. Cannot be null.
     * @param packPartReferences the pack part references. Cannot be null.
     * @param rootDirectory      the root directory of the pack. Cannot be null.
     * @param outputDirectory    the output directory. Can be null.
     * @param outputFile         the output file. Can be null.
     * @param serializer         the serializer to serialize components when needed. Cannot be null.
     */
    PackGeneratorContext(
        final ResourcePack resourcePack,
        final Pack pack,
        final PackReferenceMeta packReference,
        final Collection<PackReferencePart> packPartReferences,
        final Path rootDirectory,
        final Path outputDirectory,
        final Path outputFile,
        final ComponentSerializer<?, ?, String> serializer
    ) {
        this.resourcePack = resourcePack;
        this.pack = pack;
        this.packReference = packReference;
        this.packPartReferences = Collections.unmodifiableCollection(packPartReferences);
        this.rootDirectory = rootDirectory;
        this.outputDirectory = outputDirectory;
        this.outputFile = outputFile;
        this.serializer = serializer;
    }

    /**
     * Returns the resource pack.
     *
     * @return the resource pack.
     */
    public ResourcePack resourcePack() {
        return this.resourcePack;
    }

    /**
     * Returns the pack.
     *
     * @return the pack.
     */
    public Pack pack() {
        return this.pack;
    }

    /**
     * Returns the pack reference.
     *
     * @return the pack reference.
     */
    public PackReferenceMeta packReference() {
        return this.packReference;
    }

    /**
     * Returns the pack part references.
     *
     * @return the pack part references.
     */
    public Collection<PackReferencePart> packPartReferences() {
        return this.packPartReferences;
    }

    /**
     * Returns the root directory.
     *
     * @return the root directory. Can be null.
     */
    public Path rootDirectory() {
        return this.rootDirectory;
    }

    /**
     * Returns the output directory.
     *
     * @return the output directory. Can be null.
     */
    public Path outputDirectory() {
        return this.outputDirectory;
    }

    /**
     * Returns the output file.
     *
     * @return the output file. Can be null.
     */
    public Path outputFile() {
        return this.outputFile;
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
        return new StringJoiner(", ", PackGeneratorContext.class.getSimpleName() + "[", "]")
            .add("resourcePack=" + this.resourcePack)
            .add("pack=" + this.pack)
            .add("packReference=" + this.packReference)
            .add("packPartReferences=" + this.packPartReferences)
            .add("outputDirectory=" + this.outputDirectory)
            .add("outputFile=" + this.outputFile)
            .add("serializer=" + this.serializer)
            .toString();
    }
}
