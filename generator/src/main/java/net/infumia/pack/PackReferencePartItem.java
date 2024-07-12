package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.nio.file.Path;
import java.util.Locale;
import java.util.StringJoiner;
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

    @JsonProperty(value = "custom-model-data", required = true)
    private int customModelData;

    @JsonProperty(required = true)
    private String image;

    @JsonProperty("overridden-namespace")
    private String overriddenNamespace;

    @JsonProperty(value = "overridden-key", required = true)
    private String overriddenKey;

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
                    Key.key(namespace, parent + this.key),
                    overriddenItemKey,
                    Writable.path(root.resolve(parent + this.image)),
                    this.customModelData
                )
            );
    }

    @Override
    PackReferencePartItem directory(final Path directory) {
        this.directory = directory;
        return this;
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
}
