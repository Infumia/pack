package net.infumia.pack;

import net.kyori.adventure.key.Key;

public interface PackKeyExtractorReferencePart {
    Key extract(PackGeneratorContext context, PackReferencePart part);
}
