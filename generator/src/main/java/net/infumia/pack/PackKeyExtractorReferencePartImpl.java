package net.infumia.pack;

import net.kyori.adventure.key.Key;

final class PackKeyExtractorReferencePartImpl implements PackKeyExtractorReferencePart {

    static final PackKeyExtractorReferencePart INSTANCE = new PackKeyExtractorReferencePartImpl();

    @Override
    public Key extract(final PackReadContext context, final PackReferencePart part) {
        final String overriddenNamespace = part.namespace();
        final String namespace = overriddenNamespace == null
            ? context.packReference().defaultNamespace()
            : overriddenNamespace;
        if (namespace == null) {
            throw new IllegalStateException("Pack reference namespace cannot be null!");
        }
        return Key.key(namespace, part.parentPath() + part.key());
    }
}
