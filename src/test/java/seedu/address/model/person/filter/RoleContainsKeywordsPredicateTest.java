package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.SupplierBuilder;

class RoleContainsKeywordsPredicateTest {

    @Test
    public void formatFilter() {
        String firstKeyword = "client";
        String secondKeyword = "supplier";

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstKeyword);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondKeyword);

        assertEquals("role/client", firstPredicate.formatFilter());
        assertEquals("role/supplier", secondPredicate.formatFilter());
    }

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeyword);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstPredicateKeyword);
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
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate("client");
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

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{roles=" + roles + "}";
        assertEquals(expected, predicate.toString());
    }
}
