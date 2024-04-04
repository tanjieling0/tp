package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowRelatedCommand;

public class ShowRelatedCommandParserTest {
    private final ShowRelatedCommandParser parser = new ShowRelatedCommandParser();

    @Test
    public void parse_validArgs_returnsShowRelatedCommand() {
        // no leading and trailing whitespaces
        assertParseSuccess(parser, " i/1", new ShowRelatedCommand(ID_FIRST_PERSON));

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " i/ \n 1 \n \t", new ShowRelatedCommand(ID_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no index
        assertParseFailure(
                parser, "i/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowRelatedCommand.MESSAGE_USAGE));

        // no index and prefix
        assertParseFailure(
                parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowRelatedCommand.MESSAGE_USAGE));

        // non integer
        assertParseFailure(
                parser, "i/one", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowRelatedCommand.MESSAGE_USAGE));
    }
}
