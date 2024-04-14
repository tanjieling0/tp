package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREFERENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TERMSOFSERVICE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Client;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Employee;
import seedu.address.model.person.JobTitle;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Products;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Skills;
import seedu.address.model.person.Supplier;
import seedu.address.model.person.TermsOfService;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK, PREFIX_ROLE, PREFIX_PREFERENCES, PREFIX_PRODUCTS,
                PREFIX_DEPARTMENT, PREFIX_JOBTITLE, PREFIX_TERMSOFSERVICE, PREFIX_SKILLS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = argMultimap.getValue(PREFIX_REMARK).isPresent()
                ? ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get())
                : new Remark("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        String role = argMultimap.getValue(PREFIX_ROLE).get();

        return new AddCommand(
                createPerson(role, name, phone, email, address, remark, tagList, argMultimap));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any of the prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static Person createPerson(
            String role, Name name, Phone phone, Email email,
            Address address, Remark remark, Set<Tag> tagList, ArgumentMultimap argMultimap)
            throws ParseException {
        switch (role.toLowerCase()) {
        case "client":
            if (anyPrefixesPresent(
                    argMultimap, PREFIX_DEPARTMENT, PREFIX_JOBTITLE, PREFIX_SKILLS, PREFIX_TERMSOFSERVICE)) {
                throw new ParseException(Messages.MESSAGE_INVALID_CLIENT_PROPERTY);
            }
            String preferences = "";
            Products products = new Products();
            if (argMultimap.getValue(PREFIX_PREFERENCES).isPresent()) {
                preferences = ParserUtil.parsePreferences(argMultimap.getValue(PREFIX_PREFERENCES).get());
            }
            if (argMultimap.getValue(PREFIX_PRODUCTS).isPresent()) {
                products = ParserUtil.parseProducts(argMultimap.getAllValues(PREFIX_PRODUCTS));
            }
            return new Client(name, phone, email, address, remark, tagList, products, preferences);
        case "employee":
            if (anyPrefixesPresent(argMultimap, PREFIX_PRODUCTS, PREFIX_PREFERENCES, PREFIX_TERMSOFSERVICE)) {
                throw new ParseException(Messages.MESSAGE_INVALID_EMPLOYEE_PROPERTY);
            }
            Department department = new Department();
            JobTitle jobTitle = new JobTitle();
            Skills skills = new Skills();
            if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
                department = ParserUtil.parseDepartment(argMultimap.getValue(PREFIX_DEPARTMENT).get());
            }
            if (argMultimap.getValue(PREFIX_JOBTITLE).isPresent()) {
                jobTitle = ParserUtil.parseJobTitle(argMultimap.getValue(PREFIX_JOBTITLE).get());
            }
            if (argMultimap.getValue(PREFIX_SKILLS).isPresent()) {
                skills = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILLS));
            }
            return new Employee(name, phone, email, address, remark, tagList, department, jobTitle, skills);
        case "supplier":
            if (anyPrefixesPresent(
                    argMultimap, PREFIX_DEPARTMENT, PREFIX_JOBTITLE, PREFIX_SKILLS, PREFIX_PREFERENCES)) {
                throw new ParseException(Messages.MESSAGE_INVALID_SUPPLIER_PROPERTY);
            }
            TermsOfService termsOfService = new TermsOfService();
            Products supplierProducts = new Products();
            if (argMultimap.getValue(PREFIX_TERMSOFSERVICE).isPresent()) {
                termsOfService = ParserUtil.parseTermsOfService(argMultimap.getValue(PREFIX_TERMSOFSERVICE).get());
            }
            if (argMultimap.getValue(PREFIX_PRODUCTS).isPresent()) {
                supplierProducts = ParserUtil.parseProducts(argMultimap.getAllValues(PREFIX_PRODUCTS));
            }
            return new Supplier(name, phone, email, address, remark, tagList, supplierProducts, termsOfService);
        default:
            throw new ParseException("Invalid role specified. Must be one of: client, employee, supplier.");
        }
    }

}
