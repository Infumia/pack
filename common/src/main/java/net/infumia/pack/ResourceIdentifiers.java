package net.infumia.pack;

/**
 * Utility class containing resource identifiers for various resource types.
 */
public final class ResourceIdentifiers {

    /**
     * Resource identifier for spaces resource producer.
     */
    public static final ResourceIdentifier<ResourceProducerSpaces> SPACES =
        new ResourceIdentifierImpl<>("spaces", ResourceProducerSpaces.class);

    private ResourceIdentifiers() {
        throw new IllegalStateException("Utility class");
    }
}
