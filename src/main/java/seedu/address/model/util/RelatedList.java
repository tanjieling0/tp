package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Id;

/**
 * Contains tuple methods for relate command storage.
 */
public class RelatedList implements Iterable<IdTuple> {

    private final ObservableList<IdTuple> relatedPersons = FXCollections.observableArrayList();
    private final ObservableList<IdTuple> relatedPersonsUnmodifiableList =
            FXCollections.unmodifiableObservableList(relatedPersons);

    public List<IdTuple> getListIdTuple() {
        return relatedPersons;
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

        return relatedPersons.equals(otherList);
    }

    public IdTuple get(int index) {
        return relatedPersons.get(index);
    }

    public void setRelatedList(List<IdTuple> idTuples) {
        relatedPersons.addAll(idTuples);
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
                return false;
            }
        }
        relatedPersons.add(idTuple);
        return true;
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
        if (relatedPersons.contains(idTuple)) {
            relatedPersons.remove(idTuple);
            return true;
        }
        return relatedPersons.remove(idTuple.getReversedTuple());
    }

    /**
     * Retrieves all related IDs from the RelatedList.
     *
     * @param relatedList The list of related persons.
     * @param id          The ID to be checked.
     * @return The list of related IDs.
     */
    public List<Integer> getAllRelatedIds(RelatedList relatedList, Id id) {
        List<Integer> relatedIds = new ArrayList<>();

        // Iterate through all IdTuple objects in the RelatedList
        for (int i = 0; i < relatedList.size(); i++) {
            assert i >= 0 && i < relatedList.size();

            IdTuple idTuple = relatedList.get(i);

            // Check if the provided ID matches either the first or the second ID in the
            // tuple
            if (idTuple.getFirstPersonId().equals(id)) {
                relatedIds.add(idTuple.getSecondPersonId().value);
            } else if (idTuple.getSecondPersonId().equals(id)) {
                relatedIds.add(idTuple.getFirstPersonId().value);
            }
        }
        return relatedIds;
    }

    public ObservableList<IdTuple> asUnmodifiableObservableList() {
        return relatedPersonsUnmodifiableList;
    }

    @Override
    public Iterator<IdTuple> iterator() {
        return relatedPersons.iterator();
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
