package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PackReferencePart {

    private final String key;
    private final String image;

    @JsonCreator
    public PackReferencePart(
        @JsonProperty("key") final String key,
        @JsonProperty("image") final String image
    ) {
        this.key = key;
        this.image = image;
    }

    public String key() {
        return this.key;
    }

    public String image() {
        return this.image;
    }

    public FileResource parseResource() {
        return FileResources.all();
    }
}