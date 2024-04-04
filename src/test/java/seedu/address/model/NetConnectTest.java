package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Client;
import seedu.address.model.person.Employee;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.util.IdTuple;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.SupplierBuilder;

public class NetConnectTest {

    private final NetConnect netConnect = new NetConnect();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), netConnect.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNetConnect_replacesData() {
        NetConnect newData = getTypicalNetConnect();
        netConnect.resetData(newData);
        assertEquals(newData, netConnect);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        Client editedAliceClient = new ClientBuilder(ALICE).withPreferences("New Preferences").build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAliceClient);
        NetConnectStub newData = new NetConnectStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> netConnect.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInNetConnect_returnsFalse() {
        assertFalse(netConnect.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        assertTrue(netConnect.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        Client editedAliceClient = new ClientBuilder(ALICE).withPreferences("Different Preferences").build();
        assertTrue(netConnect.hasPerson(editedAliceClient));
    }

    @Test
    public void hasId_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.hasId(null));
    }

    @Test
    public void hasId_idNotInNetConnect_returnsFalse() {
        assertFalse(netConnect.hasId(ALICE.getId()));
    }

    @Test
    public void hasId_idInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        assertTrue(netConnect.hasId(ALICE.getId()));
    }

    @Test
    public void getPersonById_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.getPersonById(null));
    }

    @Test
    public void getPersonById_idNotInNetConnect_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> netConnect.getPersonById(ALICE.getId()));
    }

    @Test
    public void getPersonById_idInNetConnect_returnsPerson() {
        netConnect.addPerson(ALICE);
        assertEquals(ALICE, netConnect.getPersonById(ALICE.getId()));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> netConnect.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = NetConnect.class.getCanonicalName() + "{persons=" + netConnect.getPersonList() + "}";
        assertEquals(expected, netConnect.toString());
    }

    /**
     * Test case to verify that the `addPerson` method handles different roles with
     * the same identity fields correctly.
     * It adds an `ALICE` person to the `netConnect` object as an employee and then
     * as a supplier.
     * The method asserts that no exceptions are thrown during the process.
     */
    @Test
    public void addPerson_differentRolesWithSameIdentityFields_throwsDuplicatePersonException() {
        netConnect.addPerson(ALICE);
        Employee aliceEmployee = new EmployeeBuilder().withName(ALICE.getName().toString())
                .withPhone(ALICE.getPhone().toString())
                .withEmail(ALICE.getEmail().toString())
                .withAddress(ALICE.getAddress().toString())
                .withDepartment("Engineering")
                .withJobTitle("Engineer")
                .build();

        assertThrows(DuplicatePersonException.class, () -> netConnect.addPerson(aliceEmployee));

        Supplier aliceSupplier = new SupplierBuilder().withName(ALICE.getName().toString())
                .withPhone(ALICE.getPhone().toString())
                .withEmail(ALICE.getEmail().toString())
                .withAddress(ALICE.getAddress().toString())
                .withTermsOfService("1 Year Warranty")
                .build();

        assertThrows(DuplicatePersonException.class, () -> netConnect.addPerson(aliceSupplier));
    }

    @Test
    public void hasPerson_diffRolesWithSameIdentityFieldsInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        Employee aliceEmployee = new EmployeeBuilder().withName(ALICE.getName().toString())
                .withPhone(ALICE.getPhone().toString())
                .withEmail(ALICE.getEmail().toString())
                .withAddress(ALICE.getAddress().toString())
                .withDepartment("Engineering")
                .build();
        assertTrue(netConnect.hasPerson(aliceEmployee));
    }

    @Test
    public void hasRelatedId_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.hasRelatedId(null));
    }

    @Test
    public void hasRelatedId_idNotInNetConnect_returnsFalse() {
        assertFalse(netConnect.hasRelatedId(new IdTuple(ALICE.getId(), BOB.getId())));
    }

    @Test
    public void hasRelatedId_idInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        netConnect.addPerson(BOB);
        netConnect.allowAddIdTuple(new IdTuple(ALICE.getId(), BOB.getId()));
        assertTrue(netConnect.hasRelatedId(new IdTuple(ALICE.getId(), BOB.getId())));
    }

    @Test
    public void allowAddIdTuple_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.allowAddIdTuple(null));
    }

    @Test
    public void removeRelatedId_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> netConnect.removeRelatedId(null));
    }

    @Test
    public void removeRelatedId_idNotInNetConnect_returnsFalse() {
        assertFalse(netConnect.removeRelatedId(new IdTuple(ALICE.getId(), BOB.getId())));
    }

    @Test
    public void removeRelatedId_idInNetConnect_returnsTrue() {
        netConnect.addPerson(ALICE);
        netConnect.addPerson(BOB);
        netConnect.allowAddIdTuple(new IdTuple(ALICE.getId(), BOB.getId()));
        assertTrue(netConnect.removeRelatedId(new IdTuple(ALICE.getId(), BOB.getId())));
    }

    /**
     * A stub ReadOnlyNetConnect whose persons list can violate interface
     * constraints.
     */
    private static class NetConnectStub implements ReadOnlyNetConnect {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final List<IdTuple> relatedList = new ArrayList<>();

        NetConnectStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public List<IdTuple> getListIdTuple() {
            return relatedList;
        }
    }
}
