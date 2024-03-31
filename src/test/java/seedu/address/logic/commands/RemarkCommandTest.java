package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAllPersons;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtId;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NetConnect;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.util.RelatedList;
import seedu.address.testutil.ClientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs(), new RelatedList());
    private static final String REMARK_STUB = "Some remark";

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getPersonById(ID_FIRST_PERSON);
        Person editedPerson = new ClientBuilder((Client) firstPerson).withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person firstPerson = model.getPersonById(ID_FIRST_PERSON);
        Person editedPerson = new ClientBuilder((Client) firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListIdPresent_success() {
        showPersonAtId(model, ID_FIRST_PERSON);

        Person firstPerson = model.getPersonById(ID_FIRST_PERSON);
        Person editedPerson = new ClientBuilder((Client) firstPerson).withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListIdAbsent_success() {
        showPersonAtId(model, ID_SECOND_PERSON);

        Person firstPerson = model.getPersonById(ID_FIRST_PERSON);
        Person editedPerson = new ClientBuilder((Client) firstPerson).withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        expectedModel.setPerson(firstPerson, editedPerson);
        showAllPersons(expectedModel);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdUnfilteredList_failure() {
        Id outOfBoundId = Id.generateNextId();
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundId, new Remark(VALID_REMARK_AMY));

        assertCommandFailure(remarkCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, outOfBoundId.value));
    }

    @Test
    public void execute_invalidIdFilteredList_failure() {
        showPersonAtId(model, ID_FIRST_PERSON);

        Id outOfBoundId = Id.generateNextId();
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundId, new Remark(VALID_REMARK_AMY));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        showAllPersons(expectedModel);

        assertCommandFailure(remarkCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_ID, outOfBoundId.value), expectedModel);
    }

    @Test
    public void equals() {
        final RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same object -> returns true
        assertTrue(remarkCommand.equals(remarkCommand));

        // same values -> returns true
        assertTrue(remarkCommand.equals(new RemarkCommand(ID_FIRST_PERSON, new Remark(VALID_REMARK_AMY))));

        // null -> returns false
        assertFalse(remarkCommand.equals(null));

        // different types -> returns false
        assertFalse(remarkCommand.equals(new ClearCommand()));

        // different index, same remark -> returns false
        assertFalse(remarkCommand.equals(new RemarkCommand(ID_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different remark, same index -> returns false
        assertFalse(remarkCommand.equals(new RemarkCommand(ID_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));

        // different remark and index -> returns false
        assertFalse(remarkCommand.equals(new RemarkCommand(ID_SECOND_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    @Test
    public void execute_editClientRemarkUnfilteredList_success() {
        Person firstPerson = model.getPersonById(ID_FIRST_PERSON);
        Person editedPerson = new ClientBuilder((Client) firstPerson).withRemark(VALID_REMARK_AMY).build();

        RemarkCommand remarkCommand = new RemarkCommand(ID_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new NetConnect(model.getNetConnect()),
                new UserPrefs(),
                new RelatedList());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

}
