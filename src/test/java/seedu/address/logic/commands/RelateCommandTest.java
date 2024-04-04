package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalIds.ID_THIRD_PERSON;

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

public class RelateCommandTest {

    private final Model model = new ModelManager();

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RelateCommand(null));
    }

    @Test
    public void execute_validIds_success() throws CommandException {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        RelateCommand command = new RelateCommand(predicate);
        CommandResult commandResult = command.execute(model);

        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasRelatedIdTuple(new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON)));
    }

    @Test
    public void execute_invalidFirstPersonId_throwsCommandException() {
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(secondPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        RelateCommand command = new RelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidSecondPersonId_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        model.addPerson(firstPerson);

        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        RelateCommand command = new RelateCommand(predicate);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        IdContainsDigitsPredicate predicate1 = new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value,
                ID_SECOND_PERSON.value));
        IdContainsDigitsPredicate predicate2 = new IdContainsDigitsPredicate(List.of(ID_SECOND_PERSON.value,
                ID_THIRD_PERSON.value));
        RelateCommand command1 = new RelateCommand(predicate1);
        RelateCommand command2 = new RelateCommand(predicate1);
        RelateCommand command3 = new RelateCommand(predicate2);

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        assertEquals(command1, command2);

        // different types -> returns false
        assertNotEquals(1, command1);

        // null -> returns false
        assertNotEquals(null, command1);

        // different predicate -> returns false
        assertNotEquals(command1, command3);
    }
}
