package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.metadata.pack.PackMeta;

/**
 * Represents a reference to a pack with format constraints and a description.
 */
public final class PackReferenceMeta {

    private final Integer format;
    private final Integer minimumFormat;
    private final Integer maximumFormat;
    private final String description;
    private final boolean addBlankSlot;
    private final Integer blankSlotCustomModelData;
    private final boolean addSpaces;
    private final String defaultNamespace;
    private final Integer customModelDataOffset;

    /**
     * Ctor.
     *
     * @param format                   the pack format. Can be null
     * @param minimumFormat            the minimum pack format. Can be null
     * @param maximumFormat            the maximum pack format. Can be null
     * @param description              the description of the pack
     * @param addBlankSlot             adds the {@link BlankSlot} resources.
     * @param blankSlotCustomModelData the custom model data of the blank slot paper model.
     * @param addSpaces                adds the {@link ResourceProducers#spacesBitmap()} or {@link ResourceProducers#spacesMojang()} based on the pack format.
     * @param defaultNamespace         the default namespace that will be used when a {@link PackReferencePart} does not have a namespace. Can be null.
     * @param customModelDataOffset    the custom model data offset to be used when automatically incrementing the next custom model data for items.
     */
    @JsonCreator
    public PackReferenceMeta(
        @JsonProperty("format") final Integer format,
        @JsonProperty("minimum-format") final Integer minimumFormat,
        @JsonProperty("maximum-format") final Integer maximumFormat,
        @JsonProperty("description") final String description,
        @JsonProperty("add-blank-slot") final boolean addBlankSlot,
        @JsonProperty("blank-slot-custom-model-data") final Integer blankSlotCustomModelData,
        @JsonProperty("add-spaces") final boolean addSpaces,
        @JsonProperty("default-namespace") final String defaultNamespace,
        @JsonProperty("custom-model-data-offset") final Integer customModelDataOffset
    ) {
        this.format = format;
        this.minimumFormat = minimumFormat;
        this.maximumFormat = maximumFormat;
        this.description = description;
        this.addBlankSlot = addBlankSlot;
        this.blankSlotCustomModelData = blankSlotCustomModelData;
        this.addSpaces = addSpaces;
        this.defaultNamespace = defaultNamespace;
        this.customModelDataOffset = customModelDataOffset;
    }

    /**
     * Returns whether the {@link BlankSlot} resources should be added.
     *
     * @return {@code true} if the {@link BlankSlot} resources should be added, {@code false} otherwise
     */
    public boolean addBlankSlot() {
        return this.addBlankSlot;
    }

    /**
     * Returns the custom model data of the blank slot.
     *
     * @return the custom model data of the blank slot.
     */
    public Integer blankSlotCustomModelData() {
        return blankSlotCustomModelData;
    }

    /**
     * Returns whether spaces should be added.
     *
     * @return {@code true} if spaces should be added, {@code false} otherwise
     */
    public boolean addSpaces() {
        return this.addSpaces;
    }

    /**
     * Returns the default namespace.
     *
     * @return the default namespace. Can be null.
     */
    public String defaultNamespace() {
        return this.defaultNamespace;
    }

    /**
     * Returns the custom model data offset.
     *
     * @return the custom model data offset. Can be null
     */
    public Integer customModelDataOffset() {
        return customModelDataOffset;
    }

    /**
     * Parses the pack formats to a {@link PackMeta} object.
     *
     * @param serializer the component serializer. Cannot be null.
     * @return the generated {@link PackMeta}.
     * @throws IllegalStateException if none of the {@link #format},{@link #minimumFormat}, and {@link #maximumFormat} are provided.
     */
    public PackMeta parsePackMeta(final ComponentSerializer<?, ?, String> serializer) {
        if (this.format == null && this.minimumFormat == null && this.maximumFormat == null) {
            throw new IllegalStateException(
                "At least one of format, minimumFormat and maximumFormat must be provided!"
            );
        }

        final int minimumFormat;
        if (this.format != null) {
            minimumFormat = this.format;
        } else if (this.minimumFormat != null) {
            minimumFormat = this.minimumFormat;
        } else {
            minimumFormat = this.maximumFormat;
        }

        final int maximumFormat;
        if (this.format != null) {
            maximumFormat = this.format;
        } else if (this.maximumFormat != null) {
            maximumFormat = this.maximumFormat;
        } else {
            maximumFormat = minimumFormat;
        }

        return PackMeta.of(
            PackFormat.format(minimumFormat, minimumFormat, maximumFormat),
            serializer.deserialize(this.description)
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PackReferenceMeta.class.getSimpleName() + "[", "]")
            .add("format=" + this.format)
            .add("minimumFormat=" + this.minimumFormat)
            .add("maximumFormat=" + this.maximumFormat)
            .add("description='" + this.description + "'")
            .add("addBlankSlot=" + this.addBlankSlot)
            .add("addSpaces=" + this.addSpaces)
            .toString();
    }
}
