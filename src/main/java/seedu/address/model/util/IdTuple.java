package seedu.address.model.util;


import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class IdTuple {
    private final Id firstPersonId;
    private final Id secondPersonId;

    /**
     * Constructor for the IdTuple.
     *
     * @param firstPersonId The first person's id.
     * @param secondPersonId The second person's id.
     */
    public IdTuple(Id firstPersonId, Id secondPersonId) {
        this.firstPersonId = firstPersonId;
        this.secondPersonId = secondPersonId;
    }

    // needs a checker for if id is valid, construct the person tuple, and have an overloaded constructor

    public Id getFirstPersonId() {
        return firstPersonId;
    }

    public Id getSecondPersonId() {
        return secondPersonId;
    }

    public IdTuple getReversedTuple() {
        return new IdTuple(secondPersonId, firstPersonId);
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

        if (firstPersonId.equals(otherTuple.getFirstPersonId())
                && secondPersonId.equals(otherTuple.getSecondPersonId())) {
            return true;
        }

        return firstPersonId.equals(otherTuple.getSecondPersonId())
                && secondPersonId.equals(otherTuple.getFirstPersonId());
    }

    public boolean relatesItself() {
        return firstPersonId.equals(secondPersonId);
    }

    @Override
    public String toString() {
        return firstPersonId + "relates" + secondPersonId;
    }
}
