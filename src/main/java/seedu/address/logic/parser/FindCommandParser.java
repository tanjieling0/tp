package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NameContainsKeywordsPredicate;
import seedu.address.model.person.filter.NetConnectPredicate;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);
        argMultimap.verifyOnlyOnePrefix();

        NetConnectPredicate<Person> predicate;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> names = argMultimap.getAllValues(PREFIX_NAME);
            if (!names.stream().allMatch(Name::isValidName)) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }
            predicate = new NameContainsKeywordsPredicate(names);
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tags = argMultimap.getAllValues(PREFIX_TAG);
            if (!tags.stream().allMatch(Tag::isValidTagName)) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
            predicate = new TagsContainsKeywordsPredicate(tags);
        } else {
            // no field provided
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

}
