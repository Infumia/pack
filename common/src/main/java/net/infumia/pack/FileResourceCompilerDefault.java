package net.infumia.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.font.FontProvider;

final class FileResourceCompilerDefault implements FileResourceCompiler {

    private final ArbitraryCharacterFactory characterFactory;

    FileResourceCompilerDefault(final ArbitraryCharacterFactory characterFactory) {
        this.characterFactory = characterFactory;
    }

    @Override
    public Collection<FileResource> compile(final Collection<ResourceProducer> producers) {
        final Collection<FileResource> resources = new HashSet<>();
        final Collection<Key> fontKeys = producers
            .stream()
            .map(ResourceProducer::key)
            .collect(Collectors.toSet());
        for (final Key fontKey : fontKeys) {
            final List<FontProvider> fontProviders = new ArrayList<>();
            for (final ResourceProducer producer : producers) {
                if (producer.key().equals(fontKey)) {
                    producer.produce(this.characterFactory);
                    fontProviders.addAll(producer.fontProviders());
                    final List<FileResource> textureResources = producer
                        .textures()
                        .stream()
                        .map(FileResources::texture)
                        .collect(Collectors.toList());
                    resources.add(FileResources.all(textureResources));
                }
            }
            resources.add(FileResources.font(Font.font(fontKey, fontProviders)));
        }
        return resources;
    }
}
