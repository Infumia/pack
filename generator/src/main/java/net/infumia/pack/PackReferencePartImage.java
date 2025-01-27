package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

/**
 * Represents an image part of a pack reference.
 */
public final class PackReferencePartImage extends PackReferencePart {

    @JsonProperty(required = true)
    private String image;

    @JsonProperty(required = true)
    private int height;

    @JsonProperty(required = true)
    private int ascent;

    @Override
    public void add(final PackReadContext context, final Pack pack) {
        final Key key = this.extractKey(context);
        pack.with(
            (ResourceIdentifierImage) key::value,
            ResourceProducers.image(
                Font.MINECRAFT_DEFAULT,
                Texture.texture(
                    Internal.toPngKey(key),
                    this.provideWritableWithParent(context, this.image)
                ),
                new TextureProperties(this.height, this.ascent)
            )
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReferencePartImage.class.getSimpleName() + "[", "]")
            .add("namespace='" + this.namespace() + "'")
            .add("key='" + this.key() + "'")
            .add("image='" + this.image + "'")
            .add("height=" + this.height)
            .add("ascent=" + this.ascent)
            .toString();
    }
}
