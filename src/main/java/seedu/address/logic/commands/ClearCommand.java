package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.NetConnect;
import seedu.address.ui.ClearConfirmationWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (ClearConfirmationWindow.isConfirmedClear()) {
            ClearConfirmationWindow.resetConfirmation();
            model.setNetConnect(new NetConnect());
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult("Clear command cancelled");
    }
}
