package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindRemCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RemarkContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRemCommand object
 */
public class FindRemCommandParser implements Parser<FindRemCommand> {

    /**
    * Parses the given {@code String} of arguments in the context of the FindRemCommand
    * and returns a FindRemCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public FindRemCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRemCommand.MESSAGE_USAGE));
        }

        String[] remarkKeywords = trimmedArgs.split("\\s+");

        // Check if all keywords in input are valid
        for (String keyword : remarkKeywords) {
            assert !keyword.isEmpty();
            assert keyword.matches("^[a-zA-Z0-9\\p{Punct}]*$");
        }

        return new FindRemCommand(new RemarkContainsKeywordsPredicate(Arrays.asList(remarkKeywords)));
    }

}
