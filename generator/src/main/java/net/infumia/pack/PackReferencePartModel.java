package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
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

    @Override
    public void add(final PackReadContext context, final Pack pack) {
        final Key overriddenItemKey;
        if (this.overriddenNamespace == null) {
            overriddenItemKey = Key.key(this.overriddenKey);
        } else {
            overriddenItemKey = Key.key(this.overriddenNamespace, this.overriddenKey);
        }

        final Key key = this.extractKey(context);
        pack.with(
            FileResources.all(
                this.textures.stream()
                    .map(
                        texture ->
                            FileResources.texture(
                                Texture.texture(
                                    Key.key(key.namespace(), "item/" + this.parentPath() + texture),
                                    this.provideWritableWithParent(context, texture)
                                )
                            )
                    )
                    .collect(Collectors.toList())
            )
        );
        pack.with(
            FileResources.unknown(
                "assets/" + key.namespace() + "/models/" + this.parentPath() + this.model,
                this.provideWritableWithParent(context, this.model)
            )
        );
        pack.with(
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
                            ItemPredicate.customModelData(
                                this.nextCustomModelData(context, this.customModelData)
                            )
                        )
                    )
                    .build()
            )
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReferencePartItem.class.getSimpleName() + "[", "]")
            .add("namespace='" + this.namespace() + "'")
            .add("key='" + this.key() + "'")
            .add("textures=" + this.textures)
            .add("model='" + this.model + "'")
            .add("customModelData=" + this.customModelData)
            .add("overriddenNamespace='" + this.overriddenNamespace + "'")
            .add("overriddenKey='" + this.overriddenKey + "'")
            .toString();
    }
}
