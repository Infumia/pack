package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.model.ItemOverride;
import team.unnamed.creative.model.ItemPredicate;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.model.ModelTexture;
import team.unnamed.creative.model.ModelTextures;
import team.unnamed.creative.texture.Texture;

/**
 * Represents a model part of a pack reference.
 */
public final class PackReferencePartModel extends PackReferencePart {

    @JsonProperty
    private String namespace;

    @JsonProperty(required = true)
    private String key;

    @JsonProperty(required = true)
    private List<String> textures;

    @JsonProperty(required = true)
    private String model;

    @JsonProperty(value = "custom-model-data")
    private Integer customModelData;

    @JsonProperty("overridden-namespace")
    private String overriddenNamespace;

    @JsonProperty(value = "overridden-key", required = true)
    private String overriddenKey;

    private Path directory;

    @Override
    public void add(final PackGeneratorContext context) {
        final Key key = this.extractKey(context);
        final Key overriddenItemKey;
        if (this.overriddenNamespace == null) {
            overriddenItemKey = Key.key(this.overriddenKey);
        } else {
            overriddenItemKey = Key.key(this.overriddenNamespace, this.overriddenKey);
        }

        context
            .pack()
            .with(
                FileResources.all(
                    this.textures.stream()
                        .map(
                            texture -> {
                                final String path = this.parent(context) + texture;
                                return FileResources.texture(
                                    Texture.texture(
                                        Key.key(key.namespace(), path),
                                        Writable.path(
                                            context
                                                .rootDirectory()
                                                .resolve(path)
                                        )
                                    )
                                );
                            }
                        )
                        .collect(Collectors.toList())
                )
            );
        final String path = this.parent(context) + this.model;
        context
            .pack()
            .with(
                FileResources.unknown(
                    "assets/" + key.namespace() + "/models/" + path,
                    Writable.path(
                        context.rootDirectory().resolve(path)
                    )
                )
            );
        context
            .pack()
            .with(
                FileResources.model(
                    Model.model()
                        .key(overriddenItemKey)
                        .parent(Model.ITEM_GENERATED)
                        .textures(
                            ModelTextures.builder()
                                .layers(ModelTexture.ofKey(overriddenItemKey))
                                .build()
                        )
                        .overrides(
                            ItemOverride.of(
                                key,
                                ItemPredicate.customModelData(this.customModelData(context))
                            )
                        )
                        .build()
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
        return Key.key(namespace, this.parent(context) + this.key);
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
                .replace(" ", "_") + "/"
        );
    }

    private int customModelData(final PackGeneratorContext context) {
        if (this.customModelData != null) {
            return this.customModelData;
        }

        final Integer offset = context.packReference().customModelDataOffset();
        if (offset == null) {
            throw new IllegalStateException(
                String.format(
                    "Custom model data offset cannot be null when custom-model-data not specified (%s)!",
                    this.extractKey(context)
                )
            );
        }

        final AtomicInteger lastCustomModelData = context.lastCustomModelData();
        if (offset > lastCustomModelData.get()) {
            lastCustomModelData.set(offset);
        }
        return lastCustomModelData.getAndIncrement();
    }
}
