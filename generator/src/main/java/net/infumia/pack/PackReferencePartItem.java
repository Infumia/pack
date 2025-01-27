package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;
import net.kyori.adventure.key.Key;

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
    public void add(final PackReadContext context, final Pack pack) {
        final Key overriddenItemKey;
        if (this.overriddenNamespace == null) {
            overriddenItemKey = Key.key(this.overriddenKey);
        } else {
            overriddenItemKey = Key.key(this.overriddenNamespace, this.overriddenKey);
        }

        pack.with(
            ResourceProducers.item(
                this.extractKey(context),
                overriddenItemKey,
                this.provideWritable(context, this.image),
                this.nextCustomModelData(context, this.customModelData)
            )
        );
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
}
