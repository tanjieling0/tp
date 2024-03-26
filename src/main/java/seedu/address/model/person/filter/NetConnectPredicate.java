package seedu.address.model.person.filter;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Represents a predicate used in NetConnect. Supports {@link #formatFilter()}
 * to format the text to output to users.
 */
public abstract class NetConnectPredicate<T> implements Predicate<T> {
    public abstract String formatFilter();

    /**
     * Boxes a predicate into a NetConnectPredicate, with a null formatFilter.
     */
    public static NetConnectPredicate<Person> box(Predicate<Person> predicate) {
        return new NetConnectPredicate<Person>() {
            @Override
            public String formatFilter() {
                return null;
            }

            @Override
            public boolean test(Person person) {
                return predicate.test(person);
            }
        };
    }
}
