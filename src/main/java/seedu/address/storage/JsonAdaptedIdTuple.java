package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Id;
import seedu.address.model.util.IdTuple;

class JsonAdaptedIdTuple {
    private final String firstPersonId;
    private final String secondPersonId;

    public JsonAdaptedIdTuple(@JsonProperty("firstPersonId") String firstPersonId,
            @JsonProperty("secondPersonId") String secondPersonId) {
        this.firstPersonId = firstPersonId;
        this.secondPersonId = secondPersonId;
    }

    public JsonAdaptedIdTuple(IdTuple source) {
        this.firstPersonId = source.getFirstPersonId().toString();
        this.secondPersonId = source.getSecondPersonId().toString();
    }

    public String getFirstPersonId() {
        return firstPersonId;
    }

    public String getSecondPersonId() {
        return secondPersonId;
    }

    public IdTuple toModelType() {
        return new IdTuple(Id.generateTempId(Integer.parseInt(firstPersonId)),
                Id.generateTempId(Integer.parseInt(secondPersonId)));
    }
}