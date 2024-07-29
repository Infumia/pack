package net.infumia.pack;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Interface for building glyph components with various positioning and appending capabilities.
 */
public interface GlyphComponentBuilder {
    /**
     * Creates a universal GlyphComponentBuilder with the specified space producer.
     *
     * @param spacesProducer The space producer to use.
     * @return A new GlyphComponentBuilder instance.
     */
    static GlyphComponentBuilder universal(final ResourceProducerSpaces spacesProducer) {
        return new GlyphComponentBuilderImpl(spacesProducer, 0, Component.text(""));
    }

    /**
     * Creates a GUI GlyphComponentBuilder with the specified space producer.
     *
     * @param spacesProducer The space producer to use.
     * @return A new GlyphComponentBuilder instance.
     */
    static GlyphComponentBuilder gui(final ResourceProducerSpaces spacesProducer) {
        return new GlyphComponentBuilderImpl(
            spacesProducer,
            -8,
            Component.text("", NamedTextColor.WHITE)
        );
    }

    /**
     * Creates a custom GlyphComponentBuilder with the specified parameters.
     *
     * @param spacesProducer The space producer to use.
     * @param position       The initial position.
     * @param baseComponent  The base component.
     * @return A new GlyphComponentBuilder instance.
     */
    static GlyphComponentBuilder custom(
        final ResourceProducerSpaces spacesProducer,
        final int position,
        final Component baseComponent
    ) {
        return new GlyphComponentBuilderImpl(spacesProducer, position, baseComponent);
    }

    /**
     * Appends a glyph with a specified position type and position.
     *
     * @param positionType The type of position (absolute or relative).
     * @param position     The position value.
     * @param glyph        The glyph to append.
     * @return This GlyphComponentBuilder instance.
     */
    GlyphComponentBuilder append(PositionType positionType, int position, GlyphAppendable glyph);

    /**
     * Appends a glyph with specified position type and default position (0).
     *
     * @param positionType The type of position (absolute or relative).
     * @param glyph        The glyph to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(
        final PositionType positionType,
        final GlyphAppendable glyph
    ) {
        return this.append(positionType, 0, glyph);
    }

    /**
     * Appends a list of glyphs with a specified position type and position.
     *
     * @param positionType The type of position (absolute or relative).
     * @param position     The position value.
     * @param glyphes      The list of glyphs to append.
     * @return This GlyphComponentBuilder instance.
     */
    GlyphComponentBuilder append(
        PositionType positionType,
        int position,
        List<? extends GlyphAppendable> glyphes
    );

    /**
     * Appends a list of glyphs with specified position type and default position (0).
     *
     * @param positionType The type of position (absolute or relative).
     * @param glyphList    The list of glyphs to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(
        final PositionType positionType,
        final List<? extends GlyphAppendable> glyphList
    ) {
        return this.append(positionType, 0, glyphList);
    }

    /**
     * Appends a glyph with an absolute position type and specified position.
     *
     * @param position The position value.
     * @param glyph    The glyph to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(final int position, final GlyphAppendable glyph) {
        return this.append(PositionType.ABSOLUTE, position, glyph);
    }

    /**
     * Appends a glyph with absolute position type and default position.
     *
     * @param glyph The glyph to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(final GlyphAppendable glyph) {
        return this.append(PositionType.ABSOLUTE, glyph);
    }

    /**
     * Appends a list of glyphs with an absolute position type and specified position.
     *
     * @param position  The position value.
     * @param glyphList The list of glyphs to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(
        final int position,
        final List<? extends GlyphAppendable> glyphList
    ) {
        return this.append(PositionType.ABSOLUTE, position, glyphList);
    }

    /**
     * Appends a list of glyphs with absolute position type and default position.
     *
     * @param glyphList The list of glyphs to append.
     * @return This GlyphComponentBuilder instance.
     */
    default GlyphComponentBuilder append(final List<? extends GlyphAppendable> glyphList) {
        return this.append(PositionType.ABSOLUTE, glyphList);
    }

    /**
     * Builds the component with an option to keep the initial position.
     *
     * @param keepInitialPosition Whether to keep the initial position.
     * @return The built Component.
     */
    Component build(boolean keepInitialPosition);

    /**
     * Builds the component keeping the initial position.
     *
     * @return The built Component.
     */
    default Component build() {
        return this.build(true);
    }

    /**
     * Enum representing the type of position for appending glyphs.
     */
    enum PositionType {
        ABSOLUTE,
        RELATIVE,
    }
}
