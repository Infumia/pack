package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.base.Writable;

final class FileResourceUnknown implements FileResource {

    final String path;
    final Writable writable;

    FileResourceUnknown(final String path, final Writable writable) {
        this.path = path;
        this.writable = writable;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.unknownFile(this.path, this.writable);
    }
}
