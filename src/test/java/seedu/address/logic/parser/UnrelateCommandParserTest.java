package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnrelateCommand;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;

public class UnrelateCommandParserTest {

    private final UnrelateCommandParser parser = new UnrelateCommandParser();

    @Test
    public void parse_validArgs_returnsUnrelateCommand() {
        assertParseSuccess(parser, "i/1 i/2",
                new UnrelateCommand(new IdContainsDigitsPredicate(Arrays.asList(1, 2))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnrelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnrelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/1 i/2 i/3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/1 i/2 i/3 i/4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "i/1 i/2 i/3 i/4 i/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnrelateCommand.MESSAGE_USAGE));
    }
}
