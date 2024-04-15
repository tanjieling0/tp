package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RelateCommand;

public class RelateCommandParserTest {

    private final RelateCommandParser parser = new RelateCommandParser();

    @Test
    public void parse_validArgs_returnsRelateCommand() {
        assertParseSuccess(parser, " i/1 i/2",
                new RelateCommand(ID_FIRST_PERSON, ID_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/1 i/2 i/3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/1 i/2 i/3 i/4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " i/1 i/2 i/3 i/4 i/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/John n/Roy",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelateCommand.MESSAGE_USAGE));
    }
}
