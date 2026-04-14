package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("123456789012345678")); // more than 17 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("65 1234")); // spaces are not allowed inside
        assertFalse(Phone.isValidPhone("65-1234")); // other symbols are not allowed
        assertFalse(Phone.isValidPhone("65+1234")); // plus is not allowed
        assertFalse(Phone.isValidPhone("()+651234")); // non-digit symbols are not allowed
        assertFalse(Phone.isValidPhone("(65)")); // parentheses are not allowed

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers (15)
        assertTrue(Phone.isValidPhone("12345678901234567")); // exactly 17 numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
