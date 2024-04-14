package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAllPersons;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalIds.ID_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.util.IdTuple;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EmployeeBuilder;

public class UnrelateCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void execute_validIdsUnrelated_success() throws CommandException {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);
        IdTuple idTuple = new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON);
        model.addRelatedIdTuple(idTuple);

        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        Model expectedModel = new ModelManager(model.getNetConnect(), model.getUserPrefs());
        expectedModel.removeRelatedIdTuple(idTuple);

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_UNRELATION_SUCCESS, idTuple), expectedModel);
    }

    @Test
    public void execute_invalidFirstPersonId_throwsCommandException() {
        Person secondPerson = new ClientBuilder().withId(ID_SECOND_PERSON.value).build();
        model.addPerson(secondPerson);

        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, ID_FIRST_PERSON.value));
    }

    @Test
    public void execute_invalidSecondPersonId_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withId(ID_FIRST_PERSON.value).build();
        model.addPerson(firstPerson);

        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, ID_SECOND_PERSON.value));
    }

    @Test
    public void execute_samePersonId_throwsCommandException() {
        Person person = new ClientBuilder().withId(ID_FIRST_PERSON.value).build();
        model.addPerson(person);

        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getNetConnect(), model.getUserPrefs());
        showAllPersons(expectedModel);

        assertCommandFailure(command, model, Messages.MESSAGE_CANNOT_UNRELATE_ITSELF, expectedModel);
    }

    @Test
    public void execute_relatedIdTupleNotExists_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new EmployeeBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model, Messages.MESSAGE_RELATION_NOT_EXISTS);
    }

    @Test
    public void equals() {
        UnrelateCommand unrelateFirstSecond = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        UnrelateCommand otherUnrelateFirstSecond = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        UnrelateCommand unrelateSecondFirst = new UnrelateCommand(ID_SECOND_PERSON, ID_FIRST_PERSON);
        UnrelateCommand unrelateSecondThird = new UnrelateCommand(ID_SECOND_PERSON, ID_THIRD_PERSON);

        // same object -> returns true
        assertTrue(unrelateFirstSecond.equals(unrelateFirstSecond));

        // same values -> returns true
        assertTrue(unrelateFirstSecond.equals(otherUnrelateFirstSecond));

        // same values, diff order -> returns true
        assertTrue(unrelateFirstSecond.equals(unrelateSecondFirst));

        // different types -> returns false
        assertFalse(unrelateFirstSecond.equals(1));

        // null -> returns false
        assertFalse(unrelateFirstSecond.equals(null));

        // different values -> returns false
        assertFalse(unrelateFirstSecond.equals(unrelateSecondThird));
    }

    @Test
    public void toStringMethod() {
        UnrelateCommand command = new UnrelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        String expected = UnrelateCommand.class.getCanonicalName()
                + "{first id=" + ID_FIRST_PERSON.value
                + ", second id=" + ID_SECOND_PERSON.value + "}";
        assertEquals(expected, command.toString());
    }
}
