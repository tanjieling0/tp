package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class RelatedList {
    private static ArrayList<IdTuple> relatedPersons = new ArrayList<>();

    public RelatedList() {
        relatedPersons = new ArrayList<>();
    }

    public RelatedList getRelatedIdList() {
        return this;
    }

    /**
     * Converts a `String` list of related persons to a RelatedList.
     *
     * @return The string representation of the list of related persons.
     */
    public RelatedList toArrayList(String string) {
        RelatedList relatedList = new RelatedList();
        if (string.equals("") || string.equals("[]")) {
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

    /**
     * Adds a related person to the list.
     *
     * @param idTuple The related person to be added.
     * @return True if the related person is added, false otherwise.
     */
    public boolean allowAddIdTuple(IdTuple idTuple) {
        requireNonNull(idTuple);
        for (IdTuple oneIdTuple : relatedPersons) {
            if (oneIdTuple.equals(idTuple)) {
                return true;
            }
        }
        relatedPersons.add(idTuple);
        return false;
    }

    /**
     * Checks if the list contains the related person.
     *
     * @param idTuple The related person to be checked.
     * @return True if the related person is in the list, false otherwise.
     */
    public boolean hasId(IdTuple idTuple) {
        requireNonNull(idTuple);
        for (IdTuple oneIdTuple : relatedPersons) {
            if (oneIdTuple.equals(idTuple)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a related person to the list.
     *
     * @param idTuple The related person to be added.
     * @return True if the related person is added, false otherwise.
     */
    public boolean remove(IdTuple idTuple) {
        requireNonNull(idTuple);
        return relatedPersons.remove(idTuple);
    }

    public int size() {
        return relatedPersons.size();
    }

    public boolean isEmpty() {
        return relatedPersons.isEmpty();
    }

    public String toString() {
        return relatedPersons.toString();
    }

}
