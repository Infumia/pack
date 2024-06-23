package net.infumia.pack;

final class ResourceIdentifierImpl<T extends ResourceProducer> implements ResourceIdentifier<T> {
    private final String id;
    private final Class<T> type;

    ResourceIdentifierImpl(final String id, final Class<T> type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public Class<T> type() {
        return this.type;
    }

    @Override
    public String key() {
        return this.id;
    }
}
