package seedu.address.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Utility class for testing command format validation.
 */
public class TestCommandFormatUtil {
    private static final String MESSAGE_CONSTRAINTS = "Invalid command format! Invalid prefix has been detected.";
    private static final String VALIDATION_STRING = "[^/]*";

    /**
     * Checks if the given input contains a slash ("/").
     *
     * @param input the input string to be checked
     * @throws IllegalArgumentException if the input does not contain a slash
     */
    public static void checkArgumentContainSlash(String input) {
        checkArgument(validate(input), MESSAGE_CONSTRAINTS);
    }

    public static boolean validate(String input) {
        return input.matches(VALIDATION_STRING);
    }
}
