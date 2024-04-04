package seedu.address.model.person.filter;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Remark} contains any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate extends NetConnectPredicate<Person> {
    private final boolean hasEmptyKeyword;
    private final List<String> keywords;

    /**
     * Constructs a {@code RemarkContainsKeywordsPredicate} with a list of keywords.
     *
     * @param keywords The list of keywords to match against the person's remarks.
     */
    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords.stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
        this.hasEmptyKeyword = keywords.stream().anyMatch(String::isBlank);
    }

    @Override
    public String formatFilter() {
        return keywords.stream().map(s -> PREFIX_REMARK + s).collect(Collectors.joining(" "))
                + (hasEmptyKeyword ? PREFIX_REMARK + "EMPTY-REMARK" : "");
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getRemark().value, keyword))
                || (hasEmptyKeyword && person.getRemark().value.isBlank());
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
        return keywords.equals(otherRemarkContainsKeywordsPredicate.keywords)
                && (hasEmptyKeyword == otherRemarkContainsKeywordsPredicate.hasEmptyKeyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("remarks", keywords).toString();
    }
}
