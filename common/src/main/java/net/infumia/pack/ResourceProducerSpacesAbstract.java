package net.infumia.pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.infumia.pack.exception.ResourceNotProducedException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

abstract class ResourceProducerSpacesAbstract implements ResourceProducerSpaces {

    private final Key fontKey;
    protected Map<Integer, Character> mapping;

    protected ResourceProducerSpacesAbstract(final Key fontKey) {
        this.fontKey = fontKey;
    }

    @NotNull
    @Override
    public final Key key() {
        return this.fontKey;
    }

    @Override
    public boolean produced() {
        return this.mapping != null;
    }

    @Override
    public Glyph translate(final int length) throws ResourceNotProducedException {
        if (this.mapping == null) {
            throw new ResourceNotProducedException();
        }
        if (length == 0) {
            return Glyphs.empty();
        }
        final int sign = length > 0 ? 1 : -1;
        final String binaryString = Integer.toBinaryString(Math.abs(length));
        final List<Character> characters = new ArrayList<>();
        int currentRankLength = 1;
        for (int index = 0; index < binaryString.length(); index++) {
            final char digit = binaryString.charAt(binaryString.length() - index - 1);
            if (digit == '1') {
                final int partLength = currentRankLength * sign;
                if (!this.mapping.containsKey(partLength)) {
                    throw new IllegalArgumentException("Too much length");
                }
                characters.add(this.mapping.get(partLength));
            }
            currentRankLength *= 2;
        }
        return new GlyphImpl(this.fontKey, Internal.toCharArray(characters), length);
    }
}
