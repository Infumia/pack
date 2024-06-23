package net.infumia.pack;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import team.unnamed.creative.font.Font;

final class GlyphSpaces implements GlyphAppendable {

    static final GlyphAppendable DEFAULT = new GlyphSpaces(Font.MINECRAFT_DEFAULT, " ", 4);

    private final Component component;
    private final int length;

    GlyphSpaces(final Component component, final int length) {
        this.component = component;
        this.length = length;
    }

    GlyphSpaces(final Key key, final String text, final int length) {
        this(Component.text(text).font(key), length);
    }

    GlyphSpaces(final Key key, final char[] text, final int length) {
        this(key, new String(text), length);
    }

    @Override
    public Component toAdventure() {
        return this.component;
    }

    @Override
    public int width() {
        return this.length;
    }
}
