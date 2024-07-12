package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.font.Font;

final class FileResourceFont implements FileResource {

    final Font font;

    FileResourceFont(final Font font) {
        this.font = font;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.font(this.font);
    }
}
