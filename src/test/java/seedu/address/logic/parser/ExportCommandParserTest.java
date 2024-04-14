package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_returnsDefaultExportCommand() {
        assertParseSuccess(parser, "", new ExportCommand());
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        String filename = "test.csv";
        ExportCommand expectedExportCommand = new ExportCommand(filename);
        assertParseSuccess(parser, filename, expectedExportCommand);
    }

    @Test
    public void parse_invalidFilename_throwsParseException() {
        String invalidFilename = "invalid";
        String expectedErrorMessage =
                "Error: Invalid filename. Please provide a valid filename with the .csv extension.";
        assertParseFailure(parser, invalidFilename, expectedErrorMessage);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String expectedErrorMessage = String.format(ExportCommandParser.MESSAGE_NO_EXTENSION);
        assertParseFailure(parser, "   ", expectedErrorMessage);
    }

    @Test
    public void parse_blankArgs_throwsParseException() {
        ExportCommand expectedExportCommand = new ExportCommand();
        assertParseSuccess(parser, "", expectedExportCommand);
    }

    @Test
    public void testIsValidFilenameWithValidFilename() {
        String validFilename = "test.csv";
        ExportCommandParser exportCommandParser = new ExportCommandParser();
        assertTrue(exportCommandParser.isValidFilename(validFilename));
    }

    @Test
    public void testGetErrorMessageNoExtension() {
        String filename = "test";
        ExportCommandParser exportCommandParser = new ExportCommandParser();
        assertTrue(exportCommandParser.getErrorMessage(filename).equals("Error: Invalid filename. "
                + "Please provide a valid filename with the .csv extension."));
    }

    @Test
    public void testGetErrorMessageSpaceDetected() {
        String filename = " test.csv";
        ExportCommandParser exportCommandParser = new ExportCommandParser();
        assertTrue(exportCommandParser.getErrorMessage(filename).equals("Error: Invalid filename. "
                + "Please ensure the filename does not contain leading, trailing spaces, or end with a period."));
    }

    @Test
    public void testGetErrorMessageNoSpecialCharacter() {
        String filename = "test@file.csv";
        ExportCommandParser exportCommandParser = new ExportCommandParser();
        assertTrue(exportCommandParser.getErrorMessage(filename).equals("Error: Invalid filename. "
                + "Please ensure the filename only contain alphanumeric, underscores, periods and hyphens"));
    }
}
