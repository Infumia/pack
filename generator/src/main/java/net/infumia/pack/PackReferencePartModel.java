package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.texture.Texture;

/**
 * Represents a model part of a pack reference.
 */
public final class PackReferencePartModel extends PackReferencePart {

    @JsonProperty
    private String namespace;

    @JsonProperty(required = true)
    private List<String> textures;

    @JsonProperty(required = true)
    private String model;

    private Path directory;

    @Override
    public void add(final PackGeneratorContext context) {
        final Key key = this.extractKey(context);
        context
            .pack()
            .with(
                FileResources.all(
                    this.textures.stream()
                        .map(
                            texture ->
                                FileResources.texture(
                                    Texture.texture(
                                        Key.key(key.namespace(), key.value() + "/" + texture),
                                        Writable.path(
                                            context
                                                .rootDirectory()
                                                .resolve(this.parent(context) + "/" + texture)
                                        )
                                    )
                                )
                        )
                        .collect(Collectors.toList())
                )
            );
        System.out.println(
            "assets/" + key.namespace() + "/models/" + key.value() + "/" + this.model
        );
        context
            .pack()
            .with(
                FileResources.unknown(
                    "assets/" + key.namespace() + "/models/" + key.value() + "/" + this.model,
                    Writable.path(
                        context.rootDirectory().resolve(this.parent(context) + "/" + this.model)
                    )
                )
            );
    }

    @Override
    PackReferencePartModel directory(final Path directory) {
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
        return Key.key(namespace, this.parent(context));
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
                .replace(" ", "_")
        );
    }
}
