package net.infumia.pack;

import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.BitMapFontProvider;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

final class ResourceProducerSpacesDefault extends ResourceProducerSpacesAbstract {
    private final Key textureKey;
    private final Writable writable;

    private Set<Texture> textures;
    private Set<FontProvider> fontProviders;

    ResourceProducerSpacesDefault(final Key fontKey, final Key textureKey, final Writable writable) {
        super(fontKey);
        this.textureKey = textureKey;
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
        final Set<FontProvider> fontProviders = new HashSet<>();
        for (int length = 1; length <= 2048; length *= 2) {
            fontProviders.add(this.prepareBuilder(characterFactory, length).build());
            fontProviders.add(this.prepareBuilder(characterFactory, length * (-1)).build());
        }
        this.textures = Collections.singleton(Texture.texture(this.textureKey, this.writable));
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

    private BitMapFontProvider.Builder prepareBuilder(final ArbitraryCharacterFactory characterFactory, final int length) {
        final BitMapFontProvider.Builder fontProviderBuilder = FontProvider.bitMap();
        final char character = characterFactory.create();
        fontProviderBuilder.characters(String.valueOf(character));
        fontProviderBuilder.file(this.textureKey);
        if (length > 0) {
            fontProviderBuilder.height(length - 1);
            fontProviderBuilder.ascent(0);
        } else {
            fontProviderBuilder.height(length - 2);
            fontProviderBuilder.ascent(-32768);
        }
        this.mapping.put(length, character);
        return fontProviderBuilder;
    }
}
