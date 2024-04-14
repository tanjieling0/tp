package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    public static final String MESSAGE_NO_EXTENSION = "Error: Invalid filename. "
            + "Please provide a valid filename with the .csv extension.";

    public static final String MESSAGE_SPACE_DETECTED = "Error: Invalid filename. "
            + "Please ensure the filename does not contain leading, trailing spaces, or end with a period.";

    public static final String MESSAGE_NO_SPECIAL_CHARACTER = "Error: Invalid filename. "
            + "Please ensure the filename only contain alphanumeric, underscores, periods and hyphens";


    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     *
     * @param args The user input string containing the filename for export.
     * @return An ExportCommand object representing the parsed command.
     * @throws ParseException if the user input does not conform to the expected format
     *                        or if the filename is invalid.
     */
    public ExportCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ExportCommand();
        }
        String trimmedArgs = args.trim();
        if (!isValidFilename(trimmedArgs)) {
            throw new ParseException(getErrorMessage(trimmedArgs));
        }
        return new ExportCommand(trimmedArgs);
    }

    /**
     * The isValidFilename function in Java checks if a given filename ends with ".csv", does not start or end with
     *     spaces or a period, and only contains alphanumeric characters, underscores, hyphens, and periods.
     * @param filename The `isValidFilename` method checks if a given filename is valid based on the following
     *     criteria:
     * @return The method `isValidFilename` returns a boolean value indicating whether the input filename is
     *     considered valid based on the specified criteria. It returns `true` if the filename ends with ".csv", does
     *     not start or end with spaces or end with a period, and only contains alphanumeric characters, underscores,
     *     hyphens, and periods. If any of these conditions are not met, it returns `false`.
     */
    public boolean isValidFilename(String filename) {
        // Check if filename ends with ".csv"
        if (!filename.endsWith(".csv")) {
            return false;
        }

        if (filename.startsWith(" ") || filename.endsWith(" ") || filename.endsWith(".")) {
            return false;
        }

        if (!filename.matches("[a-zA-Z0-9_\\-.]*")) {
            return false;
        }

        return true;
    }

    public String getErrorMessage(String filename) {
        if (!filename.endsWith(".csv")) {
            return MESSAGE_NO_EXTENSION;
        }

        if (filename.startsWith(" ") || filename.endsWith(" ") || filename.endsWith(".")) {
            return MESSAGE_SPACE_DETECTED;
        }

        if (!filename.matches("[a-zA-Z0-9_\\-.]*")) {
            return MESSAGE_NO_SPECIAL_CHARACTER;
        }

        return null;
    }
}

