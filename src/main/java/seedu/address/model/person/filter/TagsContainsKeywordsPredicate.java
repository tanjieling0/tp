package seedu.address.model.person.filter;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a predicate that checks if a person's tags contains a specified keyword.
 * This predicate is used to filter a list of persons based on their tags.
 */
public class TagsContainsKeywordsPredicate extends NetConnectPredicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code TagsContainsKeywordsPredicate} with the specified keyword.
     *
     * @param keywords The keyword to match against the person's tags.
     */
    public TagsContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);

        this.keywords = keywords.stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    @Override
    public String formatFilter() {
        return keywords.stream().map(keyword -> "t/" + keyword).collect(Collectors.joining(" "));
    }

    /**
     * Tests if the given person's tags contains the keyword.
     *
     * @param person The person to test.
     * @return {@code true} if the person's tags contain any of the keyword, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .map(t -> t.tagName.toLowerCase())
                .anyMatch(keywords::contains);
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainsKeywordsPredicate)) {
            return false;
        }

        TagsContainsKeywordsPredicate otherTagsContainsKeywordsPredicate = (TagsContainsKeywordsPredicate) other;
        return keywords.equals(otherTagsContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
