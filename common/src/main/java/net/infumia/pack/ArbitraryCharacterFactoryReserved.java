package net.infumia.pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A factory for creating arbitrary characters, ensuring that reserved characters are not produced.
 */
@SuppressWarnings("UnnecessaryUnicodeEscape")
public final class ArbitraryCharacterFactoryReserved implements ArbitraryCharacterFactory {

    private final Collection<Character> reserved;
    private char startPoint;

    /**
     * Constructs a new instance with the specified start point and collection of reserved characters.
     *
     * @param startPoint the starting character for generating new characters.
     * @param reserved   the collection of characters that should not be produced by the factory. Cannot be null.
     */
    public ArbitraryCharacterFactoryReserved(
        final char startPoint,
        final Collection<Character> reserved
    ) {
        this.startPoint = startPoint;
        this.reserved = reserved;
    }

    /**
     * Constructs a new instance with the specified collection of reserved characters,
     * starting from the default character {@literal '\uA201'}.
     *
     * @param reserved the collection of characters that should not be produced by the factory. Cannot be null.
     */
    public ArbitraryCharacterFactoryReserved(final Collection<Character> reserved) {
        this('\uA201', reserved);
    }

    /**
     * Constructs a new instance with a default set of reserved characters,
     * starting from the default character '\uA201'.
     */
    public ArbitraryCharacterFactoryReserved() {
        this(Internal.RESERVED.get());
    }

    @Override
    public char create() throws IllegalStateException {
        if (this.startPoint == Character.MAX_VALUE) {
            throw new IllegalStateException("Characters range exceeded");
        }
        do {
            this.startPoint++;
        } while (this.reserved.contains(this.startPoint));
        return this.startPoint;
    }

    private static final class Internal {

        private static final Lazy<Collection<Character>> RESERVED = Lazy.of(() -> {
            final Collection<Character> reserved = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) {
                reserved.add(c);
            }
            for (char c = 'A'; c <= 'Z'; c++) {
                reserved.add(c);
            }
            for (char c = '0'; c <= '9'; c++) {
                reserved.add(c);
            }
            Collections.addAll(
                reserved,
                '!',
                '?',
                ':',
                '$',
                ';',
                '#',
                '@',
                '%',
                '^',
                '&',
                '*',
                '(',
                ')',
                '_',
                '-',
                '+',
                '/',
                '\\',
                '"',
                '\'',
                '{',
                '}',
                '[',
                ']',
                '~',
                '`',
                '<',
                '>',
                ',',
                '.',
                '|',
                '\n',
                '\r',
                '\b',
                '\f',
                '\t',
                ' ',
                '='
            );
            return reserved;
        });

        private Internal() {
            throw new IllegalStateException("Utility class");
        }
    }
}
