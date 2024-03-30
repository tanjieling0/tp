package seedu.address.model.util;

import java.io.Serializable;
import java.util.ArrayList;

import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class RelatedList extends ArrayList<IdTuple> implements Serializable {
    private static ArrayList<IdTuple> relatedPersons = new ArrayList<>();

    public RelatedList() {
        relatedPersons = new ArrayList<>();
    }

    public ArrayList<IdTuple> getRelatedIdList() {
        return relatedPersons;
    }

    /**
     * Converts a `String` list of related persons to a RelatedList.
     *
     * @return The string representation of the list of related persons.
     */
    public RelatedList toArrayList(String string) {
        if (string.equals("")) {
            return new RelatedList();
        }
        string = string.replace("]", "");
        string = string.replace("[", "");
        RelatedList relatedList = new RelatedList();
        String[] idTuples = string.split(", ");
        for (String idTuple : idTuples) {
            String[] ids = idTuple.split("relates");
            Id id1 = Id.generateTempId(Integer.parseInt(ids[0]));
            Id id2 = Id.generateTempId(Integer.parseInt(ids[1]));
            relatedList.add(new IdTuple(id1, id2));
        }
        return relatedList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RelatedList)) {
            return false;
        }

        RelatedList otherList = (RelatedList) other;

        return relatedPersons.equals(otherList.getRelatedIdList());
    }

    @Override
    public IdTuple get(int index) {
        return relatedPersons.get(index);
    }

    @Override
    public boolean add(IdTuple idTuple) {
        assert idTuple != null : "PersonTuple should not be null";
        relatedPersons.add(idTuple);
        return false;
    }

    @Override
    public boolean contains(Object o) {
        assert o != null : "PersonTuple should not be null";
        return relatedPersons.contains(o);
    }

    @Override
    public boolean remove(Object o) {
        assert o != null : "PersonTuple should not be null";
        return relatedPersons.remove(o);
    }

    @Override
    public int size() {
        return relatedPersons.size();
    }

    @Override
    public boolean isEmpty() {
        return relatedPersons.isEmpty();
    }

    @Override
    public String toString() {
        return relatedPersons.toString();
    }

}
