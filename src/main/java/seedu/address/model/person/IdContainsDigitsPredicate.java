package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code ID} matches any of the IDs given.
 */
public class IdContainsDigitsPredicate implements Predicate<Person> {
    private final List<Integer> ids;

    public IdContainsDigitsPredicate(List<Integer> ids) {
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
        if (!(other instanceof IdContainsDigitsPredicate)) {
            return false;
        }

        IdContainsDigitsPredicate otherIdContainsDigitsPredicate = (IdContainsDigitsPredicate) other;
        return ids.equals(otherIdContainsDigitsPredicate.ids);
    }

    // method not used, issues with converting uninstantiated List<Integer> to List<String> for use by ToStringBuilder.
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).add("IDs", ids.stream().map(i -> Integer.toString(i))).toString();
//    }
}
