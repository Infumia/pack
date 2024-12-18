package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.Locale;
import java.util.StringJoiner;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

/**
 * Represents an image part of a pack reference.
 */
public final class PackReferencePartImage extends PackReferencePart {

    @JsonProperty
    private String namespace;

    @JsonProperty(required = true)
    private String key;

    @JsonProperty(required = true)
    private String image;

    @JsonProperty(required = true)
    private int height;

    @JsonProperty(required = true)
    private int ascent;

    private Path directory;

    @Override
    public void add(final PackGeneratorContext context) {
        final Key key = this.extractKey(context);
        context
            .pack()
            .with(
                (ResourceIdentifierImage) key::value,
                ResourceProducers.image(
                    Font.MINECRAFT_DEFAULT,
                    Texture.texture(
                        Key.key(key.namespace(), key.value() + ".png"),
                        Writable.path(
                            context.rootDirectory().resolve(this.parent(context) + this.image)
                        )
                    ),
                    new TextureProperties(this.height, this.ascent)
                )
            );
    }

    @Override
    PackReferencePartImage directory(final Path directory) {
        this.directory = directory;
        return this;
    }

    @Override
    Key extractKey(final PackGeneratorContext context) {
        final String namespace = this.namespace == null
            ? context.packReference().defaultNamespace()
            : this.namespace;
        if (namespace == null) {
            throw new IllegalStateException("Pack reference namespace cannot be null!");
        }
        return Key.key(namespace, this.parent(context) + this.key);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReferencePartImage.class.getSimpleName() + "[", "]")
            .add("namespace='" + this.namespace + "'")
            .add("key='" + this.key + "'")
            .add("image='" + this.image + "'")
            .add("height=" + this.height)
            .add("ascent=" + this.ascent)
            .add("directory=" + this.directory)
            .toString();
    }

    private String parent(final PackGeneratorContext context) {
        if (this.directory == null) {
            return "";
        }
        return (
            context
                .rootDirectory()
                .relativize(this.directory)
                .toString()
                .toLowerCase(Locale.ROOT)
                .replace("\\", "/")
                .replace(" ", "_") +
            "/"
        );
    }
}
