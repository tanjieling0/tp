package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Id;

public class RelatedListTest {

    private RelatedList relatedList;

    @BeforeEach
    public void setUp() {
        relatedList = new RelatedList();
    }

    @Test
    public void allowAddIdTuple_validIdTuple_returnsTrue() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        assertTrue(relatedList.allowAddIdTuple(idTuple));
    }

    @Test
    public void allowAddIdTuple_duplicateIdTuple_returnsFalse() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        relatedList.allowAddIdTuple(idTuple);
        assertFalse(relatedList.allowAddIdTuple(idTuple));
    }

    @Test
    public void hasId_relatedListContainsIdTuple_returnsTrue() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        relatedList.allowAddIdTuple(idTuple);
        assertTrue(relatedList.hasId(idTuple));
    }

    @Test
    public void hasId_relatedListDoesNotContainIdTuple_returnsFalse() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        assertFalse(relatedList.hasId(idTuple));
    }

    @Test
    public void remove_relatedListContainsIdTuple_returnsTrue() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        relatedList.allowAddIdTuple(idTuple);
        assertTrue(relatedList.remove(idTuple));
    }

    @Test
    public void remove_relatedListDoesNotContainIdTuple_returnsFalse() {
        IdTuple idTuple = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        assertFalse(relatedList.remove(idTuple));
    }

    @Test
    public void getAllRelatedIds_relatedListContainsId_returnsListOfRelatedIds() {
        IdTuple idTuple1 = new IdTuple(Id.generateTempId(1), Id.generateTempId(2));
        IdTuple idTuple2 = new IdTuple(Id.generateTempId(2), Id.generateTempId(3));
        relatedList.allowAddIdTuple(idTuple1);
        relatedList.allowAddIdTuple(idTuple2);

        assertEquals(1, relatedList.getAllRelatedIds(relatedList, Id.generateTempId(1)).size());
        assertEquals(2, relatedList.getAllRelatedIds(relatedList, Id.generateTempId(2)).size());
        assertEquals(1, relatedList.getAllRelatedIds(relatedList, Id.generateTempId(3)).size());
    }

    @Test
    public void asUnmodifiableObservableList_returnsUnmodifiableList() {
        assertTrue(relatedList.asUnmodifiableObservableList().isEmpty());
        assertEquals(0, relatedList.asUnmodifiableObservableList().size());
    }

    @Test
    public void iterator_returnsIterator() {
        assertFalse(relatedList.iterator().hasNext());
    }

    @Test
    public void size_emptyRelatedList_returnsZero() {
        assertEquals(0, relatedList.size());
    }

    @Test
    public void isEmpty_emptyRelatedList_returnsTrue() {
        assertTrue(relatedList.isEmpty());
    }

    @Test
    public void toString_emptyRelatedList_returnsEmptyString() {
        assertEquals("[]", relatedList.toString());
    }
}
