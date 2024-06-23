package net.infumia.pack;

/**
 * Represents a resource identifier associated with a specific type of resource producer.
 *
 * @param <T> The type of resource producer associated with this identifier.
 */
public interface ResourceIdentifier<T extends ResourceProducer> {
    /**
     * Returns the key associated with this resource identifier.
     *
     * @return The key as a string.
     */
    String key();

    /**
     * Returns the class object representing the type of resource producer associated with this identifier.
     *
     * @return The class object of type T.
     */
    Class<T> type();
}
