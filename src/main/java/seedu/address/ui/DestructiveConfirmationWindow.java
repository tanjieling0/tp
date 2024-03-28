package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a Destructive Action page
 */
public class DestructiveConfirmationWindow {
    private static final Logger logger = LogsCenter.getLogger(DestructiveConfirmationWindow.class);

    private static final String TITLE = "Destructive Action Confirmation";
    private static final String CLEAR_HEADER_TEXT =
            "Are you sure you want to clear the address book? This action cannot be undone.";
    private static final String DELETE_HEADER_TEXT =
            "Are you sure you want to delete this person? This action cannot be undone";

    /**
     * Shows a confirmation dialog for destructive actions.
     *
     * @param isDeleteCommand true if the command is a delete command
     * @param isClearCommand true if the command is a clear command
     * @return true if the user confirms the action, false otherwise
     */
    @FXML
    public static boolean handleDestructiveConfirmation(boolean isDeleteCommand, boolean isClearCommand) {

        logger.info("Showing destructive action confirmation");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(TITLE);
        if (isClearCommand) {
            alert.setHeaderText(CLEAR_HEADER_TEXT);
        } else if (isDeleteCommand) {
            alert.setHeaderText(DELETE_HEADER_TEXT);
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
