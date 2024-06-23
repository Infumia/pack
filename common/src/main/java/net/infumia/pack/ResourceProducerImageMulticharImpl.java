package net.infumia.pack;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;

final class ResourceProducerImageMulticharImpl implements ResourceProducerImageMultichar {

    private final Map<Character, Character> originToChar = new HashMap<>();

    private final Key key;
    private final Texture texture;
    private final TextureProperties properties;
    private final List<String> charactersMapping;

    private Set<FontProvider> fontProviders;
    private BufferedImage image;

    ResourceProducerImageMulticharImpl(
        final Key key,
        final Texture texture,
        final TextureProperties properties,
        final List<String> charactersMapping
    ) {
        this.key = key;
        this.texture = texture;
        this.properties = properties;
        this.charactersMapping = charactersMapping;
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
        final BitMapFontProvider.Builder fontProviderBuilder = FontProvider.bitMap();
        fontProviderBuilder.file(this.texture.key());
        fontProviderBuilder.ascent(this.properties.ascent());
        fontProviderBuilder.height(this.properties.height());
        final List<String> mappingLines = new ArrayList<>();
        for (final String mappingLine : this.charactersMapping) {
            final StringBuilder builder = new StringBuilder();
            for (final char character : mappingLine.toCharArray()) {
                final char arbitraryCharacter = characterFactory.create();
                this.originToChar.put(character, arbitraryCharacter);
                builder.append(arbitraryCharacter);
            }
            mappingLines.add(builder.toString());
        }
        fontProviderBuilder.characters(mappingLines);
        this.fontProviders = Collections.singleton(fontProviderBuilder.build());
    }

    @Override
    public Collection<FontProvider> fontProviders() throws ResourceNotProducedException {
        return this.fontProviders;
    }

    @Override
    public Collection<Texture> textures() throws ResourceNotProducedException {
        return Collections.singleton(this.texture);
    }

    @Override
    public GlyphImagePrepared translate(final char character, final TextColor color)
        throws IllegalArgumentException {
        if (!this.originToChar.containsKey(character)) {
            throw new IllegalArgumentException("Illegal character: " + character);
        }
        int width = 0;
        for (int lineIndex = 0; lineIndex < this.charactersMapping.size(); lineIndex++) {
            final String line = this.charactersMapping.get(lineIndex);
            for (
                int characterIndex = 0;
                characterIndex < line.toCharArray().length;
                characterIndex++
            ) {
                if (line.charAt(characterIndex) == character) {
                    if (this.image == null) {
                        this.cacheImage();
                    }
                    if (this.image == null) {
                        throw new IllegalArgumentException(
                            "Image " + this.texture.key() + " not found"
                        );
                    }
                    final int filePartWidth =
                        this.image.getWidth() / this.charactersMapping.get(0).length();
                    final int filePartHeight =
                        this.image.getHeight() / this.charactersMapping.size();
                    width = (int) Math.ceil(
                        ((double) this.properties.height() / (double) filePartHeight) *
                        Internal.calculateWidth(
                            this.image,
                            filePartWidth * characterIndex,
                            filePartHeight * lineIndex,
                            filePartWidth * (characterIndex + 1),
                            filePartHeight * (lineIndex + 1)
                        )
                    ) +
                    Internal.SEPARATOR_WIDTH;
                    break;
                }
            }
        }

        return new GlyphImagePrepared(this.key, this.originToChar.get(character), width, color);
    }

    private void cacheImage() {
        try {
            this.image = ImageIO.read(new ByteArrayInputStream(this.texture.data().toByteArray()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
