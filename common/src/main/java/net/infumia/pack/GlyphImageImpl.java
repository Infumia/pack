package net.infumia.pack;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.imageio.ImageIO;
import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;

final class GlyphImageImpl implements GlyphImage {

    private final Key key;
    private final Texture texture;
    private final TextureProperties properties;

    private Character character;
    private Set<FontProvider> fontProviders;

    private int width = -1;

    GlyphImageImpl(final Key key, final Texture texture, final TextureProperties properties) {
        this.key = key;
        this.texture = texture;
        this.properties = properties;
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
        if (this.fontProviders != null || this.character != null) {
            throw new ResourceAlreadyProducedException();
        }
        this.character = characterFactory.create();
        final BitMapFontProvider.Builder fontProviderBuilder = FontProvider.bitMap();
        fontProviderBuilder.characters(String.valueOf(this.character));
        fontProviderBuilder.file(this.texture.key());
        fontProviderBuilder.ascent(this.properties.ascent());
        fontProviderBuilder.height(this.properties.height());
        this.fontProviders = Collections.singleton(fontProviderBuilder.build());
    }

    @Override
    public Collection<FontProvider> fontProviders() throws ResourceNotProducedException {
        if (this.fontProviders == null) {
            throw new ResourceNotProducedException();
        }
        return this.fontProviders;
    }

    @Override
    public Collection<Texture> textures() throws ResourceNotProducedException {
        return Collections.singleton(this.texture);
    }

    @Override
    public int width() {
        if (this.width != -1) {
            return this.width;
        }
        try {
            final BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(this.texture.data().toByteArray())
            );
            final int fileHeight = image.getHeight();
            this.width = (int) Math.ceil(
                ((double) this.properties.height() / (double) fileHeight) *
                Internal.calculateWidth(image)
            ) +
            Internal.SEPARATOR_WIDTH;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return this.width;
    }

    @Override
    public char character() throws ResourceNotProducedException {
        if (this.character == null) {
            throw new ResourceNotProducedException();
        }
        return this.character;
    }

    @Override
    public Texture texture() {
        return this.texture;
    }
}
