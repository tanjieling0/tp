package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.IDContainsDigitsPredicate;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonTuple;
import seedu.address.storage.RelateStorage;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all persons in address book whose ID contains any of the argument IDs.
 */
public class RelateCommand extends Command {

    public static final String COMMAND_WORD = "relate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": relates strictly two existing persons in NetConnect"
            + "using either their unique id, OR, name.\n"
            + "The unique IDs or names provided must exist.\n"
            + "Parameters: [i/ID_1][n/NAME_1] [i/ID_2][n/NAME_2] ...\n"
            + "Example: " + COMMAND_WORD + " 4 12";

    private final IDContainsDigitsPredicate predicate;

    private final Id firstPersonId;
    private final Id secondPersonId;

    public RelateCommand(IDContainsDigitsPredicate predicate) {
        this.predicate = predicate;
        this.firstPersonId = Id.generateTempId(predicate.getFirstId());
        this.secondPersonId = Id.generateId(predicate.getSecondId());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // if ids are valid AND exists, model will display them, otherwise, it will be an empty list
        model.updateFilteredPersonList(predicate);
        // actual execution occurs here
        if (model.hasId(firstPersonId) && model.hasId(secondPersonId)) {
            Person person1 = model.getPersonById(firstPersonId);
            Person person2 = model.getPersonById(secondPersonId);
            PersonTuple tuple = new PersonTuple(person1, person2);
            RelateStorage.addRelatedPersonsTuple(tuple);
        } else {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_ID);
        }

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
