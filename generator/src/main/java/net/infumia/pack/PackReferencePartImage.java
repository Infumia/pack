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
        final String namespace = this.namespace == null
            ? context.packReference().defaultNamespace()
            : this.namespace;
        if (namespace == null) {
            throw new IllegalStateException("Pack reference namespace cannot be null!");
        }

        final Path root = context.rootDirectory();

        final String parent;
        if (this.directory == null) {
            parent = "";
        } else {
            parent = root
                .relativize(this.directory)
                .toString()
                .toLowerCase(Locale.ROOT)
                .replace("\\", "/")
                .replace(" ", "_") +
            "/";
        }

        final String key = parent + this.key;
        context
            .pack()
            .with(
                (ResourceIdentifierImage) () -> key,
                ResourceProducers.image(
                    Font.MINECRAFT_DEFAULT,
                    Texture.texture(
                        Key.key(namespace, key + ".png"),
                        Writable.path(root.resolve(parent + this.image))
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
}
