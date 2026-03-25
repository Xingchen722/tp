package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Note in an Application.
 */
public class Note {
    public static final int MAX_LENGTH = 1000;
    public static final String MESSAGE_CONSTRAINTS =
            "Notes can take any values, but should not be completely blank or exceed " + MAX_LENGTH + " characters.";

    public final String value;

    public Note(String value) {
        requireNonNull(value);
        this.value = value;
    }

    public static boolean isValidNote(String test) {
        return test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note)) {
            return false;
        }

        Note otherNote = (Note) other;
        return value.equals(otherNote.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
