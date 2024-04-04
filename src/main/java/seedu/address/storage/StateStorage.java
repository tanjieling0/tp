package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataLoadingException;

/**
 * Represents the database to store the previous state of command before the application is closed.
 */
public interface StateStorage {
    /**
     * Returns the file path of the StateStorage data file.
     */
    Path getStateStorageFilePath();

    /**
     * Returns command box state data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    String readState() throws DataLoadingException;

    /**
     * Saves the given string retrieved from the command box to the state storage.
     *
     * @param input retrieved from the command box.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveState(String input) throws IOException;
}
