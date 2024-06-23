package net.infumia.pack;

import net.infumia.pack.exception.ResourceAlreadyProducedException;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Keyed;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.part.ResourcePackPart;
import team.unnamed.creative.texture.Texture;

import java.util.Collection;
import java.util.Collections;

/**
 * Interface representing a producer of {@link ResourcePackPart}, identified by a unique key.
 */
public interface ResourceProducer extends Keyed {
    /**
     * Checks if the resource has been produced.
     *
     * @return {@code true} if the resource has been produced, {@code false} otherwise.
     */
    boolean produced();

    /**
     * Produces the resource using the provided {@link ArbitraryCharacterFactory}.
     *
     * @param characterFactory the character factory used to produce the resource. Cannot be null.
     * @throws ResourceAlreadyProducedException if the resource has already been produced.
     */
    void produce(ArbitraryCharacterFactory characterFactory) throws ResourceAlreadyProducedException;

    /**
     * Retrieves the collection of {@link FontProvider} instances associated with the produced resource.
     *
     * @return an unmodifiable collection of font providers.
     * @throws ResourceNotProducedException if the resource has not been produced.
     */
    Collection<FontProvider> fontProviders() throws ResourceNotProducedException;

    /**
     * Retrieves the collection of {@link Texture} instances associated with the produced resource.
     *
     * @return a unmodifiable collection of textures.
     * @throws ResourceNotProducedException if the resource has not been produced.
     */
    default Collection<Texture> textures() throws ResourceNotProducedException {
        return Collections.emptyList();
    }
}
