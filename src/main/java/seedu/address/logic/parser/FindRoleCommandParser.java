package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.person.Person.isValidRole;

import java.util.Arrays;

import seedu.address.logic.commands.FindRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRoleCommand object
 */
public class FindRoleCommandParser implements Parser<FindRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindRoleCommand
     * and returns a FindRoleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRoleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRoleCommand.MESSAGE_USAGE));
        }

        String[] roleKeywords = trimmedArgs.split("\\s+");
        // Check if all roleKeywords in input are valid
        for (String roleKeyword : roleKeywords) {
            if (!isValidRole(roleKeyword)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRoleCommand.MESSAGE_USAGE));
            }
        }

        return new FindRoleCommand(new RoleContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
    }

}
