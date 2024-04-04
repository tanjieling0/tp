package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAllPersons;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtId;
import static seedu.address.logic.commands.DeleteCommand.cleanUpAfterTesting;
import static seedu.address.logic.commands.DeleteCommand.setUpForTesting;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NetConnect;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NetConnectPredicate;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());
    @BeforeAll
    public static void setUp() {
        setUpForTesting();
    }

    @AfterAll
    public static void close() {
        cleanUpAfterTesting();
    }

    @Test
    public void execute_validIdUnfilteredList_success() {
        Person personToDelete = model.getPersonById(ID_FIRST_PERSON);
        DeleteCommand deleteCommand = DeleteCommand.byId(ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIdFilteredListPresent_success() {
        showPersonAtId(model, ID_FIRST_PERSON);
        Person personToDelete = model.getPersonById(ID_FIRST_PERSON);
        DeleteCommand deleteCommand = DeleteCommand.byId(ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIdFilteredListAbsent_success() {
        showPersonAtId(model, ID_SECOND_PERSON);
        Person personToDelete = model.getPersonById(ID_FIRST_PERSON);
        DeleteCommand deleteCommand = DeleteCommand.byId(ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showAllPersons(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdUnfilteredList_failure() {
        Id outOfBoundId = Id.generateNextId();
        DeleteCommand deleteCommand = DeleteCommand.byId(outOfBoundId);

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, outOfBoundId.value), expectedModel);
    }

    @Test
    public void execute_invalidIdFilteredList_failure() {
        showPersonAtId(model, ID_FIRST_PERSON);

        Id outOfBoundId = Id.generateNextId();
        DeleteCommand deleteCommand = DeleteCommand.byId(outOfBoundId);

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        showAllPersons(expectedModel);

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, outOfBoundId.value), expectedModel);
    }

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personToDelete = model.getPersonByName(ALICE.getName());
        DeleteCommand deleteCommand = DeleteCommand.byName(ALICE.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameFilteredListPresent_success() {
        showPersonAtId(model, ALICE.getId());

        Person personToDelete = model.getPersonByName(ALICE.getName());
        DeleteCommand deleteCommand = DeleteCommand.byName(ALICE.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameFilteredListAbsent_success() {
        showPersonAtId(model, BENSON.getId());

        Person personToDelete = model.getPersonByName(ALICE.getName());
        DeleteCommand deleteCommand = DeleteCommand.byName(ALICE.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showAllPersons(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_failure() {
        // zero persons matching the name
        Name invalidName = HOON.getName();
        DeleteCommand deleteCommand = DeleteCommand.byName(invalidName);

        ModelManager expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs());

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_NAME, invalidName.fullName),
                expectedModel);

        // more than one persons matching the name
        model.addPerson(new EmployeeBuilder().withName(ALICE.getName().fullName).build());
        deleteCommand = DeleteCommand.byName(ALICE.getName());

        expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs());
        expectedModel.stackFilters(NetConnectPredicate.box(p -> p.getName().equals(ALICE.getName())));

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_DUPLICATE_NAME_USAGE,
                        model.countPersonsWithName(ALICE.getName()), ALICE.getName().fullName),
                expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_failure() {
        // zero persons matching the name
        showPersonAtId(model, ID_FIRST_PERSON);

        Name invalidName = HOON.getName();
        DeleteCommand deleteCommand = DeleteCommand.byName(invalidName);

        Model expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        showAllPersons(expectedModel);

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_NAME, invalidName.fullName),
                expectedModel);

        // more than one persons matching the name
        showPersonAtId(model, ID_FIRST_PERSON);

        model.addPerson(new EmployeeBuilder().withName(ALICE.getName().fullName).build());
        deleteCommand = DeleteCommand.byName(ALICE.getName());

        expectedModel = new ModelManager(model.getNetConnect(), new UserPrefs());
        expectedModel.stackFilters(NetConnectPredicate.box(p -> p.getName().equals(ALICE.getName())));

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_DUPLICATE_NAME_USAGE,
                        model.countPersonsWithName(ALICE.getName()), ALICE.getName().fullName),
                expectedModel);
    }

    @Test
    public void equals() {
        // by id
        DeleteCommand deleteFirstCommand = DeleteCommand.byId(ID_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = DeleteCommand.byId(ID_SECOND_PERSON);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = DeleteCommand.byId(ID_FIRST_PERSON);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);

        // by name
        deleteFirstCommand = DeleteCommand.byName(ALICE.getName());
        deleteSecondCommand = DeleteCommand.byName(BENSON.getName());

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        deleteFirstCommandCopy = DeleteCommand.byName(ALICE.getName());
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toStringMethod() {
        // by id
        Id targetId = ID_FIRST_PERSON;
        DeleteCommand deleteCommand = DeleteCommand.byId(targetId);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{targetId=" + targetId
                + ", targetName=null}";
        assertEquals(expected, deleteCommand.toString());

        // by name
        Name targetName = ALICE.getName();
        deleteCommand = DeleteCommand.byName(targetName);
        expected = DeleteCommand.class.getCanonicalName()
                + "{targetId=null, targetName=" + targetName + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.stackFilters(NetConnectPredicate.box(p -> false));

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
