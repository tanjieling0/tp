package seedu.address.model.person.filter;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_FILTERS_APPLIED = "%1$d filter(s) applied:\n%2$s";

    /** Cached empty filter object */
    private static final Filter EMPTY_FILTER = new Filter(List.of());

    private final List<NetConnectPredicate<Person>> filters;

    /**
     * Returns a {@code Filter} object with the given list of predicates.
     */
    private Filter(List<NetConnectPredicate<Person>> predicates) {
        requireNonNull(predicates);
        filters = Collections.unmodifiableList(predicates);
    }

    /**
     * Returns a Filter with the give list of predicates, or return the
     * empty Filter if the given list of predicates is empty.
     */
    public static Filter of(List<NetConnectPredicate<Person>> predicates) {
        requireNonNull(predicates);
        if (predicates.isEmpty()) {
            return EMPTY_FILTER;
        }
        return new Filter(predicates);
    }

    /**
     * Returns a new empty Filter.
     */
    public static Filter noFilter() {
        return EMPTY_FILTER;
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

    /**
     * Returns the count of predicates in the {@code Filter}.
     */
    public int size() {
        return filters.size();
    }

    /**
     * Formats the filter in a user-readable format.
     */
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
