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

        System.out.println(trimmedArgs);


        String[] providedIds = trimmedArgs.split("i/");

        //remove leading and trailing whitespaces in indexes
        for (int i = 0; i < providedIds.length; i++) {
            providedIds[i] = providedIds[i].trim();
        }

        if (providedIds.length != 3 || !providedIds[0].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        }

        String[] pureIds = new String[2];

        for (int i = 1; i < providedIds.length; i++) {
            ParserUtil.parseId(providedIds[i]);
            pureIds[i - 1] = providedIds[i];
        }

        Integer[] providedIdsAsInt = Arrays.stream(pureIds).map(Integer::parseInt).toArray(Integer[]::new);

        return new RelateCommand(new IdContainsDigitsPredicate(Arrays.asList(providedIdsAsInt)));
    }
}
