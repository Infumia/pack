package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.nio.file.Path;

/**
 * Abstract base class for a pack part reference.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = PackReferencePartImage.class, name = "image") })
public abstract class PackReferencePart {

    /**
     * Adds this part to the given pack generation context.
     *
     * @param context the pack generation context
     */
    public abstract void add(PackGeneratorContext context);

    /**
     * Sets the directory for this pack part reference.
     *
     * @param directory the directory path. Cannot be null.
     * @return the updated pack part reference.
     */
    abstract PackReferencePart directory(Path directory);
}
