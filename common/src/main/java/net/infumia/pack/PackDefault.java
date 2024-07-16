package net.infumia.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class PackDefault implements Pack {

    private final FileResourceCompiler compiler;
    private final FileResourceMerger merger;
    private final Map<String, ResourceProducer> raw = new HashMap<>();
    private final Map<String, ResourceProducer> compiled = new HashMap<>();
    private final List<FileResource> resources = new ArrayList<>();

    PackDefault(final FileResourceCompiler compiler, final FileResourceMerger merger) {
        this.compiler = compiler;
        this.merger = merger;
    }

    @Override
    public Collection<FileResource> all() {
        if (!this.raw.isEmpty()) {
            this.compileAll();
        }
        return this.resources;
    }

    @Override
    public void compileAll() {
        final Collection<FileResource> resources = this.compiler.compile(this.raw.values());
        this.resources.addAll(resources);
        final Collection<FileResource> mergedResources = this.merger.merge(this.resources);
        this.resources.clear();
        this.resources.addAll(mergedResources);
        this.compiled.putAll(this.raw);
        this.raw.clear();
    }

    @Override
    public <T extends ResourceProducer> Pack with(
        final ResourceIdentifier<T> id,
        final T producer
    ) {
        if (this.raw.containsKey(id.key()) || this.compiled.containsKey(id.key())) {
            throw new IllegalArgumentException(
                "Producer with " + id.key() + " identifier already registered"
            );
        }
        this.raw.put(id.key(), producer);
        return this;
    }

    @Override
    public Pack with(final FileResource resource) {
        this.resources.add(resource);
        return this;
    }

    @Override
    public <T extends ResourceProducer> T get(final ResourceIdentifier<T> id)
        throws IllegalArgumentException {
        if (!this.compiled.containsKey(id.key())) {
            throw new IllegalArgumentException(
                "Producer with " + id.key() + " identifier is not compiled"
            );
        }
        final ResourceProducer producer = this.compiled.get(id.key());
        if (!id.type().isAssignableFrom(producer.getClass())) {
            throw new IllegalArgumentException("Wrong producer type");
        }
        //noinspection unchecked
        return (T) producer;
    }
}
