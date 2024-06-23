package net.infumia.pack;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

final class GlyphImagePrepared implements GlyphAppendable, GlyphColorable {
    private final Key key;
    private final char character;
    private final int width;
    private TextColor color;

    GlyphImagePrepared(final Key key, final char character, final int width, final TextColor color) {
        this.key = key;
        this.character = character;
        this.width = width;
        this.color = color;
    }

    @Override
    public Component toAdventure() {
        return Component.text(this.character).font(this.key).color(this.color == null ? NamedTextColor.BLACK : this.color);
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public TextColor color() {
        return this.color;
    }

    @Override
    public void updateColor(final TextColor color) {
        this.color = color;
    }
}
