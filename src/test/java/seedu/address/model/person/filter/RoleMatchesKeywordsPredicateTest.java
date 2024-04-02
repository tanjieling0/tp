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

class RoleMatchesKeywordsPredicateTest {

    @Test
    public void formatFilter() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleMatchesKeywordsPredicate firstPredicate = new RoleMatchesKeywordsPredicate(firstPredicateKeywordList);
        RoleMatchesKeywordsPredicate secondPredicate = new RoleMatchesKeywordsPredicate(secondPredicateKeywordList);

        assertEquals("role/first", firstPredicate.formatFilter());
        assertEquals("role/first role/second", secondPredicate.formatFilter());
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RoleMatchesKeywordsPredicate firstPredicate = new RoleMatchesKeywordsPredicate(firstPredicateKeywordList);
        RoleMatchesKeywordsPredicate secondPredicate = new RoleMatchesKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleMatchesKeywordsPredicate firstPredicateCopy =
                new RoleMatchesKeywordsPredicate(firstPredicateKeywordList);
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
        RoleMatchesKeywordsPredicate predicate =
                new RoleMatchesKeywordsPredicate(Collections.singletonList("client"));
        assertTrue(predicate.test(new ClientBuilder().build()));

        // Multiple keywords
        predicate = new RoleMatchesKeywordsPredicate(List.of("client", "supplier"));
        assertTrue(predicate.test(new ClientBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));

        // Only one matching keyword
        predicate = new RoleMatchesKeywordsPredicate(List.of("client", "supplier"));
        assertTrue(predicate.test(new SupplierBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));

        // Mixed-case keywords
        predicate = new RoleMatchesKeywordsPredicate(List.of("cLiEnT", "sUpPlIeR"));
        assertTrue(predicate.test(new SupplierBuilder().build()));
        assertTrue(predicate.test(new SupplierBuilder().build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleMatchesKeywordsPredicate predicate = new RoleMatchesKeywordsPredicate(List.of());
        assertFalse(predicate.test(new ClientBuilder().build()));
        assertFalse(predicate.test(new SupplierBuilder().build()));

        // Non-matching keyword
        predicate = new RoleMatchesKeywordsPredicate(List.of("employee"));
        assertFalse(predicate.test(new ClientBuilder().build()));
        assertFalse(predicate.test(new SupplierBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> roles = List.of("client", "supplier");
        RoleMatchesKeywordsPredicate predicate = new RoleMatchesKeywordsPredicate(roles);

        String expected = RoleMatchesKeywordsPredicate.class.getCanonicalName() + "{keywords=" + roles + "}";
        assertEquals(expected, predicate.toString());
    }
}
