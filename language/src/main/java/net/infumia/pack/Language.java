package net.infumia.pack;

import java.util.List;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.texture.Texture;

public final class Language {

    /**
     * Creates a {@link ResourceProducerLanguage} instance with the specified parameters.
     *
     * @param fontKey           The key associated with the font. Cannot be null.
     * @param texture           The texture of the glyph collection. Cannot be null.
     * @param propertiesList    The list of texture properties for the glyphs. Cannot be null.
     * @param charactersMapping The list of character mappings for the glyphs. Cannot be null.
     * @return A {@link ResourceProducerLanguage} instance.
     */
    public static ResourceProducerLanguage language(
        final Key fontKey,
        final Texture texture,
        final List<TextureProperties> propertiesList,
        final List<String> charactersMapping
    ) {
        return new ResourceProducerLanguageImpl(
            fontKey,
            texture,
            propertiesList,
            charactersMapping
        );
    }

    private Language() {
        throw new IllegalStateException("Utility class");
    }
}
