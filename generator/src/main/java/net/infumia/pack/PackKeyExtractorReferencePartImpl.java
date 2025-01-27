package net.infumia.pack;

import net.kyori.adventure.key.Key;

final class PackKeyExtractorReferencePartImpl implements PackKeyExtractorReferencePart {

    static final PackKeyExtractorReferencePart INSTANCE = new PackKeyExtractorReferencePartImpl();

    @Override
    public Key extract(final PackGeneratorContext context, final PackReferencePart part) {
        final String overriddenNamespace = part.namespace();
        final String namespace = overriddenNamespace == null
            ? context.packReference().defaultNamespace()
            : overriddenNamespace;
        if (namespace == null) {
            throw new IllegalStateException("Pack reference namespace cannot be null!");
        }
        final String creatorName = part.creator().simplifiedName();
        final int index = creatorName.lastIndexOf('/');
        final String parent = index == -1 ? "" : creatorName.substring(0, index + 1);
        return Key.key(namespace, parent + part.key());
    }
}
