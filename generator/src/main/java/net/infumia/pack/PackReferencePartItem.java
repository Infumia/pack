package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;

/**
 * Represents an item part of a pack reference.
 */
public final class PackReferencePartItem extends PackReferencePart {

    @JsonProperty
    private String namespace;

    @JsonProperty(required = true)
    private String key;

    @JsonProperty(value = "custom-model-data")
    private Integer customModelData;

    @JsonProperty(required = true)
    private String image;

    @JsonProperty("overridden-namespace")
    private String overriddenNamespace;

    @JsonProperty(value = "overridden-key", required = true)
    private String overriddenKey;

    private Path directory;

    @Override
    public void add(final PackGeneratorContext context) {
        final Key overriddenItemKey;
        if (this.overriddenNamespace == null) {
            overriddenItemKey = Key.key(this.overriddenKey);
        } else {
            overriddenItemKey = Key.key(this.overriddenNamespace, this.overriddenKey);
        }

        context
            .pack()
            .with(
                ResourceProducers.item(
                    this.extractKey(context),
                    overriddenItemKey,
                    Writable.path(
                        context.rootDirectory().resolve(this.parent(context) + this.image)
                    ),
                    this.customModelData(context)
                )
            );
    }

    @Override
    PackReferencePartItem directory(final Path directory) {
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
        return new StringJoiner(", ", PackReferencePartItem.class.getSimpleName() + "[", "]")
            .add("namespace='" + this.namespace + "'")
            .add("key='" + this.key + "'")
            .add("customModelData=" + this.customModelData)
            .add("image='" + this.image + "'")
            .add("overriddenNamespace='" + this.overriddenNamespace + "'")
            .add("overriddenKey='" + this.overriddenKey + "'")
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

    private int customModelData(final PackGeneratorContext context) {
        if (this.customModelData != null) {
            return this.customModelData;
        }

        final Integer offset = context.packReference().customModelDataOffset();
        if (offset == null) {
            throw new IllegalStateException(
                "Custom model data offset cannot be null when custom-model-data not specified!"
            );
        }

        final AtomicInteger lastCustomModelData = context.lastCustomModelData();
        if (offset > lastCustomModelData.get()) {
            lastCustomModelData.set(offset);
        }
        return lastCustomModelData.getAndIncrement();
    }
}
