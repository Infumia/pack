package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.texture.Texture;

final class FileResourceTexture implements FileResource {

    final Texture texture;

    FileResourceTexture(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.texture(this.texture);
    }
}
