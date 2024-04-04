package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.filter.NameContainsKeywordsPredicate;
import seedu.address.model.person.filter.NetConnectPredicate;
import seedu.address.model.person.filter.PhoneMatchesDigitsPredicate;
import seedu.address.model.person.filter.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.filter.RoleMatchesKeywordsPredicate;
import seedu.address.model.person.filter.TagsContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG, PREFIX_ROLE, PREFIX_REMARK);
        argMultimap.verifyOnlyOnePrefix();

        return new FindCommand(createPredicate(argMultimap));
    }

    /**
     * Creates the matching NetConnectPredicate based on the provided values in
     * the given {@code argMultimap}.
     *
     * @throws ParseException if the given values are not valid
     */
    private static NetConnectPredicate<Person> createPredicate(
            ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> names = argMultimap.getAllValues(PREFIX_NAME);
            if (!names.stream().allMatch(Name::isValidName)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
            return new NameContainsKeywordsPredicate(names);
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
            if (!tags.stream().allMatch(Tag::isValidTagName)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
            return new TagsContainsKeywordsPredicate(tags);
        } else if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            List<String> roles = argMultimap.getAllValues(PREFIX_ROLE);
            if (!roles.stream().allMatch(Person::isValidRole)) {
                throw new ParseException(Person.MESSAGE_ROLE_CONSTRAINTS);
            }
            return new RoleMatchesKeywordsPredicate(roles);
        } else if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            List<String> remarks = argMultimap.getAllValues(PREFIX_REMARK);
            return new RemarkContainsKeywordsPredicate(remarks);
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phones = argMultimap.getAllValues(PREFIX_PHONE);
            if (!phones.stream().allMatch(Phone::isValidPhone)) {
                throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
            }
            return new PhoneMatchesDigitsPredicate(phones);
        } else {
            // no field provided
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
