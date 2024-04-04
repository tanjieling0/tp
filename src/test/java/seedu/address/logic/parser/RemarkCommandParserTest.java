package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Id;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validId_returnsRemarkCommand() {
        assertParseSuccess(parser, " i/1 r/remark",
                new RemarkCommand(ID_FIRST_PERSON, new Remark("remark")));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        assertParseFailure(parser, " i/0 r/remark",
                String.format(Id.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_repeatedFields_throwsParseException() {
        // repeated id
        assertParseFailure(parser, " i/1 i/2 r/remark",
                String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID)));

        // repeated remark
        assertParseFailure(parser, " i/1 r/remark r/remark",
                String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK)));

        // repeated id and remark
        assertParseFailure(parser, " i/1 i/2 r/remark r/another remark",
                String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID, PREFIX_REMARK)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
