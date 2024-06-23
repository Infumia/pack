package net.infumia.pack;

import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.model.Model;

final class FileResourceModel implements FileResource {
    private final Model model;

    FileResourceModel(final Model model) {
        this.model = model;
    }

    @Override
    public void write(final ResourcePack pack) {
        pack.model(this.model);
    }
}
