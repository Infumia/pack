package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an item part of a pack reference.
 */
public final class PackReferencePartItem extends PackReferencePart {

    @JsonProperty(value = "custom-model-data")
    private Integer customModelData;

    @JsonProperty(required = true)
    private String image;

    @JsonProperty("overridden-namespace")
    private String overriddenNamespace;

    @JsonProperty(value = "overridden-key", required = true)
    private String overriddenKey;

    @Override
    public void add(final PackGeneratorContext context) {
        /*final Key overriddenItemKey;
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
            );*/
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReferencePartItem.class.getSimpleName() + "[", "]")
            .add("namespace='" + this.namespace() + "'")
            .add("key='" + this.key() + "'")
            .add("customModelData=" + this.customModelData)
            .add("image='" + this.image + "'")
            .add("overriddenNamespace='" + this.overriddenNamespace + "'")
            .add("overriddenKey='" + this.overriddenKey + "'")
            .toString();
    }

    private String parent(final PackGeneratorContext context) {
        return "";
        /*if (this.directory == null) {
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
        );*/
    }

    private int customModelData(final PackGeneratorContext context) {
        if (this.customModelData != null) {
            return this.customModelData;
        }

        final Integer offset = context.packReference().customModelDataOffset();
        /*if (offset == null) {
            throw new IllegalStateException(
                String.format(
                    "Custom model data offset cannot be null when custom-model-data not specified (%s)!",
                    this.extractKey(context)
                )
            );
        }*/

        final AtomicInteger lastCustomModelData = context.lastCustomModelData();
        if (offset > lastCustomModelData.get()) {
            lastCustomModelData.set(offset);
        }
        return lastCustomModelData.getAndIncrement();
    }
}
