package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

public class FindRoleCommandTest {
    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalNetConnect(), new UserPrefs());

    @Test
    public void equals() {
        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate("client");
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate("supplier");

        FindRoleCommand findRoleFirstCommand = new FindRoleCommand(firstPredicate);
        FindRoleCommand findRoleSecondCommand = new FindRoleCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findRoleFirstCommand, findRoleFirstCommand);

        // same values -> returns true
        FindRoleCommand findRoleFirstCommandCopy = new FindRoleCommand(firstPredicate);
        assertEquals(findRoleFirstCommand, findRoleFirstCommandCopy);

        // different types -> returns false
        assertFalse(findRoleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findRoleFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findRoleFirstCommand.equals(findRoleSecondCommand));
    }

    @Test
    public void execute_zeroRoles_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        RoleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindRoleCommand command = new FindRoleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleRole_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        RoleContainsKeywordsPredicate predicate = preparePredicate("client");
        FindRoleCommand command = new FindRoleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, AMY), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleRoles_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        RoleContainsKeywordsPredicate predicate = preparePredicate("client employee");
        FindRoleCommand command = new FindRoleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE, AMY, BOB), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate("keyword");
        FindRoleCommand findRoleCommand = new FindRoleCommand(predicate);
        String expected = FindRoleCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findRoleCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private RoleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
