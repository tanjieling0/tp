package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.ui.MainWindow.handleDestructiveCommands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with the specified ID.\n"
            + "Parameters: "
            + PREFIX_ID + "ID\n"
            + "Example: " + COMMAND_WORD + " i/1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_CANCELLED = "Delete cancelled";
    private static boolean doNotSkipConfirmation = true;
    private final Id targetId;

    public DeleteCommand(Id targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check if the ID is valid
        if (!model.hasId(targetId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, targetId.value));
        }

        if (doNotSkipConfirmation) {
            // obtain confirmation from
            boolean isConfirmed = handleDestructiveCommands(true, false);
            if (!isConfirmed) {
                return new CommandResult(MESSAGE_DELETE_CANCELLED, false, false);
            }
        }


        Person personToDelete = model.getPersonById(targetId);
        boolean showList = !model.getFilteredPersonList().contains(personToDelete);
        model.deletePerson(personToDelete);
        if (showList) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetId.equals(otherDeleteCommand.targetId);
    }

    public static void setUpForTesting() {
        doNotSkipConfirmation = false;
    }

    public static void cleanUpAfterTesting() {
        doNotSkipConfirmation = true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
