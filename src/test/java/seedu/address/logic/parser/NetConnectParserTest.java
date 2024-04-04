package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RelateCommand;
import seedu.address.logic.commands.ShowRelatedCommand;
import seedu.address.logic.commands.UnrelateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.NameContainsKeywordsPredicate;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonUtil;

public class NetConnectParserTest {

    private final NetConnectParser parser = new NetConnectParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new ClientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(PersonUtil.getDeleteCommand(ALICE));
        assertEquals(DeleteCommand.byId(ALICE.getId()), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new ClientBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + PREFIX_ID + "1 " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(ID_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .map(s -> "n/" + s).collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_showRelated() throws Exception {
        assertTrue(
                parser.parseCommand(ShowRelatedCommand.COMMAND_WORD + " i/3") instanceof ShowRelatedCommand);
    }

    @Test
    public void parseCommand_relate() throws Exception {
        assertTrue(parser.parseCommand("relate i/1 i/2") instanceof RelateCommand);
    }

    @Test
    public void parseCommand_unrelate() throws Exception {
        assertTrue(parser.parseCommand("unrelate i/1 i/2") instanceof UnrelateCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_export() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(ExportCommand.COMMAND_WORD);
        assertTrue(command instanceof ExportCommand);
        assertEquals(new ExportCommand(), command);

        String filename = "contacts.csv";
        ExportCommand commandWithFilename = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + filename);
        assertTrue(commandWithFilename instanceof ExportCommand);
        assertEquals(new ExportCommand(filename), commandWithFilename);
    }

}
