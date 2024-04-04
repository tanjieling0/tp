package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;
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
        model.addRelatedIdTuple(new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON));

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        UnrelateCommand command = new UnrelateCommand(predicate);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_UNRELATION_SUCCESS, new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON)),
                commandResult.getFeedbackToUser());
        assertFalse(model.hasRelatedIdTuple(new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON)));
    }

    @Test
    public void execute_invalidFirstPersonId_throwsCommandException() {
        Person secondPerson = new ClientBuilder().withId(ID_SECOND_PERSON.value).build();
        model.addPerson(secondPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        UnrelateCommand command = new UnrelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidSecondPersonId_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withId(ID_FIRST_PERSON.value).build();
        model.addPerson(firstPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        UnrelateCommand command = new UnrelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_samePersonId_throwsCommandException() {
        Person person = new ClientBuilder().withId(ID_FIRST_PERSON.value).build();
        model.addPerson(person);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        UnrelateCommand command = new UnrelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_relatedIdTupleNotExists_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new EmployeeBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        UnrelateCommand command = new UnrelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        IdContainsDigitsPredicate predicate1 = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        IdContainsDigitsPredicate predicate2 = new IdContainsDigitsPredicate(List.of(ID_SECOND_PERSON.value,
                ID_FIRST_PERSON.value));

        UnrelateCommand command1 = new UnrelateCommand(predicate1);
        UnrelateCommand command2 = new UnrelateCommand(predicate1);
        UnrelateCommand command3 = new UnrelateCommand(predicate2);

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command2);

        // different types -> returns false
        assertNotEquals(5, command1);

        // null -> returns false
        assertNotEquals(null, command1);

        // different predicate -> returns false
        assertNotEquals(command1, command3);
    }
}
