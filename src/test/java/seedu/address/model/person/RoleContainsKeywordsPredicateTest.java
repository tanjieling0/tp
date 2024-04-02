package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.SupplierBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        // same object -> returns true
        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate("client");
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate("client");
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        RoleContainsKeywordsPredicate fifthPredicate = new RoleContainsKeywordsPredicate("supplier");
        assertFalse(firstPredicate.equals(fifthPredicate));
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
