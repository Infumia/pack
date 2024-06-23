package net.infumia.pack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.flattener.FlattenerListener;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

final class Kyori {
    static Collection<ColoredComponentTextPart> toColoredParts(final Component component) {
        final Collection<ColoredComponentTextPart> result = new ArrayList<>();
        ComponentFlattener.basic().flatten(component, new ColoredPartsFlattenerListener(result));
        return result;
    }

    static final class ColoredComponentTextPart {
        private final String text;
        private final TextColor color;

        private ColoredComponentTextPart(final String text, final TextColor color) {
            this.text = text;
            this.color = color;
        }

        public String text() {
            return this.text;
        }

        public TextColor color() {
            return this.color;
        }
    }

    private static final class ColoredPartsFlattenerListener implements FlattenerListener {
        private final Deque<TextColor> colors = new LinkedList<>();
        private final Collection<ColoredComponentTextPart> result;

        private ColoredPartsFlattenerListener(final Collection<ColoredComponentTextPart> result) {
            this.result = result;
        }

        @Override
        public void pushStyle(final Style style) {
            final TextColor color = style.color();
            if (color != null) {
                this.colors.add(color);
            }
        }

        @Override
        public void component(@NotNull final String text) {
            this.result.add(new ColoredComponentTextPart(text, this.current()));
        }

        @Override
        public void popStyle(@NotNull final Style style) {
            final TextColor color = style.color();
            if (color != null) {
                this.colors.removeLast();
            }
        }

        private TextColor current() {
            final TextColor color = this.colors.peekLast();
            return color != null ? color : NamedTextColor.WHITE;
        }
    }

    private Kyori() {
        throw new IllegalStateException("Utility class");
    }
}
