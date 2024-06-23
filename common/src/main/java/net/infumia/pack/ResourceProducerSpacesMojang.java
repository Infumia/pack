package net.infumia.pack;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.font.SpaceFontProvider;

final class ResourceProducerSpacesMojang extends ResourceProducerSpacesAbstract {

    private Collection<FontProvider> fontProviders;

    ResourceProducerSpacesMojang(final Key key) {
        super(key);
    }

    @Override
    public boolean produced() {
        return this.fontProviders != null;
    }

    @Override
    public void produce(final ArbitraryCharacterFactory characterFactory) {
        if (this.fontProviders != null) {
            throw new ResourceAlreadyProducedException();
        }
        this.mapping = new HashMap<>();
        final SpaceFontProvider.Builder fontProviderBuilder = FontProvider.space();
        for (int length = 1; length <= 2048; length *= 2) {
            fontProviderBuilder.advance(this.retrieveCharacter(characterFactory, length), length);
            fontProviderBuilder.advance(
                this.retrieveCharacter(characterFactory, length * (-1)),
                length * (-1)
            );
        }
        this.fontProviders = Collections.singleton(fontProviderBuilder.build());
    }

    @Override
    public Collection<FontProvider> fontProviders() throws ResourceNotProducedException {
        if (this.fontProviders == null) {
            throw new ResourceNotProducedException();
        }
        return this.fontProviders;
    }

    private char retrieveCharacter(
        final ArbitraryCharacterFactory characterFactory,
        final int length
    ) {
        final char character = characterFactory.create();
        this.mapping.put(length, character);
        return character;
    }
}
