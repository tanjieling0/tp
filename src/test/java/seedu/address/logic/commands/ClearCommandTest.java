package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.cleanUpAfterTesting;
import static seedu.address.logic.commands.ClearCommand.setUpForTesting;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalNetConnect;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NetConnect;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {
    @BeforeAll
    public static void setUp() {
        setUpForTesting();
    }

    @AfterAll
    public static void close() {
        cleanUpAfterTesting();
    }

    @Test
    public void execute_emptyNetConnect_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.CLEAR_SUCCESS_MESSAGE, expectedModel);
    }

    @Test
    public void execute_nonEmptyNetConnect_success() {
        Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalNetConnect(), new UserPrefs());
        expectedModel.setNetConnect(new NetConnect());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.CLEAR_SUCCESS_MESSAGE, expectedModel);
    }

}
