package net.infumia.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;

final class ResourceProducerLanguageImpl implements ResourceProducerLanguage {

    private final Key key;
    private final Texture texture;
    private final Map<TextureProperties, ResourceProducerImageMultichar> propertiesToMultichar;

    private List<FontProvider> fontProviders;

    ResourceProducerLanguageImpl(
        final Key key,
        final Texture texture,
        final List<TextureProperties> propertiesList,
        final List<String> charactersMapping
    ) {
        this.key = key;
        this.texture = texture;
        this.propertiesToMultichar = propertiesList
            .stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    properties -> Language.multichar(key, texture, properties, charactersMapping)
                )
            );
    }

    @NotNull
    @Override
    public Key key() {
        return this.key;
    }

    @Override
    public boolean produced() {
        return this.fontProviders != null;
    }

    @Override
    public void produce(final ArbitraryCharacterFactory characterFactory)
        throws ResourceAlreadyProducedException {
        if (this.fontProviders != null) {
            throw new ResourceAlreadyProducedException();
        }
        this.fontProviders = new ArrayList<>();
        this.propertiesToMultichar.values()
            .forEach(multichar -> {
                multichar.produce(characterFactory);
                this.fontProviders.addAll(multichar.fontProviders());
            });
    }

    @Override
    public Collection<FontProvider> fontProviders() throws ResourceNotProducedException {
        return this.fontProviders;
    }

    @Override
    public Collection<Texture> textures() throws ResourceNotProducedException {
        return Collections.singletonList(this.texture);
    }

    private ResourceProducerImageMultichar getGlyphCollection(final int height, final int ascent) {
        final TextureProperties properties = new TextureProperties(height, ascent);
        final ResourceProducerImageMultichar glyphCollection =
            this.propertiesToMultichar.get(properties);
        if (glyphCollection == null) {
            throw new IllegalArgumentException("Font with " + properties + " not found");
        }
        return glyphCollection;
    }

    @Override
    public GlyphAppendable translate(
        final int height,
        final int ascent,
        final char character,
        final TextColor color
    ) throws IllegalArgumentException {
        return this.getGlyphCollection(height, ascent).translate(character, color);
    }

    @Override
    public List<GlyphAppendable> translate(
        final int height,
        final int ascent,
        final String text,
        final TextColor color
    ) throws IllegalArgumentException {
        return Collections.unmodifiableList(
            this.getGlyphCollection(height, ascent).translate(text, color)
        );
    }

    @Override
    public List<GlyphAppendable> translate(
        final int height,
        final int ascent,
        final Component component
    ) throws IllegalArgumentException {
        final ResourceProducerImageMultichar collection = this.getGlyphCollection(height, ascent);
        final Collection<Kyori.ColoredComponentTextPart> textAndColors = Kyori.toColoredParts(
            component
        );
        return Collections.unmodifiableList(
            textAndColors
                .stream()
                .map(parts -> collection.translate(parts.text(), parts.color()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
        );
    }
}
