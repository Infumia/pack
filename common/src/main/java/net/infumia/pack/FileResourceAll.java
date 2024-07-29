package net.infumia.pack;

import java.util.Collection;
import team.unnamed.creative.ResourcePack;

final class FileResourceAll implements FileResource {

    final Collection<FileResource> resources;

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
