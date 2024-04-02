package seedu.address.model.person.filter;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsDigitsPredicate extends NetConnectPredicate<Person> {
    private final List<String> phones;

    public PhoneContainsDigitsPredicate(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public String formatFilter() {
        return phones.stream().map(keyword -> "p/" + keyword).collect(Collectors.joining(" "));
    }

    @Override
    public boolean test(Person person) {
        return phones.stream()
                .anyMatch(phone -> StringUtil.containsWordIgnoreCase(person.getPhone().value, phone));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsDigitsPredicate)) {
            return false;
        }

        PhoneContainsDigitsPredicate otherNameContainsKeywordsPredicate = (PhoneContainsDigitsPredicate) other;
        return phones.equals(otherNameContainsKeywordsPredicate.phones);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phones", phones).toString();
    }
}
