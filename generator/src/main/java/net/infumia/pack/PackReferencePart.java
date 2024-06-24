package net.infumia.pack;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = PackReferencePartImage.class, name = "image") })
public abstract class PackReferencePart {

    public abstract void add(PackGeneratorContext context);
}
