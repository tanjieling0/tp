package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.logic.commands.ShowRelatedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Id;

/**
 * Parses input arguments and creates a new ShowRelatedCommand object
 */
public class ShowRelatedCommandParser implements Parser<ShowRelatedCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShowRelatedCommand
     * and returns a ShowRelatedCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowRelatedCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID);

        if (argMultimap.getValue(PREFIX_ID).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowRelatedCommand.MESSAGE_USAGE));
        }

        try {
            Id id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());
            return new ShowRelatedCommand(id);
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ShowRelatedCommand.MESSAGE_USAGE), pe);
        }
    }
}

