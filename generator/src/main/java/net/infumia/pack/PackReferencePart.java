package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PackReferencePart {

    private final String key;
    private final String image;

    @JsonCreator
    public PackReferencePart(@JsonProperty final String key, @JsonProperty final String image) {
        this.key = key;
        this.image = image;
    }

    public String key() {
        return this.key;
    }

    public String image() {
        return this.image;
    }

    public FileResource toResource() {
        return FileResources.all();
    }
}
