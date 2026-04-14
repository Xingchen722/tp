package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isValidNote() {
        assertTrue(Note.isValidNote(""));
        assertTrue(Note.isValidNote("a"));
        assertTrue(Note.isValidNote(" ".repeat(10)));

        String maxLen = "x".repeat(Note.MAX_LENGTH);
        assertTrue(Note.isValidNote(maxLen));

        assertFalse(Note.isValidNote("x".repeat(Note.MAX_LENGTH + 1)));
    }

    @Test
    public void equals() {
        Note first = new Note("Follow up Monday");
        Note second = new Note("Follow up Monday");
        Note third = new Note("Different");

        assertEquals(first, second);
        assertEquals(first, first);
        assertNotEquals(first, third);
        assertNotEquals(first, null);
        assertNotEquals(first, 1);
    }

    @Test
    public void hashCode_sameValue_sameHash() {
        Note a = new Note("same");
        Note b = new Note("same");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void toString_returnsValue() {
        Note note = new Note("hello");
        assertEquals("hello", note.toString());
    }
}
