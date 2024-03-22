package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Client;
import seedu.address.model.person.Employee;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Supplier;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person with the specified id. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: "
            + PREFIX_ID + "ID "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private final Id id;
    private final Remark remark;

    /**
     * @param id of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public RemarkCommand(Id id, Remark remark) {
        requireAllNonNull(id, remark);

        this.id = id;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasId(id)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, id.value));
        }

        Person personToEdit = model.getPersonById(id);
        Person editedPerson;
        if (personToEdit instanceof Client) {
            editedPerson = new Client(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getTags(), (
                    (Client) personToEdit).getProducts(), ((Client) personToEdit).getPreferences());
        } else if (personToEdit instanceof Supplier) {
            editedPerson = new Supplier(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getTags(), (
                    (Supplier) personToEdit).getProducts(), ((Supplier) personToEdit).getTermsOfService());
        } else if (personToEdit instanceof Employee) {
            editedPerson = new Employee(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getTags(), (
                    (Employee) personToEdit).getDepartment(), ((Employee) personToEdit).getJobTitle(), (
                    (Employee) personToEdit).getSkills());
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, id.value));
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand e = (RemarkCommand) other;
        return id.equals(e.id)
                && remark.equals(e.remark);
    }
}
