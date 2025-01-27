package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.metadata.pack.PackMeta;

final class FileResourcePackMeta implements FileResource {

    final PackMeta meta;

    FileResourcePackMeta(final PackMeta meta) {
        this.meta = meta;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.packMeta(this.meta);
    }
}
