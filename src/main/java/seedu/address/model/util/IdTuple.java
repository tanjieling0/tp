package seedu.address.model.util;


import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class IdTuple {
    private final Id firstPersonId;
    private final Id secondPersonId;

    public IdTuple(Id firstPersonId, Id secondPersonId) {
        this.firstPersonId = firstPersonId;
        this.secondPersonId = secondPersonId;
    }

    // needs a checker for if id is valid, construct the person tuple, and have an overloaded constructor

    public Id getFirstPersonId() {
        return secondPersonId;
    }

    public Id getSecondPersonId() {
        return secondPersonId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IdTuple)) {
            return false;
        }

        IdTuple otherTuple = (IdTuple) other;

        // can consider in the future if it gets computationally inefficient to compare all person fields to compare by
        // unique id instead, though compare by name, if chosen, must be done with care for duplicate names.

        if (firstPersonId.equals(otherTuple.getFirstPersonId()) && secondPersonId.equals(otherTuple.getSecondPersonId())) {
            return true;
        }

        return firstPersonId.equals(otherTuple.getSecondPersonId())
                && secondPersonId.equals(otherTuple.getFirstPersonId());
    }

    @Override
    public String toString() {
        return "(" + firstPersonId + " " + secondPersonId + ")";
    }
}
