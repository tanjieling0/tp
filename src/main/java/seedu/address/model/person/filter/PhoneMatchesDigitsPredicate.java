package seedu.address.model.person.filter;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneMatchesDigitsPredicate extends NetConnectPredicate<Person> {
    private final List<String> phones;

    public PhoneMatchesDigitsPredicate(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public String formatFilter() {
        return phones.stream().map(keyword -> "p/" + keyword).collect(Collectors.joining(" "));
    }

    @Override
    public boolean test(Person person) {
        return phones.stream().anyMatch(person.getPhone().value::equals);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneMatchesDigitsPredicate)) {
            return false;
        }

        PhoneMatchesDigitsPredicate otherNameContainsKeywordsPredicate = (PhoneMatchesDigitsPredicate) other;
        return phones.equals(otherNameContainsKeywordsPredicate.phones);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phones", phones).toString();
    }
}
