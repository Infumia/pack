package net.infumia.pack;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.atlas.Atlas;
import team.unnamed.creative.model.ItemOverride;
import team.unnamed.creative.model.ItemPredicate;
import team.unnamed.creative.model.Model;

final class FileResourceMergerDefault implements FileResourceMerger {

    static final FileResourceMerger INSTANCE = new FileResourceMergerDefault();
    static final Comparator<ItemOverride> OVERRIDE_COMPARATOR = Comparator.comparingInt(value -> {
        final Optional<ItemPredicate> first = value.predicate().stream().findFirst();
        if (!first.isPresent()) {
            return 0;
        }
        final ItemPredicate predicate = first.get();
        if (predicate.name().equals("custom_model_data")) {
            return (int) predicate.value();
        }
        return 0;
    });

    private FileResourceMergerDefault() {}

    @Override
    public Collection<FileResource> merge(final Collection<FileResource> resources) {
        final Collection<FileResource> simplified = new HashSet<>(resources.size());
        for (final FileResource resource : resources) {
            simplified.addAll(this.simplify(resource));
        }

        final MultiMap<Key, Atlas> atlases = new MultiMap<>();
        final MultiMap<Key, Model> models = new MultiMap<>();
        final HashSet<FileResource> remaining = new HashSet<>();
        for (final FileResource resource : simplified) {
            if (resource instanceof FileResourceAtlas) {
                final Atlas atlas = ((FileResourceAtlas) resource).atlas;
                atlases.put(atlas.key(), atlas);
            } else if (resource instanceof FileResourceModel) {
                final Model model = ((FileResourceModel) resource).model;
                models.put(model.key(), model);
            }
            // TODO: portlek, Merge more things.
            else {
                remaining.add(resource);
            }
        }

        final Collection<Atlas> mergedAtlases = new HashSet<>(atlases.keys().size());
        for (final Key key : atlases.keys()) {
            final Collection<Atlas> duplicates = atlases.get(key);
            final Atlas merged = Atlas.atlas()
                .key(key)
                .sources(
                    duplicates
                        .stream()
                        .map(Atlas::sources)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
                )
                .build();
            mergedAtlases.add(merged);
        }

        final Collection<Model> mergedModels = new HashSet<>(models.keys().size());
        for (final Key key : models.keys()) {
            final Collection<Model> duplicates = models.get(key);
            final Model.Builder builder = Model.model().key(key);
            // TODO: portlek, Find a way to merge these too.
            for (final Model duplicate : duplicates) {
                builder
                    .guiLight(duplicate.guiLight())
                    .elements(duplicate.elements())
                    .parent(duplicate.parent())
                    .ambientOcclusion(duplicate.ambientOcclusion())
                    .display(duplicate.display())
                    .textures(duplicate.textures());
            }
            builder.overrides(
                duplicates
                    .stream()
                    .map(Model::overrides)
                    .flatMap(Collection::stream)
                    .sorted(FileResourceMergerDefault.OVERRIDE_COMPARATOR)
                    .collect(Collectors.toList())
            );
            mergedModels.add(builder.build());
        }

        final Collection<FileResource> mergedResources = mergedAtlases
            .stream()
            .map(FileResources::atlas)
            .collect(Collectors.toSet());
        mergedResources.addAll(
            mergedModels.stream().map(FileResources::model).collect(Collectors.toSet())
        );
        mergedResources.addAll(remaining);
        return mergedResources;
    }

    private Collection<FileResource> simplify(final FileResource resource) {
        if (resource instanceof FileResourceAll) {
            return ((FileResourceAll) resource).resources.stream()
                .map(this::simplify)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        } else {
            return Collections.singleton(resource);
        }
    }
}