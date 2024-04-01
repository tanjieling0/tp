package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.filter.RoleContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose role matches the argument roles.
 */
public class FindRoleCommand extends Command {

    public static final String COMMAND_WORD = "findrole";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose role matches the specified "
            + "role and displays them as a list with index numbers.\n"
            + "A role must be either CLIENT, SUPPLIER or EMPLOYEE. \n"
            + "Parameters: ROLE [ROLES]...\n"
            + "Example: " + COMMAND_WORD + " CLIENT SUPPLIER";

    private final RoleContainsKeywordsPredicate predicate;

    public FindRoleCommand(RoleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearFilter();
        model.stackFilters(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindRoleCommand)) {
            return false;
        }

        FindRoleCommand otherFindRoleCommand = (FindRoleCommand) other;
        return predicate.equals(otherFindRoleCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
