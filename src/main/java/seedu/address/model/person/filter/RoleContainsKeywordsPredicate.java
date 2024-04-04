package seedu.address.model.person.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a predicate that checks if a person's role contains any of the specified keywords.
 * This predicate is used to filter a list of persons based on their roles.
 */
public class RoleContainsKeywordsPredicate extends NetConnectPredicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code RoleContainsKeywordsPredicate} with a list of keywords.
     *
     * @param keywords The list of keywords to match against the person's role.
     */
    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = new ArrayList<>();
        for (String keyword : keywords) {
            this.keywords.add(keyword.toLowerCase());
        }
    }

    /**
     * Constructs a {@code RoleContainsKeywordsPredicate} with a single keyword.
     *
     * @param keyword The keyword to match against the person's role.
     */
    public RoleContainsKeywordsPredicate(String keyword) {
        this.keywords = new ArrayList<>();
        this.keywords.add(keyword.toLowerCase());
    }

    @Override
    public String formatFilter() {
        return keywords.stream().map(s -> "role/" + s).collect(Collectors.joining(" "));
    }

    /**
     * Tests if the given person's role contains any of the keywords.
     *
     * @param person The person to test.
     * @return {@code true} if the person's role contains any of the keywords, {@code false} otherwise.
     */
    @Override
    public boolean test(Person person) {
        String lowercaseRole = person.getRole().toLowerCase();
        for (String keyword : keywords) {
            if (lowercaseRole.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RoleContainsKeywordsPredicate)) {
            return false;
        }
        RoleContainsKeywordsPredicate otherPredicate = (RoleContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    /**
     * Returns a string representation of the predicate.
     *
     * @return A string representation of the predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roles", keywords).toString();
    }
}
