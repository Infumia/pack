package net.infumia.pack;

import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

final class GlyphImageColoredImpl implements GlyphImageColored {
    private final GlyphImage original;
    private TextColor color;

    GlyphImageColoredImpl(final GlyphImage original, final TextColor color) {
        this.original = original;
        this.color = color;
    }

    @Override
    public Component toAdventure() throws ResourceNotProducedException {
        Component component = this.original.toAdventure();
        if (this.color != null) {
            component = component.color(this.color);
        }
        return component;
    }

    @Override
    public int width() {
        return this.original.width();
    }

    @Override
    public TextColor color() {
        return this.color;
    }

    @Override
    public void updateColor(final TextColor color) {
        this.color = color;
    }

    @Override
    public GlyphImage original() {
        return this.original;
    }
}
