package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Id;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validId_returnsDeleteCommand() {
        assertParseSuccess(parser, " i/1", DeleteCommand.byId(ID_FIRST_PERSON));
    }

    @Test
    public void parse_validName_returnsDeleteCommand() {
        assertParseSuccess(parser, " n/" + ALICE.getName().fullName, DeleteCommand.byName(ALICE.getName()));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        assertParseFailure(parser, " i/1 n/John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        // zero id
        assertParseFailure(parser, " i/0", Id.MESSAGE_CONSTRAINTS);

        // negative id
        assertParseFailure(parser, " i/-5", Id.MESSAGE_CONSTRAINTS);

        // non numerical id
        assertParseFailure(parser, " i/one", Id.MESSAGE_CONSTRAINTS);

        // empty id
        assertParseFailure(parser, " i/", Id.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // zero id
        assertParseFailure(parser, " i/1 i/2", String.format(getErrorMessageForDuplicatePrefixes(PREFIX_ID)));
    }

    @Test
    public void parse_invalidId_throwsParseException() {
        // zero id
        assertParseFailure(parser, " i/0", Id.MESSAGE_CONSTRAINTS);

        // negative id
        assertParseFailure(parser, " i/-5", Id.MESSAGE_CONSTRAINTS);

        // non numerical id
        assertParseFailure(parser, " i/one", Id.MESSAGE_CONSTRAINTS);

        // empty id
        assertParseFailure(parser, " i/", Id.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // zero id
        assertParseFailure(parser, " i/1 i/2", String.format(getErrorMessageForDuplicatePrefixes(PREFIX_ID)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
