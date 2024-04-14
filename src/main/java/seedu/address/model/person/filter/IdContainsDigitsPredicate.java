package seedu.address.model.person.filter;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code ID} matches any of the IDs given.
 */
public class IdContainsDigitsPredicate extends NetConnectPredicate<Person> {
    private final List<Integer> ids;

    public IdContainsDigitsPredicate(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String formatFilter() {
        return ids.stream()
                .map(id -> "i/" + id).collect(Collectors.joining(" "));
    }

    @Override
    public boolean test(Person person) {
        return ids.stream()
                .anyMatch(id -> person.getId().value == id);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IdContainsDigitsPredicate)) {
            return false;
        }

        IdContainsDigitsPredicate otherIdContainsDigitsPredicate = (IdContainsDigitsPredicate) other;
        return ids.equals(otherIdContainsDigitsPredicate.ids);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ids", ids)
                .toString();
    }

}
