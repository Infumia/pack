package net.infumia.pack;

/**
 * Interface for a factory that creates arbitrary characters.
 */
public interface ArbitraryCharacterFactory {
    /**
     * Creates and returns an arbitrary character.
     *
     * @return a generated character.
     * @throws IllegalStateException if the character range exceeds.
     */
    char create() throws IllegalStateException;
}
