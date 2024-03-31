package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.IdContainsDigitsPredicate;
import seedu.address.model.util.RelatedList;

/**
 * Finds and lists all persons in address book related to the person with the specified id.
 */
public class ShowRelatedCommand extends Command {

    public static final String COMMAND_WORD = "showrelated";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons related to person with the specified id.\n"
            + "Parameters: i/ID\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + "/1";

    private final Id id;

    public ShowRelatedCommand(Id id) {
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        RelatedList relatedList = model.getRelatedIdTuples();

        List<Integer> relatedIds = relatedList.getAllRelatedIds(relatedList, id);
        IdContainsDigitsPredicate predicate = new IdContainsDigitsPredicate(relatedIds);

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
