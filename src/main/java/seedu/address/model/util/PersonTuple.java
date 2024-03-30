package seedu.address.model.util;


import seedu.address.model.person.Person;

/**
 * Contains tuple methods for relate command storage.
 */
public class PersonTuple {
    private final Person firstPerson;
    private final Person secondPerson;

    public PersonTuple(Person firstPerson, Person secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
    }

    // needs a checker for if id is valid, construct the person tuple, and have an overloaded constructor

    public Person getFirstPerson() {
        return firstPerson;
    }

    public Person getSecondPerson() {
        return secondPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        PersonTuple otherPerson = (PersonTuple) other;

        // can consider in the future if it gets computationally inefficient to compare all person fields to compare by
        // unique id instead, though compare by name, if chosen, must be done with care for duplicate names.

        if (firstPerson.equals(otherPerson.getFirstPerson()) && secondPerson.equals(otherPerson.getSecondPerson())) {
            return true;
        }

        return firstPerson.equals(otherPerson.getSecondPerson())
                && secondPerson.equals(otherPerson.getFirstPerson());
    }

    @Override
    public String toString() {
        return "[" + firstPerson + " " + secondPerson + "]";
    }
}
