package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.RelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdContainsDigitsPredicate;

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

        // Check if all IDs in input are valid
        for (String idInList : providedIds) {
            ParserUtil.parseId(idInList);
        }

        Integer[] providedIdsAsInt = Arrays.stream(providedIds).map(Integer::parseInt).toArray(Integer[]::new);

        return new RelateCommand(new IdContainsDigitsPredicate(Arrays.asList(providedIdsAsInt)));
    }
}
