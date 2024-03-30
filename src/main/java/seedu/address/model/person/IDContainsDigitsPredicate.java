package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code ID} matches any of the IDs given.
 */
public class IDContainsDigitsPredicate implements Predicate<Person> {
    private final List<Integer> ids;

    public IDContainsDigitsPredicate(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public int getFirstId() {
        return ids.get(0);
    }

    public int getSecondId() {
        return ids.get(1);
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
        if (!(other instanceof IDContainsDigitsPredicate)) {
            return false;
        }

        IDContainsDigitsPredicate otherIDContainsDigitsPredicate = (IDContainsDigitsPredicate) other;
        return ids.equals(otherIDContainsDigitsPredicate.ids);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("IDs", ids).toString();
    }
}
