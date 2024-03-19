package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a Clear Confirmation page
 */
public class ClearConfirmationWindow extends UiPart<Stage> {
    public static final String CONFIRMATION_MESSAGE = "Are you sure you want to clear the address book?";

    private static final Logger logger = LogsCenter.getLogger(ClearConfirmationWindow.class);
    private static final String FXML = "ClearConfirmationWindow.fxml";
    private static boolean isConfirmedClear = false;

    @FXML
    private Button confirmButton;

    @FXML
    private Label clearConfirmationMessage;

    /**
     * Creates a new ClearConfirmationWindow.
     *
     * @param root Stage to use as the root of the ClearConfirmationWindow.
     */
    public ClearConfirmationWindow(Stage root) {
        super(FXML, root);
        clearConfirmationMessage.setText(CONFIRMATION_MESSAGE);
    }

    /**
     * Creates a new ClearConfirmationWindow.
     */
    public ClearConfirmationWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing confirmation query for clear command.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the clear confirmation window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the clear confirmation window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the clear confirmation window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Confirms the clear command.
     */
    @FXML
    private void confirmClear() {
        isConfirmedClear = true;
        getRoot().hide();
    }

    /**
     * Returns true if the clear command is confirmed.
     */
    public static boolean isConfirmedClear() {
        return isConfirmedClear;
    }

    /**
     * Resets the confirmation status of the clear command.
     */
    public static void resetConfirmation() {
        isConfirmedClear = false;
    }
}
