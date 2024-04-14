package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class PhoneMatchesDigitsPredicateTest {

    @Test
    public void formatFilter() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneMatchesDigitsPredicate firstPredicate = new PhoneMatchesDigitsPredicate(firstPredicateKeywordList);
        PhoneMatchesDigitsPredicate secondPredicate = new PhoneMatchesDigitsPredicate(secondPredicateKeywordList);

        assertEquals("p/first", firstPredicate.formatFilter());
        assertEquals("p/first p/second", secondPredicate.formatFilter());
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("94351253");
        List<String> secondPredicateKeywordList = Arrays.asList("94351253", "98765432");

        PhoneMatchesDigitsPredicate firstPredicate = new PhoneMatchesDigitsPredicate(firstPredicateKeywordList);
        PhoneMatchesDigitsPredicate secondPredicate = new PhoneMatchesDigitsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        PhoneMatchesDigitsPredicate firstPredicateCopy = new PhoneMatchesDigitsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different instance -> returns false
        assertFalse(firstPredicate.equals(new ArrayList<>()));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_numberMatchesDigits_returnsTrue() {
        // One keyword
        PhoneMatchesDigitsPredicate predicate =
                new PhoneMatchesDigitsPredicate(Collections.singletonList("94351253"));
        assertTrue(predicate.test(new ClientBuilder().withPhone("94351253").build()));
    }

    @Test
    public void test_phoneDoesNotMatchDigits_returnsFalse() {
        // Zero keywords
        PhoneMatchesDigitsPredicate predicate = new PhoneMatchesDigitsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withPhone("94351253").build()));

        // Non-matching keyword
        predicate = new PhoneMatchesDigitsPredicate(List.of("95352563"));
        assertFalse(predicate.test(new ClientBuilder().withPhone("98765432").build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneMatchesDigitsPredicate(Arrays.asList("Alice", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> phones = List.of("94351253", "98765432");
        PhoneMatchesDigitsPredicate predicate = new PhoneMatchesDigitsPredicate(phones);

        String expected = PhoneMatchesDigitsPredicate.class.getCanonicalName() + "{phones=" + phones + "}";
        assertEquals(expected, predicate.toString());
    }
}
