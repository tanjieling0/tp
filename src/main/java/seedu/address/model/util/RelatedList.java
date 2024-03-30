package seedu.address.model.util;


import seedu.address.model.person.Person;

import java.util.ArrayList;

/**
 * Contains tuple methods for relate command storage.
 */
public class RelatedList extends ArrayList<PersonTuple>{
    private static ArrayList<PersonTuple> relatedPersons = new ArrayList<>();

    public static ArrayList<PersonTuple> getRelatedPersons() {
        return relatedPersons;
    }

    public void addRelatedPersonsTuple(PersonTuple personTuple) {
        assert personTuple != null : "PersonTuple should not be null";
        relatedPersons.add(personTuple);
    }

    public static boolean containsRelatedTuple(PersonTuple personTuple) {
        assert personTuple != null : "PersonTuple should not be null";
        return relatedPersons.contains(personTuple);
    }

    public static void clearRelatedPersons() {
        relatedPersons.clear();
    }

    public static void removeRelatedTuple(PersonTuple personTuple) {
        assert personTuple != null : "PersonTuple should not be null";
        relatedPersons.remove(personTuple);
    }

    public static int getRelatedPersonsSize() {
        return relatedPersons.size();
    }

    public boolean isEmpty() {
        return relatedPersons.isEmpty();
    }

}
