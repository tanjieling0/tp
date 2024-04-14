package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.SupplierBuilder;

class TagsContainsKeywordsPredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagsContainsKeywordsPredicate(null));
    }

    @Test
    public void formatFilter() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(secondPredicateKeywordList);

        assertEquals("t/first", firstPredicate.formatFilter());
        assertEquals("t/first t/second", secondPredicate.formatFilter());
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // Using ClientBuilder
        TagsContainsKeywordsPredicate predicate =
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends").build()));

        // Using EmployeeBuilder with Multiple keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        assertTrue(predicate.test(new EmployeeBuilder().withTags("friends", "colleagues").build()));

        // Only one matching keyword
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        assertTrue(predicate.test(new ClientBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new ClientBuilder().withTags("colleagues").build()));

        // Mixed-case keywords
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("frIEnDs", "coLLeaGUes"));
        assertTrue(predicate.test(new EmployeeBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new ClientBuilder().withTags("colleagues").build()));

        // Using SupplierBuilder
        predicate = new TagsContainsKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        assertTrue(predicate.test(new SupplierBuilder().withTags("friends").build()));
        assertTrue(predicate.test(new ClientBuilder().withTags("colleagues").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new TagsContainsKeywordsPredicate(List.of("friends"));
        assertFalse(predicate.test(new EmployeeBuilder().withTags("colleagues").build()));

        // Keywords match name, phone, email, and address, but does not match tags
        predicate = new TagsContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new ClientBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagsContainsKeywordsPredicate firstPredicate = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        TagsContainsKeywordsPredicate secondPredicate = new TagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainsKeywordsPredicate firstPredicateCopy = new TagsContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagsContainsKeywordsPredicate predicate = new TagsContainsKeywordsPredicate(keywords);

        String expected = TagsContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
