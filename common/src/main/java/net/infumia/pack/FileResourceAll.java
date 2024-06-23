package net.infumia.pack;

import team.unnamed.creative.ResourcePack;

import java.util.Collection;

final class FileResourceAll implements FileResource {
    private final Collection<FileResource> resources;

    FileResourceAll(final Collection<FileResource> resources) {
        this.resources = resources;
    }

    @Override
    public void write(final ResourcePack pack) {
        for (final FileResource resource : this.resources) {
            resource.write(pack);
        }
    }
}
