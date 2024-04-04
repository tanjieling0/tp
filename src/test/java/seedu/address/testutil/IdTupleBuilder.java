package seedu.address.testutil;

import seedu.address.model.person.Id;
import seedu.address.model.util.IdTuple;

/**
 * The IdTupleBuilder class is a utility class for building IdTuple objects.
 * It provides methods for setting the first and second person IDs, and for building an IdTuple object.
 */
public class IdTupleBuilder {
    public static final int DEFAULT_FIRST_PERSON_ID = 1;
    public static final int DEFAULT_SECOND_PERSON_ID = 2;

    private int firstPersonId;
    private int secondPersonId;

    /**
     * Constructs a new IdTupleBuilder with default values.
     */
    public IdTupleBuilder() {
        firstPersonId = DEFAULT_FIRST_PERSON_ID;
        secondPersonId = DEFAULT_SECOND_PERSON_ID;
    }

    /**
     * Sets the first person ID of the IdTuple that we are building.
     *
     * @param firstPersonId The first person ID.
     * @return The IdTupleBuilder with the first person ID set to {@code firstPersonId}.
     */
    public IdTupleBuilder withFirstPersonId(int firstPersonId) {
        this.firstPersonId = firstPersonId;
        return this;
    }

    /**
     * Sets the second person ID of the IdTuple that we are building.
     *
     * @param secondPersonId The second person ID.
     * @return The IdTupleBuilder with the second person ID set to {@code secondPersonId}.
     */
    public IdTupleBuilder withSecondPersonId(int secondPersonId) {
        this.secondPersonId = secondPersonId;
        return this;
    }

    /**
     * Returns the first person ID of the IdTuple that we are building.
     *
     * @return The first person ID.
     */
    public int getFirstPersonId() {
        return firstPersonId;
    }

    /**
     * Returns the second person ID of the IdTuple that we are building.
     *
     * @return The second person ID.
     */
    public int getSecondPersonId() {
        return secondPersonId;
    }

    public IdTuple build() {
        return new IdTuple(Id.generateTempId(firstPersonId), Id.generateTempId(secondPersonId));
    }
}
