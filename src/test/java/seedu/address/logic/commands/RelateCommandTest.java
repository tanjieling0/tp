package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAllPersons;
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
        assertThrows(NullPointerException.class, () -> new RelateCommand(null, ID_SECOND_PERSON));
        assertThrows(NullPointerException.class, () -> new RelateCommand(ID_FIRST_PERSON, null));
        assertThrows(NullPointerException.class, () -> new RelateCommand(null, null));
    }

    @Test
    public void execute_validIds_success() throws CommandException {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);

        RelateCommand command = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        Model expectedModel = new ModelManager(model.getNetConnect(), model.getUserPrefs());
        expectedModel.clearFilter();
        expectedModel.stackFilters(
                new IdContainsDigitsPredicate(List.of(ID_FIRST_PERSON.value, ID_SECOND_PERSON.value)));
        expectedModel.addRelatedIdTuple(new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON));

        assertCommandSuccess(command, model,
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2), expectedModel);
    }

    @Test
    public void execute_invalidFirstPersonId_throwsCommandException() {
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(secondPerson);

        RelateCommand command = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, ID_FIRST_PERSON.value));
    }

    @Test
    public void execute_invalidSecondPersonId_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        model.addPerson(firstPerson);

        RelateCommand command = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, ID_SECOND_PERSON.value));
    }

    @Test
    public void execute_samePersonId_throwsCommandException() {
        Person person = new ClientBuilder().withId(ID_FIRST_PERSON.value).build();
        model.addPerson(person);

        RelateCommand command = new RelateCommand(ID_FIRST_PERSON, ID_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getNetConnect(), model.getUserPrefs());
        showAllPersons(expectedModel);

        assertCommandFailure(command, model, Messages.MESSAGE_CANNOT_RELATE_ITSELF, expectedModel);
    }

    @Test
    public void execute_existingRelatedIdTuple_throwsCommandException() {
        Person firstPerson = new ClientBuilder().withName("one").withId(ID_FIRST_PERSON.value).build();
        Person secondPerson = new ClientBuilder().withName("two").withId(ID_SECOND_PERSON.value).build();
        model.addPerson(firstPerson);
        model.addPerson(secondPerson);
        model.addRelatedIdTuple(new IdTuple(ID_FIRST_PERSON, ID_SECOND_PERSON));

        RelateCommand command = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);

        assertCommandFailure(command, model, Messages.MESSAGE_RELATION_EXISTS);
    }

    @Test
    public void equals() {
        RelateCommand relateFirstSecond = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        RelateCommand otherRelateFirstSecond = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        RelateCommand relateSecondFirst = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        RelateCommand relateSecondThird = new RelateCommand(ID_SECOND_PERSON, ID_THIRD_PERSON);
        AddCommand addCommand = new AddCommand(new ClientBuilder().build());

        // same object -> returns true
        assertTrue(relateFirstSecond.equals(relateFirstSecond));

        // same values -> returns true
        assertTrue(relateFirstSecond.equals(otherRelateFirstSecond));

        // same values, diff order -> returns true
        assertTrue(relateFirstSecond.equals(relateSecondFirst));

        // different types -> returns false
        assertFalse(relateFirstSecond.equals(1));

        // null -> returns false
        assertFalse(relateFirstSecond.equals(null));

        // different values -> returns false
        assertFalse(relateFirstSecond.equals(relateSecondThird));

        // different command -> returns false
        assertFalse(relateFirstSecond.equals(addCommand));
    }

    @Test
    public void toStringMethod() {
        RelateCommand relateCommand = new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON);
        String expected = RelateCommand.class.getCanonicalName()
                + "{first id=" + ID_FIRST_PERSON.value
                + ", second id=" + ID_SECOND_PERSON.value + "}";
        assertEquals(expected, relateCommand.toString());
    }
}
