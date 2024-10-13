package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.nio.file.Path;
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

    /**
     * Adds this part to the given pack generation context.
     *
     * @param context the pack generation context to add.
     */
    public abstract void add(PackGeneratorContext context);

    /**
     * Sets the directory for this pack part reference.
     *
     * @param directory the directory path. Cannot be null.
     * @return the updated pack part reference.
     */
    abstract PackReferencePart directory(Path directory);

    /**
     * Returns the key of the part.
     *
     * @param context the context to extract.
     *
     * @return the comparable key.
     */
    abstract Key extractKey(PackGeneratorContext context);
}
