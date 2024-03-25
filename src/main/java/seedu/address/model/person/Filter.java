package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Collection of filters that is applied to the displayed NetConnect person list.
 */
public class Filter implements Predicate<Person> {

    public static final String MESSAGE_FILTERS_APPLIED = "Current filters applied:\n%1$s";

    private List<Predicate<Person>> filters;

    /**
     * Returns a {@code Filter} object with only the given predicate.
     * @param predicate
     */
    public Filter(Predicate<Person> predicate) {
        filters = new ArrayList<>();
        filters.add(predicate);
    }

    /**
     * Returns a {@code Filter} object with the given list of predicates.
     * @param predicates
     */
    public Filter(List<Predicate<Person>> predicates) {
        filters = predicates;
    }

    /**
     * Returns a new {@code Filter} object with the given predicate added with the
     * existing predicates.
     */
    public Filter add(Predicate<Person> p) {
        filters.add(p);
        return new Filter(filters);
    }

    public void clear() {
        filters.clear();
    }

    @Override
    public boolean test(Person p) {
        return filters.stream().allMatch(predicate -> predicate.test(p));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Filter)) {
            return false;
        }

        Filter otherFilter = (Filter) other;
        return filters.equals(otherFilter.filters);
    }

    @Override
    public int hashCode() {
        return filters.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("filters", filters).toString();
    }
}
