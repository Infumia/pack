package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;

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
     * Returns the parent path of the part.
     *
     * @return the parent path of the part.
     */
    public String parentPath() {
        final String creatorName = this.creator.simplifiedName();
        final int index = creatorName.lastIndexOf('/');
        return index == -1 ? "" : creatorName.substring(0, index + 1);
    }

    /**
     * Extracts the key of the part.
     *
     * @param context the pack generation context.
     * @return the key of the part.
     */
    public Key extractKey(final PackReadContext context) {
        return context.readerSettings().keyExtractor().extract(context, this);
    }

    /**
     * Adds this part to the given pack generation context.
     *
     * @param context the pack generation context to add.
     * @param pack    the pack to add the part to.
     */
    public abstract void add(PackReadContext context, Pack pack);

    /**
     * Prepares the creator of the part.
     *
     * @param creator the creator to prepare.
     */
    void prepareCreator(final Entry creator) {
        this.creator = creator;
    }

    /**
     * Provides a writable for the given path.
     *
     * @param context the pack generation context.
     * @param path    the path to provide the writable for.
     * @return the writable for the given path.
     */
    protected Writable provideWritable(final PackReadContext context, final String path) {
        try {
            return Writable.copyInputStream(
                context.readerSettings().entryProvider().provide(path).asInputStream()
            );
        } catch (final IOException e) {
            throw new RuntimeException("Failed to provide writable for " + path, e);
        }
    }

    /**
     * Provides a writable for the given path with the parent path.
     *
     * @param context the pack generation context.
     * @param path    the path to provide the writable for.
     * @return the writable for the given path with the parent path.
     */
    protected Writable provideWritableWithParent(final PackReadContext context, final String path) {
        return this.provideWritable(context, this.parentPath() + path);
    }

    /**
     * Returns the next custom model data for the given context.
     *
     * @param context                   the pack generation context.
     * @param overriddenCustomModelData the overridden custom model data. Can be null.
     * @return the next custom model data.
     */
    protected int nextCustomModelData(
        final PackReadContext context,
        final Integer overriddenCustomModelData
    ) {
        if (overriddenCustomModelData != null) {
            return overriddenCustomModelData;
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
