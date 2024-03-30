package seedu.address.logic.parser;

import seedu.address.logic.commands.RelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IDContainsDigitsPredicate;

import java.util.Arrays;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.person.Id.isValidId;

/**
 * Parses input arguments and creates a new FindNumCommand object
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

        String[] providedIDs = trimmedArgs.split("\\s+");

        // Check if all IDs in input are valid
        for (String iDInList : providedIDs) {
            if (!isValidId(Integer.parseInt(iDInList))) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
            }
        }

        /*
         A decision is made here to convert the String[] into an Integer[] since an array of IDs should not contain
         Strings, and this assumption is heavily relied upon in the following IDContainsDigitsPredicate class.
         This is a potential bug as the provided IDs is not checked for existence here before being converted to an
         Integer array. It also should not present as an efficiency issue due to small size of the array.
        */
        Integer[] providedIDsAsInt = Arrays.stream(providedIDs).map(Integer::parseInt).toArray(Integer[]::new);

        return new RelateCommand(new IDContainsDigitsPredicate(Arrays.asList(providedIDsAsInt)));
    }

}
