package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.ui.MainWindow.handleDestructiveCommands;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NetConnectPredicate;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with the specified id or name\n"
            + "Either only id or name must be provided\n"
            + "Name has to be an exact match, but is case-insensitive\n"
            + "Parameters: "
            + PREFIX_ID + "[ID] "
            + PREFIX_NAME + "[NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + "or " + COMMAND_WORD + " n/John";
    public static final String MESSAGE_DUPLICATE_NAME_USAGE = "%1d %2$s found!\n"
            + "Use id to delete instead\n"
            + COMMAND_WORD + ": Deletes the person with the specified id\n"
            + "Parameters: "
            + PREFIX_ID + "[ID]\n"
            + "Example: " + COMMAND_WORD + " i/1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_CANCELLED = "Delete cancelled";
    private static boolean doNotSkipConfirmation = true;
    private final Id targetId;
    private final Name targetName;

    private DeleteCommand(Id targetId, Name name) {
        this.targetId = targetId;
        this.targetName = name;
    }

    /**
     * Factory method to create a {@code DeleteCommand} that deletes a Person
     * by the given id.
     *
     * @param id The id of the {@code Person} to be deleted
     * @return {@code DeleteCommand} to delete by id
     */
    public static DeleteCommand byId(Id id) {
        requireNonNull(id);
        return new DeleteCommand(id, null);
    }

    /**
     * Factory method to create a {@code DeleteCommand} that deletes a Person
     * by the given name.
     *
     * @param name The name of the {@code Person} to be deleted
     * @return {@code DeleteCommand} to delete by name
     */
    public static DeleteCommand byName(Name name) {
        requireNonNull(name);
        return new DeleteCommand(null, name);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert targetId != null ^ targetName != null;

        if (doNotSkipConfirmation) {
            boolean isConfirmed = handleDestructiveCommands(true, false);
            if (!isConfirmed) {
                return new CommandResult(MESSAGE_DELETE_CANCELLED, false, false);
            }
        }

        Person personToDelete = getPersonToDelete(model);

        boolean showList = !model.getFilteredPersonList().contains(personToDelete);
        model.deletePerson(personToDelete);
        if (showList) {
            model.clearFilter();
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private Person getPersonToDelete(Model model) throws CommandException {
        assert targetId != null ^ targetName != null;

        if (targetId != null) {
            if (!model.hasId(targetId)) {
                model.clearFilter();
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, targetId.value));
            }
            return model.getPersonById(targetId);
        } else {
            int count = model.countPersonsWithName(targetName);
            if (count < 1) {
                model.clearFilter();
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_NAME, targetName.fullName));
            }
            if (count > 1) {
                model.stackFilters(NetConnectPredicate.box(
                        p -> p.getName().fullName.equalsIgnoreCase(targetName.fullName)));
                throw new CommandException(String.format(MESSAGE_DUPLICATE_NAME_USAGE, count, targetName.fullName));
            }
            return model.getPersonByName(targetName);
        }
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
        return Objects.equals(targetId, otherDeleteCommand.targetId)
                && Objects.equals(targetName, otherDeleteCommand.targetName);
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
                .add("targetName", targetName)
                .toString();
    }
}
