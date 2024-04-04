package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShowRelatedCommandTest {
    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());

    @Test
    public void execute_showRelatedUnfilteredList_success() throws CommandException {
        CommandResult commandResult = new ShowRelatedCommand(ID_FIRST_PERSON).execute(model);
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_showRelatedFilteredList_success() throws CommandException {
        model.getFilteredPersonList();
        CommandResult commandResult = new ShowRelatedCommand(ID_FIRST_PERSON).execute(model);
        assertEquals(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ShowRelatedCommand showRelatedFirstCommand = new ShowRelatedCommand(ID_FIRST_PERSON);
        ShowRelatedCommand showRelatedSecondCommand = new ShowRelatedCommand(ID_SECOND_PERSON);

        // same object -> returns true
        assertEquals(showRelatedFirstCommand, showRelatedFirstCommand);

        // same values -> returns true
        ShowRelatedCommand showRelatedFirstCommandCopy = new ShowRelatedCommand(ID_FIRST_PERSON);
        assertEquals(showRelatedFirstCommand, showRelatedFirstCommandCopy);

        // different types -> returns false
        assertFalse(showRelatedFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showRelatedFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(showRelatedFirstCommand.equals(showRelatedSecondCommand));
    }

    @Test
    public void toStringMethod() {
        ShowRelatedCommand showRelatedCommand = new ShowRelatedCommand(ID_FIRST_PERSON);
        String expectedString = ShowRelatedCommand.class.getCanonicalName() + "{id=" + ID_FIRST_PERSON + "}";
        assertEquals(expectedString, showRelatedCommand.toString());
    }

}
