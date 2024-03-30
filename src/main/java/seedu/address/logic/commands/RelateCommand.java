package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneContainsDigitsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all persons in address book whose phone number contains any of the argument phone numbers.
 */
public class RelateCommand extends Command {

    public static final String COMMAND_WORD = "relate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": relates strictly two existing persons in NetConnect"
            + "using either their unique id, OR, name.\n"
            + "The unique IDs or names provided must exist.\n"
            + "Parameters: [i/ID_1][n/NAME_1] [i/ID_2][n/NAME_2] ...\n"
            + "Example: " + COMMAND_WORD + " 4 12";

    private final PhoneContainsDigitsPredicate predicate;

    public RelateCommand(PhoneContainsDigitsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RelateCommand)) {
            return false;
        }

        RelateCommand otherFindNumCommand = (RelateCommand) other;
        return predicate.equals(otherFindNumCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
