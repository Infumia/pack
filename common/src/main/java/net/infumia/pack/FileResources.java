package net.infumia.pack;

import java.util.Arrays;
import java.util.Collection;
import team.unnamed.creative.base.Writable;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.metadata.pack.PackMeta;
import team.unnamed.creative.model.Model;
import team.unnamed.creative.texture.Texture;

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
     * Creates a {@link FileResource} for the specified meta.
     *
     * @param meta the meta to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the meta.
     */
    public static FileResource meta(final PackMeta meta) {
        return new FileResourcePackMeta(meta);
    }

    /**
     * Creates a {@link FileResource} for the specified path and writable data.
     *
     * @param path     the path to create a file resource for. Cannot be null.
     * @param writable the writable to create a file resource for. Cannot be null.
     * @return a {@link FileResource} representing the unknown file.
     */
    public static FileResource unknown(final String path, final Writable writable) {
        return new FileResourceUnknown(path, writable);
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
