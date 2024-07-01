package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    private final String namespace;
    private final String key;
    private final String image;
    private final int height;
    private final int ascent;

    private Path directory;

    /**
     * Ctor.
     *
     * @param namespace the namespace of the image. Can be null
     * @param key       the key for the image. Cannot be null.
     * @param image     the image file name. Cannot be null.
     * @param height    the height of the image.
     * @param ascent    the ascent value of the image.
     */
    @JsonCreator
    public PackReferencePartImage(
        @JsonProperty("namespace") final String namespace,
        @JsonProperty(value = "key", required = true) final String key,
        @JsonProperty(value = "image", required = true) final String image,
        @JsonProperty(value = "height", required = true) final int height,
        @JsonProperty(value = "ascent", required = true) final int ascent
    ) {
        this.namespace = namespace;
        this.key = key;
        this.image = image;
        this.height = height;
        this.ascent = ascent;
    }

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

        final Pack pack = context.pack();
        final String key = parent + this.key;
        pack.with(
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
