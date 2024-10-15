package net.infumia.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;

final class ResourceProducerSpacesBitmap extends ResourceProducerSpacesAbstract {

    private final Key textureKey;
    private final Writable writable;

    private List<Texture> textures;
    private List<FontProvider> fontProviders;

    ResourceProducerSpacesBitmap(final Key fontKey, final Key textureKey, final Writable writable) {
        super(fontKey);
        this.textureKey = Internal.toTextureKey(textureKey);
        this.writable = writable;
    }

    @Override
    public boolean produced() {
        return this.textures != null;
    }

    @Override
    public void produce(final ArbitraryCharacterFactory characterFactory) {
        if (this.textures != null) {
            throw new ResourceAlreadyProducedException();
        }
        this.mapping = new HashMap<>();
        final List<FontProvider> fontProviders = new ArrayList<>();
        for (int length = 1; length <= 2048; length *= 2) {
            fontProviders.add(this.prepareBuilder(characterFactory, length).build());
            fontProviders.add(this.prepareBuilder(characterFactory, length * (-1)).build());
        }
        this.textures = Collections.singletonList(Texture.texture(this.textureKey, this.writable));
        this.fontProviders = fontProviders;
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
        if (this.textures == null) {
            throw new ResourceNotProducedException();
        }
        return this.textures;
    }

    private BitMapFontProvider.Builder prepareBuilder(
        final ArbitraryCharacterFactory characterFactory,
        final int length
    ) {
        final BitMapFontProvider.Builder builder = FontProvider.bitMap();
        final char character = characterFactory.create();
        builder.characters(String.valueOf(character));
        builder.file(this.textureKey);
        if (length > 0) {
            builder.height(length - 1);
        } else {
            builder.height(length - 2);
            builder.ascent(Short.MIN_VALUE);
        }
        this.mapping.put(length, character);
        return builder;
    }
}
