package seedu.address.model.person.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Collection of filters that is applied to the displayed NetConnect person list.
 */
public class Filter extends NetConnectPredicate<Person> {

    public static final String MESSAGE_FILTERS_APPLIED = "Current filters applied:\n%1$s";

    private final List<NetConnectPredicate<Person>> filters;

    /**
     * Returns an empty {@code Filter} object.
     */
    public Filter() {
        filters = List.of();
    }

    /**
     * Returns a {@code Filter} object with the given list of predicates.
     * @param predicates
     */
    public Filter(List<NetConnectPredicate<Person>> predicates) {
        filters = predicates;
    }

    /**
     * Returns a new {@code Filter} object with the given predicate added with the
     * existing predicates.
     */
    public Filter add(NetConnectPredicate<Person> p) {
        ArrayList<NetConnectPredicate<Person>> newFilters = new ArrayList<>(filters);
        newFilters.add(p);
        return new Filter(Collections.unmodifiableList(newFilters));
    }

    public Filter clear() {
        return new Filter(List.of());
    }

    @Override
    public String formatFilter() {
        List<String> formatted = filters.stream()
                .map(NetConnectPredicate::formatFilter)
                .collect(Collectors.toList());
        return IntStream.range(1, formatted.size() + 1).boxed()
                .map(i -> i.toString() + ". " + formatted.get(i - 1))
                .collect(Collectors.joining("\n"));
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
