package net.infumia.pack;

import java.util.Collection;
import team.unnamed.creative.atlas.Atlas;
import team.unnamed.creative.model.Model;

/**
 * Represents a file resource merger that can merge a collection of file resources.
 */
public interface FileResourceMerger {
    /**
     * Merges a collection of {@link FileResources} into a unified resource.
     * <p>
     * Tries to merge duplicate {@link Atlas}s and {@link Model}s.
     *
     * @param resources the collection of {@link FileResources} to merge. Cannot be null.
     *
     * @return the merged collection of {@link FileResource}s. Cannot be null.
     */
    Collection<FileResource> merge(Collection<FileResource> resources);
}
