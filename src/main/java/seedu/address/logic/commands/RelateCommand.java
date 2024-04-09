package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;
import seedu.address.model.util.IdTuple;

/**
 * Finds and lists all persons in address book whose ID contains any of the argument IDs.
 */
public class RelateCommand extends Command {

    public static final String COMMAND_WORD = "relate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": relates strictly two existing persons in NetConnect "
            + "using their unique id.\n"
            + "Both unique IDs must exist.\n"
            + "Parameters: i/ID_1 i/ID_2\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + "4 " + PREFIX_ID + "12";

    private final IdContainsDigitsPredicate predicate;

    private final Id firstPersonId;
    private final Id secondPersonId;

    /**
     * Creates a RelateCommand to relate the two specified {@code IdContainsDigitsPredicate}
     */
    public RelateCommand(IdContainsDigitsPredicate predicate) {
        this.predicate = predicate;
        this.firstPersonId = Id.generateTempId(predicate.getFirstId());
        this.secondPersonId = Id.generateTempId(predicate.getSecondId());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // if ids are valid AND exists, model will display them, otherwise, it will be an empty list
        model.stackFilters(predicate);
        if (firstPersonId.equals(secondPersonId)) {
            throw new CommandException(Messages.MESSAGE_CANNOT_RELATE_ITSELF);
        }
        // actual execution occurs here
        if (!model.hasId(firstPersonId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, firstPersonId.value));
        } else if (!model.hasId(secondPersonId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, secondPersonId.value));
        }

        IdTuple tuple = new IdTuple(firstPersonId, secondPersonId);

        if (model.hasRelatedIdTuple(tuple)) {
            throw new CommandException(Messages.MESSAGE_RELATION_EXISTS);
        } else {
            model.addRelatedIdTuple(tuple);
        }

        return new CommandResult(String.format(Messages.MESSAGE_RELATE_SUCCESS,
                tuple.getFirstPersonId(), tuple.getSecondPersonId()));
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
