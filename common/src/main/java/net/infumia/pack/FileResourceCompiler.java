package net.infumia.pack;

import java.util.Collection;

/**
 * Interface representing a resource pack compiler that compiles resources from multiple producers into file resources.
 */
public interface FileResourceCompiler {
    /**
     * Compiles resources produced by the given collection of {@link ResourceProducer}s into {@link FileResource}s.
     *
     * @param producers the collection of resource producers. Cannot be null.
     * @return an unmodifiable collection of compiled file resources.
     */
    Collection<FileResource> compile(Collection<ResourceProducer> producers);
}
