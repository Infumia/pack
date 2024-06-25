package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.Locale;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.texture.Texture;

public final class PackReferencePartImage extends PackReferencePart {

    private final String namespace;
    private final String key;
    private final String image;
    private final int height;
    private final int ascent;

    private Path directory;

    @JsonCreator
    public PackReferencePartImage(
        @JsonProperty("namespace") final String namespace,
        @JsonProperty("key") final String key,
        @JsonProperty("image") final String image,
        @JsonProperty("height") final int height,
        @JsonProperty("ascent") final int ascent
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
        pack.with(
            (ResourceIdentifierImage) () -> this.key,
            ResourceProducers.image(
                Font.MINECRAFT_DEFAULT,
                Texture.texture(
                    Key.key(namespace, parent + this.key + ".png"),
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
}
