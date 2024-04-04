package seedu.address.model;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NetConnectPredicate;
import seedu.address.model.util.IdTuple;
import seedu.address.model.util.RelatedList;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' netconnect file path.
     */
    Path getNetConnectFilePath();

    /**
     * Sets the user prefs' netconnect file path.
     */
    void setNetConnectFilePath(Path netConnectFilePath);

    /**
     * Replaces netconnect data with the data in {@code netConnect}.
     */
    void setNetConnect(ReadOnlyNetConnect netConnect);

    /**
     * Returns the NetConnect
     */
    ReadOnlyNetConnect getNetConnect();

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the netconnect.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the specified id exists in the NetConnect.
     */
    boolean hasId(Id id);

    /**
     * Returns the {@code Person} with the specified id.
     */
    Person getPersonById(Id id);

    /**
     * Returns the number of {@code Person}s in the NetConnect with
     * the specified name.
     */
    int countPersonsWithName(Name name);

    /**
     * Returns the {@code Person} with the specified name.
     */
    Person getPersonByName(Name name);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the NetConnect.
     * {@code target} and {@code editedPerson} must have the same id.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Clears all filters of the filtered person list. Displays all persons.
     *
     */
    void clearFilter();

    /**
     * Updates the existing filter of the filtered person list with an
     * additional filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void stackFilters(NetConnectPredicate<Person> predicate);

    /**
     * Returns the current filters applied in a user readable format.
     */
    String printFilters();

    /**
     * Exports the data from the address book as a CSV file with the specified filename.
     * Returns {@code true} if the export operation is successful, {@code false} otherwise.
     */
    boolean exportCsv(String filename);

    boolean hasRelatedIdTuple(IdTuple idTuple);

    void addRelatedIdTuple(IdTuple idTuple);

    boolean removeRelatedIdTuple(IdTuple idTuple);

    RelatedList getRelatedIdTuples();
}
