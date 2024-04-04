package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateIdException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.IdModifiedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. Unique ids is also enforced.
 * As such, adding and updating of persons uses Person#isSamePerson(Person) for equality so as to ensure that
 * the person being added or updated is unique in terms of identity in the UniquePersonList. However, the removal of a
 * person uses Person#equals(Object) so as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains a person with the same id as in the given argument.
     */
    public boolean hasId(Id toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().map(Person::getId).anyMatch(id -> id.equals(toCheck));
    }

    /**
     * Returns the first Person in the list with the same id as in the given argument.
     *
     * @throws PersonNotFoundException if no Person in the list has the {@code id}
     */
    public Person getPersonById(Id id) {
        requireNonNull(id);
        return internalList.stream().filter(p -> p.getId().equals(id))
                .findFirst().orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Returns true if the list has exactly one {@code Person}
     * with the specified name. The check is case-insensitive.
     */
    public int countPersonsWithName(Name toCheck) {
        requireNonNull(toCheck);
        return (int) internalList.stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(toCheck.fullName)).count();
    }

    /**
     * Returns the first Person in the list with the same name as in the given argument.
     * The match is case-insensitive.
     *
     * @throws PersonNotFoundException if no Person in the list has the {@code name}
     */
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        if (countPersonsWithName(name) != 1) {
            throw new PersonNotFoundException();
        }
        return internalList.stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(name.fullName))
                .findFirst().orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        if (hasId(toAdd.getId())) {
            throw new DuplicateIdException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     * {@code target} and {@code editedPerson} must have the same id.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        if (!target.getId().equals(editedPerson.getId())) {
            throw new IdModifiedException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons or persons with duplicate ids.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        if (!idsAreUnique(persons)) {
            throw new DuplicateIdException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if {@code persons} contains only unique ids.
     */
    private boolean idsAreUnique(List<Person> persons) {
        return persons.stream().map(Person::getId).distinct().count() == persons.size();
    }
}
