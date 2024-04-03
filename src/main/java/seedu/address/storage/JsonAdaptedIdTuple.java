package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Id;
import seedu.address.model.util.IdTuple;

/**
 * Represents a JSON-adapted version of the IdTuple.
 * An instance of this class is used to serialize/deserialize IdTuple objects to/from JSON format.
 */
class JsonAdaptedIdTuple {
    private final String firstPersonId;
    private final String secondPersonId;

    /**
     * Represents a JSON-adapted version of the IdTuple.
     * An instance of this class is used to marshal/unmarshal IdTuple objects to/from JSON.
     */
    public JsonAdaptedIdTuple(@JsonProperty("firstPersonId") String firstPersonId,
            @JsonProperty("secondPersonId") String secondPersonId) {
        requireNonNull(firstPersonId);
        requireNonNull(secondPersonId);
        this.firstPersonId = firstPersonId;
        this.secondPersonId = secondPersonId;
    }

    /**
     * Represents a JSON-adapted version of the IdTuple.
     * JsonAdaptedIdTuple is used for JSON serialization and deserialization of IdTuple objects.
     */
    public JsonAdaptedIdTuple(IdTuple source) {
        this.firstPersonId = source.getFirstPersonId().toString();
        this.secondPersonId = source.getSecondPersonId().toString();
    }

    /**
     * Returns the ID of the first person in the tuple.
     *
     * @return the ID of the first person
     */
    public String getFirstPersonId() {
        return firstPersonId;
    }

    /**
     * Returns the ID of the second person in the tuple.
     *
     * @return the ID of the second person
     */
    public String getSecondPersonId() {
        return secondPersonId;
    }

    public IdTuple toModelType() {
        return new IdTuple(Id.generateTempId(Integer.parseInt(firstPersonId)),
                Id.generateTempId(Integer.parseInt(secondPersonId)));
    }
}
