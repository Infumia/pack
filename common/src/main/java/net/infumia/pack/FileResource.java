package net.infumia.pack;

import team.unnamed.creative.ResourcePack;

/**
 * Represents a file resource that can be written to a resource pack.
 */
public interface FileResource {
    /**
     * Writes the file resource to the specified resource pack.
     *
     * @param pack the resource pack to which the file resource will be written. Cannot be null.
     */
    void write(ResourcePack pack);
}
