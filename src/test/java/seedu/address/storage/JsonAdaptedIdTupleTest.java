package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Id;
import seedu.address.model.util.IdTuple;

public class JsonAdaptedIdTupleTest {

    private static final String VALID_FIRST_PERSON_ID = "1";
    private static final String VALID_SECOND_PERSON_ID = "2";

    @Test
    public void constructor_validIdTuple_success() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        JsonAdaptedIdTuple jsonAdaptedIdTuple = new JsonAdaptedIdTuple(idTuple);
        assertNotNull(jsonAdaptedIdTuple);
    }

    @Test
    public void constructor_nullFirstPersonId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedIdTuple(null, VALID_SECOND_PERSON_ID));
    }

    @Test
    public void constructor_nullSecondPersonId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedIdTuple(VALID_FIRST_PERSON_ID, null));
    }

    @Test
    public void toModelType_validIdTuple_returnsIdTuple() throws IllegalValueException {
        JsonAdaptedIdTuple jsonAdaptedIdTuple = new JsonAdaptedIdTuple(VALID_FIRST_PERSON_ID, VALID_SECOND_PERSON_ID);
        IdTuple idTuple = jsonAdaptedIdTuple.toModelType();
        assertEquals(Id.generateTempId(Integer.parseInt(VALID_FIRST_PERSON_ID)), idTuple.getFirstPersonId());
        assertEquals(Id.generateTempId(Integer.parseInt(VALID_SECOND_PERSON_ID)), idTuple.getSecondPersonId());
    }

    @Test
    public void toJsonString_validIdTuple_returnsJsonString() throws JsonProcessingException {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        JsonAdaptedIdTuple jsonAdaptedIdTuple = new JsonAdaptedIdTuple(idTuple);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonAdaptedIdTuple);
        assertNotNull(jsonString);
    }
}
