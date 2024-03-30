package seedu.address.logic.parser;

import seedu.address.logic.commands.FindNumCommand;
import seedu.address.logic.commands.RelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhoneContainsDigitsPredicate;

import java.util.Arrays;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindNumCommand object
 */
public class RelateCommandParser implements Parser<RelateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindNumCommand
     * and returns a FindNumCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RelateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        }

        String[] providedIDs = trimmedArgs.split("\\s+");

        // Check if all phone numbers in input are valid
        for (String ID : providedIDs) {
            if (!ID.isValidPhone(phoneKeyword)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNumCommand.MESSAGE_USAGE));
            }
        }

        return new RelateCommand(new PhoneContainsDigitsPredicate(Arrays.asList(phoneKeywords)));
    }

}
