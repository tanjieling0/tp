package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.SupplierBuilder;

public class RemarkContainsKeywordsPredicateTest {

    @Test
    public void formatFilter() {
        List<String> emptyKeywordList = Collections.singletonList("");
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RemarkContainsKeywordsPredicate emptyPredicate =
                new RemarkContainsKeywordsPredicate(emptyKeywordList);
        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(secondPredicateKeywordList);

        assertEquals("r/EMPTY-REMARK", emptyPredicate.formatFilter());
        assertEquals("r/first", firstPredicate.formatFilter());
        assertEquals("r/first r/second", secondPredicate.formatFilter());
    }

    @Test
    public void equals() {
        // same object -> returns true
        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList("first"));
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList("first"));
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        assert(firstPredicate.equals(secondPredicate));

        //different values -> returns false
        RemarkContainsKeywordsPredicate thirdPredicate =
                new RemarkContainsKeywordsPredicate(Arrays.asList("first", "second"));
        RemarkContainsKeywordsPredicate forthPredicate =
                new RemarkContainsKeywordsPredicate(Arrays.asList("first", "third"));

        assertFalse(thirdPredicate.equals(forthPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        RemarkContainsKeywordsPredicate sixthPredicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList("second"));
        assertFalse(firstPredicate.equals(sixthPredicate));

        //// additional tests with empty keyword
        RemarkContainsKeywordsPredicate emptyPredicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList(""));
        RemarkContainsKeywordsPredicate otherEmptyPredicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList(""));
        RemarkContainsKeywordsPredicate firstWithEmptyPredicate =
                new RemarkContainsKeywordsPredicate(Arrays.asList("first", ""));

        // same object -> returns true
        assertTrue(emptyPredicate.equals(emptyPredicate));

        // diff object, both empty -> returns true
        assertTrue(emptyPredicate.equals(otherEmptyPredicate));

        // empty but has diff keyword list -> returns false
        assertFalse(emptyPredicate.equals(firstWithEmptyPredicate));

        // same keyword list, diff empty boolean -> returns false
        assertFalse(firstPredicate.equals(firstWithEmptyPredicate));

        // all values different -> returns false
        assertFalse(thirdPredicate.equals(firstWithEmptyPredicate));
    }

    @Test
    public void test_remarkContainsKeywords_returnsTrue() {
        RemarkContainsKeywordsPredicate predicate =
                new RemarkContainsKeywordsPredicate(Arrays.asList("first", "second"));
        assertTrue(predicate.test(new ClientBuilder().withRemark("first").build()));
        assertTrue(predicate.test(new ClientBuilder().withRemark("second").build()));
    }

    @Test
    public void test_remarkMatchesEmptyKeyword_returnsTrue() {
        RemarkContainsKeywordsPredicate predicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(new ClientBuilder().withRemark("").build()));
    }

    @Test
    public void test_remarkDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withRemark("Alice Bob").build()));

        // Non-matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EmployeeBuilder().withRemark("Alice Bob").build()));

        // Keywords match name, phone, email and address, but does not match remark
        predicate = new RemarkContainsKeywordsPredicate(
                Arrays.asList("Alice", "alice@email.com", "Main", "Street", "12345"));
        assertFalse(predicate.test(new SupplierBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark("biscuit").build()));

        // Non-empty remark with empty keyword
        predicate = new RemarkContainsKeywordsPredicate(Collections.singletonList(""));
        assertFalse(predicate.test(new ClientBuilder().withRemark("first").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> remarkKeywords = Arrays.asList("first", "second");
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(remarkKeywords);
        String expected = RemarkContainsKeywordsPredicate.class.getCanonicalName() + "{remarks=" + remarkKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
