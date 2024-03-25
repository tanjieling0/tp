package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindRemCommand;
import seedu.address.model.person.RemarkContainsKeywordsPredicate;

public class FindRemCommandParserTest {
    private final FindRemCommandParser parser = new FindRemCommandParser();

    @Test
    public void parseEmptyArgs_throwsParseException() {
        assertParseFailure(
                parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRemCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgs_returnsFindRemCommand() {
        // no leading and trailing whitespaces
        FindRemCommand expectedFindRemCommand =
                new FindRemCommand(new RemarkContainsKeywordsPredicate(Arrays.asList("first", "second")));
        assertParseSuccess(parser, "first second", expectedFindRemCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n first \n \t second  \t", expectedFindRemCommand);
    }

    @Test
    public void parseInvalidArgs_throwsParseException() {
        // empty keyword
        assertParseFailure(
                parser,
                " \n \t \n",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRemCommand.MESSAGE_USAGE));
    }


}
