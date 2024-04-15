package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnrelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Id;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
        }

        String[] providedIds = trimmedArgs.split("\\s+");

        if (providedIds.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
        }

        Id[] ids = new Id[2];

        for (int i = 0; i < providedIds.length; i++) {
            String[] flagAndId = providedIds[i].split("/");
            // find and validate flag
            if (flagAndId[0].equals("i")) {
                ids[i] = ParserUtil.parseId(flagAndId[1]);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
            }
        }

        return new UnrelateCommand(ids[0], ids[1]);
    }
}
