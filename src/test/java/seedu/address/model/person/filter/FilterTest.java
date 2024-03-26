package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.ClientBuilder;

class FilterTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Filter(null));
    }

    @Test
    public void add() {
        List<NetConnectPredicate<Person>> predicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        Filter filter = new Filter(predicateList);
        List<NetConnectPredicate<Person>> expectedPredicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        Filter expectedFilter = new Filter(expectedPredicateList);
        assertEquals(expectedFilter,
                filter.add(new TagsContainsKeywordsPredicate(Collections.singletonList("friends"))));
    }

    @Test
    public void clear() {
        Filter filter = new Filter(List.of(new NameContainsKeywordsPredicate(Collections.singletonList("Alice"))));
        assertEquals(filter.clear(), new Filter());
        assertEquals(filter.clear(), new Filter(List.of()));
    }

    @Test
    public void formatFilter() {
        List<NetConnectPredicate<Person>> firstPredicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        List<NetConnectPredicate<Person>> secondPredicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));

        Filter firstFilter = new Filter(firstPredicateList);
        Filter secondFilter = new Filter(secondPredicateList);

        assertEquals("1. n/Alice", firstFilter.formatFilter());
        assertEquals("1. n/Alice\n2. t/friends", secondFilter.formatFilter());
    }

    @Test
    public void test_namesContainsKeywords_returnsTrue() {
        // exact match
        List<NetConnectPredicate<Person>> predicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        Filter filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withName("Alice").build()));

        // partial match
        assertTrue(filter.test(new ClientBuilder().withName("Alice Pauline").build()));
        assertTrue(filter.test(new ClientBuilder().withName("MAliceM Pauline").build()));

        // mixed case
        predicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Collections.singletonList("AliCe PAuLinE")));
        filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withName("Alice Pauline").build()));
        assertTrue(filter.test(new ClientBuilder().withName("aLIcE paUlINe").build()));

        // multiple names
        predicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withName("Alice").build()));
        assertTrue(filter.test(new ClientBuilder().withName("Bob").build()));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        // exact match
        List<NetConnectPredicate<Person>> predicateList = Collections.singletonList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        Filter filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withTags("friends").build()));

        // mixed case
        predicateList = Collections.singletonList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("FriEnDs")));
        filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withTags("friends").build()));
        assertTrue(filter.test(new ClientBuilder().withTags("fRIeNdS").build()));

        // multiple tags
        predicateList = Collections.singletonList(
                new TagsContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withTags("friends").build()));
        assertTrue(filter.test(new ClientBuilder().withTags("colleagues").build()));
        assertTrue(filter.test(new ClientBuilder().withTags("friends", "boss").build()));
        assertTrue(filter.test(new ClientBuilder().withTags("boss", "colleagues").build()));
    }

    @Test
    public void test_matchingMultiplePredicate_returnsTrue() {
        // matching name and tags
        List<NetConnectPredicate<Person>> predicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        Filter filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withName("Alice").withTags("friends").build()));

        // mixed case
        predicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Collections.singletonList("AlIcE")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("fRiEnDs")));
        filter = new Filter(predicateList);
        assertTrue(filter.test(new ClientBuilder().withName("aLiCe").withTags("FrIeNdS").build()));
    }

    @Test
    public void test_namesDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        List<NetConnectPredicate<Person>> predicateList = Collections.singletonList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")));
        Filter filter = new Filter(predicateList);
        assertFalse(filter.test(new ClientBuilder().withName("Bob").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        List<NetConnectPredicate<Person>> predicateList = Collections.singletonList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        Filter filter = new Filter(predicateList);
        assertFalse(filter.test(new ClientBuilder().withTags("colleagues").build()));
    }

    @Test
    public void test_notMatchingMultiplePredicate_returnsFalse() {
        // matching name, non-matching tags
        List<NetConnectPredicate<Person>> predicateList = Arrays.asList(
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        Filter filter = new Filter(predicateList);
        assertFalse(filter.test(new ClientBuilder().withName("Alice").withTags("colleagues").build()));

        // matching tags, non-matching name
        assertFalse(filter.test(new ClientBuilder().withName("Bob").withTags("friends").build()));
        assertFalse(filter.test(new ClientBuilder().withName("Bob").withTags("friends", "colleagues").build()));
        assertFalse(filter.test(new ClientBuilder().withName("Bob").withTags("colleagues", "friends").build()));
    }

    @Test
    public void equals() {
        List<NetConnectPredicate<Person>> predicates =
                List.of(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        Filter emptyFilter = new Filter();
        Filter filter = new Filter(predicates);

        // same values -> returns true
        assertTrue(emptyFilter.equals(new Filter()));
        assertTrue(filter.equals(new Filter(predicates)));

        // same object -> returns true
        assertTrue(emptyFilter.equals(emptyFilter));
        assertTrue(filter.equals(filter));

        // null -> returns false
        assertFalse(emptyFilter.equals(null));

        // different types -> returns false
        assertFalse(emptyFilter.equals(0.5f));

        // different values -> returns false
        assertFalse(emptyFilter.equals(
                new Filter(List.of(new NameContainsKeywordsPredicate(Arrays.asList("Alice"))))));
    }

    // from: https://stackoverflow.com/questions/4449728/how-can-i-do-unit-test-for-hashcode
    @Test
    public void testSymmetricEquals() {
        List<NetConnectPredicate<Person>> predicates =
                List.of(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        Filter firstFilter = new Filter(predicates);
        Filter secondFilter = new Filter(predicates);
        assertTrue(secondFilter.equals(firstFilter) && firstFilter.equals(secondFilter));
        assertTrue(secondFilter.hashCode() == firstFilter.hashCode());
    }

    @Test
    public void toStringMethod() {
        List<NetConnectPredicate<Person>> predicates =
                List.of(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        Filter emptyFilter = new Filter();
        Filter filter = new Filter(predicates);

        String expected = Filter.class.getCanonicalName() + "{filters=" + List.of() + "}";
        assertEquals(expected, emptyFilter.toString());

        expected = Filter.class.getCanonicalName() + "{filters=" + predicates + "}";
        assertEquals(expected, filter.toString());
    }
}
