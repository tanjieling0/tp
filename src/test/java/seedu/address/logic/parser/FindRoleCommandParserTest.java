package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindRoleCommand;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

public class FindRoleCommandParserTest {
    private final FindRoleCommandParser findRoleCommandParser = new FindRoleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(findRoleCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRole_throwsParseException() {
        assertParseFailure(findRoleCommandParser, "cliont",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRoleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRole_returnsFindRoleCommand() {
        // no leading and trailing whitespaces
        FindRoleCommand expectedFindRoleCommand =
                new FindRoleCommand(new RoleContainsKeywordsPredicate(Arrays.asList("client", "supplier")));
        assertParseSuccess(findRoleCommandParser, "client supplier", expectedFindRoleCommand);
        assertParseSuccess(findRoleCommandParser, " \n client \n \t supplier \t", expectedFindRoleCommand);
    }
}
