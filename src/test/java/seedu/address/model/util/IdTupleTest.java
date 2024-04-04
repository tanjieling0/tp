package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Id;

public class IdTupleTest {

    @Test
    public void constructor_validIds_success() {
        Id firstPersonId = Id.generateTempId(1);
        Id secondPersonId = Id.generateTempId(2);
        IdTuple idTuple = new IdTuple(firstPersonId, secondPersonId);
        assertEquals(firstPersonId, idTuple.getFirstPersonId());
        assertEquals(secondPersonId, idTuple.getSecondPersonId());
    }

    @Test
    public void getReversedTuple_validIds_success() {
        Id firstPersonId = Id.generateTempId(1);
        Id secondPersonId = Id.generateTempId(2);
        IdTuple idTuple = new IdTuple(firstPersonId, secondPersonId);
        IdTuple reversedTuple = idTuple.getReversedTuple();
        assertEquals(secondPersonId, reversedTuple.getFirstPersonId());
        assertEquals(firstPersonId, reversedTuple.getSecondPersonId());
    }

    @Test
    public void equals_sameTuple_success() {
        Id firstPersonId = Id.generateTempId(1);
        Id secondPersonId = Id.generateTempId(2);
        IdTuple idTuple1 = new IdTuple(firstPersonId, secondPersonId);
        IdTuple idTuple2 = new IdTuple(firstPersonId, secondPersonId);
        assertEquals(idTuple1, idTuple2);
    }

    @Test
    public void equals_differentTuple_success() {
        Id firstPersonId1 = Id.generateTempId(1);
        Id secondPersonId1 = Id.generateTempId(2);
        IdTuple idTuple1 = new IdTuple(firstPersonId1, secondPersonId1);

        Id firstPersonId2 = Id.generateTempId(3);
        Id secondPersonId2 = Id.generateTempId(4);
        IdTuple idTuple2 = new IdTuple(firstPersonId2, secondPersonId2);

        assertNotEquals(idTuple1, idTuple2);
    }

    @Test
    public void relatesItself_sameIds_success() {
        Id personId = Id.generateTempId(1);
        IdTuple idTuple = new IdTuple(personId, personId);
        assertTrue(idTuple.relatesItself());
    }

    @Test
    public void relatesItself_differentIds_success() {
        Id firstPersonId = Id.generateTempId(1);
        Id secondPersonId = Id.generateTempId(2);
        IdTuple idTuple = new IdTuple(firstPersonId, secondPersonId);
        assertFalse(idTuple.relatesItself());
    }

    @Test
    public void toString_validIds_success() {
        Id firstPersonId = Id.generateTempId(1);
        Id secondPersonId = Id.generateTempId(2);
        IdTuple idTuple = new IdTuple(firstPersonId, secondPersonId);
        String expected = "1relates2";
        assertEquals(expected, idTuple.toString());
    }
}
