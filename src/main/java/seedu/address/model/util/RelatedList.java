package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class RelatedList {
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
        RelatedList relatedList = new RelatedList();
        if (string.equals("")) {
            return relatedList;
        }
        string = string.replace("]", "").replace("[", "");
        String[] idTuples = string.split(", ");

        for (String idTuple : idTuples) {
            String[] ids = idTuple.split("relates");
            Id id1 = Id.generateTempId(Integer.parseInt(ids[0]));
            Id id2 = Id.generateTempId(Integer.parseInt(ids[1]));
            relatedPersons.add(new IdTuple(id1, id2));
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

    public IdTuple get(int index) {
        return relatedPersons.get(index);
    }


    public void add(IdTuple idTuple) {
        requireNonNull(idTuple);
        relatedPersons.add(idTuple);
    }

    public boolean contains(Object o) {
        requireNonNull(o);
        for (IdTuple idTuple : relatedPersons) {
            if (idTuple.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        requireNonNull(o);
        return relatedPersons.remove(o);
    }

    public int size() {
        return relatedPersons.size();
    }

    public boolean isEmpty() {
        return relatedPersons.isEmpty();
    }

    @Override
    public String toString() {
        return relatedPersons.toString();
    }

}
