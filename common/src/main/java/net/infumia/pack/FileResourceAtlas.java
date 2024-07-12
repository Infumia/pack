package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.atlas.Atlas;

final class FileResourceAtlas implements FileResource {

    final Atlas atlas;

    FileResourceAtlas(final Atlas atlas) {
        this.atlas = atlas;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.atlas(this.atlas);
    }
}
