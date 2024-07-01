package net.infumia.pack;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;

final class GlyphComponentBuilderImpl implements GlyphComponentBuilder {

    private final ResourceProducerSpaces spacesProducer;
    private final int initialPosition;
    private final Component baseComponent;

    private final List<Glyph> glyphs = new ArrayList<>();

    private int previousElementsWidth;

    public GlyphComponentBuilderImpl(
        final ResourceProducerSpaces spacesProducer,
        final int initialPosition,
        final Component baseComponent
    ) {
        this.spacesProducer = spacesProducer;
        this.initialPosition = initialPosition;
        this.baseComponent = baseComponent;
    }

    @Override
    public GlyphComponentBuilder append(
        final PositionType positionType,
        final int position,
        final GlyphAppendable glyph
    ) {
        this.preAppend(positionType, position);

        this.glyphs.add(glyph);
        this.previousElementsWidth += position + glyph.width();

        return this;
    }

    @Override
    public GlyphComponentBuilder append(
        final PositionType positionType,
        final int position,
        final List<? extends GlyphAppendable> glyphList
    ) {
        this.preAppend(positionType, position);
        int width = 0;
        for (final GlyphAppendable glyph : glyphList) {
            this.glyphs.add(glyph);
            width += glyph.width();
        }
        this.previousElementsWidth += position + width;
        return this;
    }

    @Override
    public Component build(final boolean keepInitialPosition) {
        if (keepInitialPosition) {
            this.previousElementsWidth += this.initialPosition;
            if (this.previousElementsWidth != 0) {
                this.glyphs.add(this.spacesProducer.translate((-1) * this.previousElementsWidth));
            }
        }
        Component component = this.baseComponent;
        if (this.initialPosition != 0) {
            component = component.append(
                this.spacesProducer.translate(this.initialPosition).toAdventure()
            );
        }
        for (final Glyph glyph : this.glyphs) {
            component = component.append(glyph.toAdventure());
        }
        return component;
    }

    private void preAppend(final PositionType positionType, final int position) {
        if (positionType == PositionType.ABSOLUTE && this.previousElementsWidth != 0) {
            this.glyphs.add(this.spacesProducer.translate((-1) * this.previousElementsWidth));
            this.previousElementsWidth = 0;
        }
        if (position != 0) {
            this.glyphs.add(this.spacesProducer.translate(position));
        }
    }
}
