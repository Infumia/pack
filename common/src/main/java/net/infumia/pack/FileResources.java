package net.infumia.pack;

import team.unnamed.creative.atlas.Atlas;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.texture.Texture;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class for creating various types of {@link FileResource} instances.
 */
public final class FileResources {
    /**
     * Creates a {@link FileResource} for the specified texture.
     *
     * @param texture the texture to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the texture.
     */
    public static FileResource texture(final Texture texture) {
        return new FileResourceTexture(texture);
    }

    /**
     * Creates a {@link FileResource} for the specified atlas.
     *
     * @param atlas the atlas to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the atlas.
     */
    public static FileResource atlas(final Atlas atlas) {
        return new FileResourceAtlas(atlas);
    }

    /**
     * Creates a {@link FileResource} for the specified font.
     *
     * @param font the font to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the font.
     */
    public static FileResource font(final Font font) {
        return new FileResourceFont(font);
    }

    /**
     * Creates a {@link FileResource} for the specified model.
     *
     * @param model the model to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the model.
     */
    public static FileResource model(final Model model) {
        return new FileResourceModel(model);
    }

    /**
     * Creates a {@link FileResource} for a collection of file resources.
     *
     * @param resources the collection of resources to create file resources for. Cannot be null.
     * @return a {@link FileResource} representing all the resources.
     */
    public static FileResource all(final Collection<FileResource> resources) {
        return new FileResourceAll(resources);
    }

    /**
     * Creates a {@link FileResource} for a collection of file resources.
     *
     * @param resources the collection of resources to create file resources for. Cannot be null.
     * @return a {@link FileResource} representing all the resources.
     */
    public static FileResource all(final FileResource... resources) {
        return FileResources.all(Arrays.asList(resources));
    }

    private FileResources() {
        throw new IllegalStateException("Utility class");
    }
}
