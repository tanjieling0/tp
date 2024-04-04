package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.filter.NameContainsKeywordsPredicate;
import seedu.address.model.person.filter.PhoneMatchesDigitsPredicate;
import seedu.address.model.person.filter.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.filter.RoleMatchesKeywordsPredicate;
import seedu.address.model.person.filter.TagsContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNames_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhones_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PhoneMatchesDigitsPredicate(Arrays.asList("123", "91234567")));
        assertParseSuccess(parser, " p/123 p/91234567", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n p/123 \n \t p/91234567  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTags_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new TagsContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, " t/friends t/colleagues", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/friends \n   \t t/colleagues  \t", expectedFindCommand);
    }

    @Test
    public void parse_validRoles_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new RoleMatchesKeywordsPredicate(
                Arrays.asList("client", "employee", "supplier")));
        assertParseSuccess(parser, " role/client role/employee role/supplier", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n role/client \n   \t role/employee  \t \n role/supplier", expectedFindCommand);
    }

    @Test
    public void parse_validRemarks_returnsFindCommand() {
        // empty remark
        FindCommand expectedFindCommand = new FindCommand(
                new RemarkContainsKeywordsPredicate(Collections.singletonList("")));
        assertParseSuccess(parser, " r/", expectedFindCommand);
        assertParseSuccess(parser, " r/ \t \n", expectedFindCommand);

        // non-empty + empty remarks
        expectedFindCommand = new FindCommand(
                new RemarkContainsKeywordsPredicate(Arrays.asList("", "first")));
        assertParseSuccess(parser, " r/ r/first", expectedFindCommand);
        assertParseSuccess(parser, " r/first \n   \t   r/ \t \n", expectedFindCommand);

        // no leading and trailing whitespaces
        expectedFindCommand = new FindCommand(new RemarkContainsKeywordsPredicate(
                Arrays.asList("first", "second")));
        assertParseSuccess(parser, " r/first r/second", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " \n r/first \n   \t r/second  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidNames_throwsParseException() {
        // empty name
        assertParseFailure(parser, " n/", String.format(Name.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " n/ n/ n/", String.format(Name.MESSAGE_CONSTRAINTS));

        // invalid name format
        assertParseFailure(parser, " n/@lice", String.format(Name.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " n/Ali@ce n/Bob", String.format(Name.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " n/Bob n/Ali@ce", String.format(Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidPhones_throwsParseException() {
        // empty phones
        assertParseFailure(parser, " p/", String.format(Phone.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " p/ p/ p/", String.format(Phone.MESSAGE_CONSTRAINTS));

        // invalid phones format
        assertParseFailure(parser, " p/12", String.format(Phone.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " p/123a45", String.format(Phone.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " p/1234a56 p/91234567", String.format(Phone.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " p/91234567 p/12", String.format(Phone.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        // empty tag
        assertParseFailure(parser, " t/", String.format(Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " t/ t/ t/", String.format(Tag.MESSAGE_CONSTRAINTS));

        // invalid tag format
        assertParseFailure(parser, " t/fri@ends", String.format(Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " t/fri@ends t/colleagues", String.format(Tag.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, " t/colleagues t/fri@ends", String.format(Tag.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidRoles_throwsParseException() {
        // empty role
        assertParseFailure(parser, " role/", String.format(Person.MESSAGE_ROLE_CONSTRAINTS));
        assertParseFailure(parser, " role/ role/ role/", String.format(Person.MESSAGE_ROLE_CONSTRAINTS));

        // invalid role format
        assertParseFailure(parser, " role/clie",
                String.format(Person.MESSAGE_ROLE_CONSTRAINTS));
        assertParseFailure(parser, " role/clie role/employees",
                String.format(Person.MESSAGE_ROLE_CONSTRAINTS));
        assertParseFailure(parser, " role/client role/employ",
                String.format(Person.MESSAGE_ROLE_CONSTRAINTS));
    }

    @Test
    public void parse_multipleFields_throwsParseException() {
        // with possible invalid inputs
        assertParseFailure(parser, " n/ t/", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/ t/ role/", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Alice t/friends", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Ali@ce t/friends", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Alice t/fri@ends", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " role/clie t/friends", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Alice t/friends role/clie",
                String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));

        // multiple repeated fields with possible invalid inputs
        assertParseFailure(parser, " n/ n/ t/", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/ t/ t/", String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Alice n/Bob t/friends",
                String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " n/Ali@ce t/friends t/colleagues",
                String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
        assertParseFailure(parser, " role/clie role/employ t/friends t/colleagues",
                String.format(Messages.MESSAGE_NON_UNIQUE_FIELDS));
    }
}
