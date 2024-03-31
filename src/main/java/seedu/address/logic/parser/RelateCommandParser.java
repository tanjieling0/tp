package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.RelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;

/**
 * Parses input arguments and creates a new RelateCommand object
 */
public class RelateCommandParser implements Parser<RelateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RelateCommand
     * and returns a RelateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RelateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        }

        String[] providedIds = trimmedArgs.split("\\s+");

        if (providedIds.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        }

        String[] pureIds = new String[2];

        for (int i = 0; i < providedIds.length; i++) {
            String[] flagAndId = providedIds[i].split("/");
            // find and validate flag
            if (flagAndId[0].equals("i")) {
                ParserUtil.parseId(flagAndId[1]);
                pureIds[i] = flagAndId[1];
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
            }
        }

        Integer[] providedIdsAsInt = Arrays.stream(pureIds).map(Integer::parseInt).toArray(Integer[]::new);

        return new RelateCommand(new IdContainsDigitsPredicate(Arrays.asList(providedIdsAsInt)));
    }
}
