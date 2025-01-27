package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.kyori.adventure.key.Key;

/**
 * Abstract base class for a pack part reference.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = PackReferencePartImage.class, name = "image"),
        @JsonSubTypes.Type(value = PackReferencePartItem.class, name = "item"),
        @JsonSubTypes.Type(value = PackReferencePartModel.class, name = "model"),
    }
)
public abstract class PackReferencePart {

    @JsonProperty
    private String namespace;

    @JsonProperty(required = true)
    private String key;

    private Entry creator;

    /**
     * Returns the namespace of the part.
     *
     * @return the namespace. Can be null.
     */
    public String namespace() {
        return this.namespace;
    }

    /**
     * Returns the key of the part.
     *
     * @return the key. Cannot be null.
     */
    public String key() {
        return this.key;
    }

    /**
     * Returns the creator of the part.
     *
     * @return the creator. Cannot be null.
     */
    public Entry creator() {
        return this.creator;
    }

    /**
     * Extracts the key of the part.
     *
     * @param context the pack generation context.
     * @return the key of the part.
     */
    public Key extractKey(final PackGeneratorContext context) {
        return context.readerSettings().keyExtractor().extract(context, this);
    }

    /**
     * Adds this part to the given pack generation context.
     *
     * @param context the pack generation context to add.
     */
    public abstract void add(PackGeneratorContext context);

    /**
     * Prepares the creator of the part.
     *
     * @param creator the creator to prepare.
     */
    void prepareCreator(final Entry creator) {
        this.creator = creator;
    }
}
