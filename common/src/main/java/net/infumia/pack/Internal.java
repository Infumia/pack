package net.infumia.pack;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import net.kyori.adventure.key.Key;
import team.unnamed.creative.base.Writable;

final class Internal {

    private static final String RESOURCES_FOLDER = "pack-resources";
    static final String DEFAULT_NAMESPACE = "glyphs";
    static final Key DEFAULT_SPACES_TEXTURE_KEY = Key.key(Internal.DEFAULT_NAMESPACE, "spaces");
    static final int SEPARATOR_WIDTH = 1;

    static Key keyWithPngExtension(final Key key) {
        //noinspection PatternValidation
        return Key.key(key.namespace(), key.value().concat(".png"));
    }

    static Writable resourceFromJar(final String fileName) {
        return Writable.resource(
            Internal.class.getClassLoader(),
            Internal.RESOURCES_FOLDER + "/" + fileName
        );
    }

    static int calculateWidth(
        final BufferedImage image,
        final int fromX,
        final int fromY,
        final int toX,
        final int toY
    ) {
        int width;
        for (width = toX - 1; width > fromX; width--) {
            for (int height = fromY; height < toY; height++) {
                if (new Color(image.getRGB(width, height), true).getAlpha() != 0) {
                    return width - fromX + 1;
                }
            }
        }
        return width - fromX + 1;
    }

    static int calculateWidth(final BufferedImage image) {
        return Internal.calculateWidth(image, 0, 0, image.getWidth(), image.getHeight());
    }

    static char[] toCharArray(final List<Character> list) {
        final char[] charArray = new char[list.size()];
        for (int i = 0; i < list.size(); i++) {
            charArray[i] = list.get(i);
        }
        return charArray;
    }

    private Internal() {
        throw new IllegalStateException("Utility class");
    }
}
