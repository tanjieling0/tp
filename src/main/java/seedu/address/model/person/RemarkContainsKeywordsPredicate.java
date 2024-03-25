package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Remark} contains any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getRemark().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkContainsKeywordsPredicate)) {
            return false;
        }

        RemarkContainsKeywordsPredicate otherRemarkContainsKeywordsPredicate = (RemarkContainsKeywordsPredicate) other;
        return keywords.equals(otherRemarkContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("remarks", keywords).toString();
    }
}
