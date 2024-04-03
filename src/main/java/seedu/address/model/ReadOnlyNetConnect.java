package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.util.IdTuple;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyNetConnect {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons and any duplicate ids.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the related list.
     * This list will not contain any duplicate related persons.
     */
    List<IdTuple> getListIdTuple();
}
