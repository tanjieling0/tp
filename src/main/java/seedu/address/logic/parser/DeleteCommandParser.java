package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID, PREFIX_NAME);

        return createDeleteCommand(argMultimap);
    }

    /**
     * Creates a {@code DeleteCommand} from the values in the given {@code ArgumentMultimap}.
     * @param argMultimap The {@code ArgumentMultimap} containing the users given values
     * @throws ParseException if the user input does not conform to the expected format
     */
    private static DeleteCommand createDeleteCommand(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasId = argMultimap.getValue(PREFIX_ID).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();

        if (hasId && hasName) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } else if (hasId) {
            Id id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());
            return DeleteCommand.byId(id);
        } else if (hasName) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return DeleteCommand.byName(name);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }
}
