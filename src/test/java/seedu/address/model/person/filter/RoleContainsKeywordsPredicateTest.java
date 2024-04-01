package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.SupplierBuilder;

class RoleContainsKeywordsPredicateTest {

    @Test
    public void formatFilter() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        assertEquals("role/first", firstPredicate.formatFilter());
        assertEquals("role/first role/second", secondPredicate.formatFilter());
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy =
                new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate =
                new RoleContainsKeywordsPredicate(Collections.singletonList("client"));
        assertTrue(predicate.test(new ClientBuilder().build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(List.of("client", "supplier"));
        assertTrue(predicate.test(new ClientBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(List.of("client", "supplier"));
        assertTrue(predicate.test(new SupplierBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(List.of("cLiEnT", "sUpPlIeR"));
        assertTrue(predicate.test(new SupplierBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of());
        assertFalse(predicate.test(new ClientBuilder().build()));
        assertFalse(predicate.test(new SupplierBuilder().build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(List.of("employee"));
        assertFalse(predicate.test(new ClientBuilder().build()));
        assertFalse(predicate.test(new SupplierBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> roles = List.of("client", "supplier");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(roles);

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + roles + "}";
        assertEquals(expected, predicate.toString());
    }
}
