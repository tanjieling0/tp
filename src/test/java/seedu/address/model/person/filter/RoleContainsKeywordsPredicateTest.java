package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
}
