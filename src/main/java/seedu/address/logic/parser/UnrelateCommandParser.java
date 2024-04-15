package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.List;

import seedu.address.logic.commands.UnrelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RelateCommand object
 */
public class UnrelateCommandParser implements Parser<UnrelateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RelateCommand
     * and returns a RelateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnrelateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);
        List<String> ids = argMultimap.getAllValues(PREFIX_ID);
        if (ids.size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
        }

        return new UnrelateCommand(ParserUtil.parseId(ids.get(0)), ParserUtil.parseId(ids.get(1)));
    }
}
