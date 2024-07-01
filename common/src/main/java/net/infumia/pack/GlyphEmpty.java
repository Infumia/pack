package net.infumia.pack;

import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.text.Component;

final class GlyphEmpty implements GlyphAppendable {

    static final GlyphEmpty INSTANCE = new GlyphEmpty();

    private GlyphEmpty() {}

    @Override
    public Component toAdventure() throws ResourceNotProducedException {
        return Component.text("");
    }

    @Override
    public int width() {
        return 0;
    }
}
