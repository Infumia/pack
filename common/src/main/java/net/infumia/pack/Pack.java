package net.infumia.pack;

import java.util.Collection;
import org.jetbrains.annotations.Contract;
import team.unnamed.creative.ResourcePack;

/**
 * An interface represents a collection of resources and provides methods to manage and manipulate them.
 */
public interface Pack {
    /**
     * Retrieves all file resources in this pack.
     *
     * @return An unmodifiable collection of {@link FileResource} objects.
     */
    Collection<FileResource> all();

    /**
     * Compiles all resources in this pack, if applicable.
     */
    void compileAll();

    /**
     * Adds a resource producer identified by the given ID to this pack.
     *
     * @param id       The identifier for the resource producer. Cannot be null.
     * @param producer The resource producer to add. Cannot be null.
     * @param <T>      The type of the resource producer. Cannot be null.
     * @return This pack instance.
     */
    @Contract("_, _ -> this")
    <T extends ResourceProducer> Pack with(ResourceIdentifier<T> id, T producer);

    /**
     * Adds a file resource to this pack.
     *
     * @param resource The file resource to add. Cannot be null.
     * @return This pack instance.
     */
    @Contract("_ -> this")
    Pack with(FileResource resource);

    /**
     * Adds Mojang-specific spaces resource to this pack.
     * <p>
     * Available after pack format 9.
     *
     * @return This pack instance.
     */
    @Contract("-> this")
    default Pack withMojangSpaces() {
        return this.with(ResourceIdentifiers.SPACES, ResourceProducers.spacesMojang());
    }

    /**
     * Adds the default spaces resource producer to the pack.
     *
     * @return This Pack instance with the default spaces added.
     */
    @Contract("-> this")
    default Pack withBitmapSpaces() {
        return this.with(ResourceIdentifiers.SPACES, ResourceProducers.spacesBitmap());
    }

    /**
     * Retrieves the resource producer associated with the given identifier.
     *
     * @param id  The identifier for the resource producer. Cannot be null.
     * @param <T> The type of the resource producer.
     * @return The resource producer instance.
     * @throws IllegalArgumentException If the identifier is not found in the pack.
     */
    <T extends ResourceProducer> T get(ResourceIdentifier<T> id) throws IllegalArgumentException;

    /**
     * Convenience method to retrieve the Mojang-specific spaces resource producer.
     *
     * @return The Mojang-specific spaces resource producer.
     */
    default ResourceProducerSpaces spaces() {
        return this.get(ResourceIdentifiers.SPACES);
    }

    /**
     * Writes all file resources in this pack to the provided ResourcePack.
     *
     * @param resourcePack The target ResourcePack to write resources to. Cannot be null.
     */
    default void writeAll(final ResourcePack resourcePack) {
        this.all().forEach(resource -> resource.write(resourcePack));
    }
}
