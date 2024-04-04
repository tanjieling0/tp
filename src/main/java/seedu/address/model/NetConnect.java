package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.util.IdTuple;
import seedu.address.model.util.RelatedList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class NetConnect implements ReadOnlyNetConnect {

    private final UniquePersonList persons;
    private final RelatedList relatedList;

    /**
     * Represents the main class for the NetConnect application.
     * It contains the constructor for initializing the application.
     */
    public NetConnect() {
        persons = new UniquePersonList();
        relatedList = new RelatedList();
    }

    /**
     * Creates an NetConnect using the Persons in the {@code toBeCopied}
     */
    public NetConnect(ReadOnlyNetConnect toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the related list with {@code relatedList}.
     * {@code relatedList} must not contain duplicate related persons.
     */
    public void setRelatedList(List<IdTuple> relatedList) {
        this.relatedList.setRelatedList(relatedList);
    }

    /**
     * Resets the existing data of this {@code NetConnect} with {@code newData}.
     */
    public void resetData(ReadOnlyNetConnect newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setRelatedList(newData.getListIdTuple());

    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the specified id exists in the NetConnect.
     */
    public boolean hasId(Id id) {
        requireNonNull(id);
        return persons.hasId(id);
    }

    /**
     * Returns the {@code Person} with the specified id.
     */
    public Person getPersonById(Id id) {
        requireNonNull(id);
        return persons.getPersonById(id);
    }

    /**
     * Returns true if the NetConnect has exactly one {@code Person}
     * with the specified name.
     */
    public int countPersonsWithName(Name name) {
        requireNonNull(name);
        return persons.countPersonsWithName(name);
    }

    /**
     * Returns the {@code Person} with the specified name.
     */
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        return persons.getPersonByName(name);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the list.
     * {@code target} and {@code editedPerson} must have the same id.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code NetConnect}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// related list operations
    /**
     * Returns true if the specified IdTuple is present in the relatedList, false otherwise.
     *
     * @param idTuple The IdTuple to check for presence in the relatedList. Must not be null.
     * @return true if the specified IdTuple is present in the relatedList, false otherwise.
     */
    public boolean hasRelatedId(IdTuple idTuple) {
        requireNonNull(idTuple);
        return relatedList.hasId(idTuple);
    }

    /**
     * Allows the addition of an IdTuple to the NetConnect model.
     *
     * @param idTuple The IdTuple to be added.
     * @throws NullPointerException if idTuple is null.
     */
    public void allowAddIdTuple(IdTuple idTuple) {
        requireNonNull(idTuple);
        relatedList.allowAddIdTuple(idTuple);
    }

    /**
     * Removes the specified IdTuple from the relatedList.
     *
     * @param idTuple The IdTuple to be removed.
     * @return true if the IdTuple is removed, false otherwise.
     */
    public boolean removeRelatedId(IdTuple idTuple) {
        requireNonNull(idTuple);
        return relatedList.remove(idTuple);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    /**
     * Returns an unmodifiable view of the person list.
     * This list will not contain any duplicate persons.
     *
     * @return An unmodifiable {@link ObservableList} of {@link Person}.
     */
    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns a list of IdTuple objects.
     *
     * @return A list of IdTuple objects.
     */
    @Override
    public List<IdTuple> getListIdTuple() {
        return relatedList.getListIdTuple();
    }

    /**
     * Represents a list of related items.
     */
    public RelatedList getRelatedList() {
        return relatedList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NetConnect)) {
            return false;
        }

        NetConnect otherNetConnect = (NetConnect) other;
        return persons.equals(otherNetConnect.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
