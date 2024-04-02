package seedu.address.model.person.filter;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a predicate that checks if a person's role matches any of the specified keywords.
 * This predicate is used to filter a list of persons based on their roles.
 */
public class RoleMatchesKeywordsPredicate extends NetConnectPredicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code RoleContainsKeywordsPredicate} with a list of keywords.
     *
     * @param keywords The list of keywords to match against the person's role.
     */
    public RoleMatchesKeywordsPredicate(List<String> keywords) {
        this.keywords = Collections.unmodifiableList(keywords);
    }

    @Override
    public String formatFilter() {
        return keywords.stream().map(s -> PREFIX_ROLE + s).collect(Collectors.joining(" "));
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(person.getRole()::equalsIgnoreCase);
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
        if (!(other instanceof RoleMatchesKeywordsPredicate)) {
            return false;
        }
        RoleMatchesKeywordsPredicate otherPredicate = (RoleMatchesKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    /**
     * Returns a string representation of the predicate.
     *
     * @return A string representation of the predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
