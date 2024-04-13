package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

public class IdContainsDigitsPredicateTest {

    @Test
    public void getIds_test_success() {
        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        assertEquals(List.of(ID_FIRST_PERSON.value, ID_SECOND_PERSON.value), predicate.getIds());
    }

    @Test
    public void formatFilter_success() {
        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        assertEquals("i/" + ID_FIRST_PERSON.value + " i/" + ID_SECOND_PERSON.value, predicate.formatFilter());
    }
    @Test
    public void equals() {
        // same object -> returns true
        IdContainsDigitsPredicate firstPredicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));

        IdContainsDigitsPredicate secondPredicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));

        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        assertEquals(firstPredicate, secondPredicate);

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different ids -> returns false
        IdContainsDigitsPredicate thirdPredicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    public void toStringMethod() {
        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        String expected = IdContainsDigitsPredicate.class.getCanonicalName() + "{ids=[" + ID_FIRST_PERSON.value + ", "
                + ID_SECOND_PERSON.value + "]}";
        assertEquals(expected, predicate.toString());
    }
}
