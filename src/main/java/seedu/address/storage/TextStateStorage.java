package seedu.address.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;

/**
 * A class to access state of command box stored in the hard disk as a text file.
 */
public class TextStateStorage implements StateStorage {
    private static final String FILE_PATH_STRING = "./data/state.txt";
    private static final Path DIRECTORY_PATH = Paths.get("./data");
    private static final Path FILE_PATH = Paths.get(FILE_PATH_STRING);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Constructor for the storage.
     * <p>
     * The constructor creates a new file and/or directory if the filepath for ./data/state.txt does not exist.
     */
    public TextStateStorage() {
        assert !FILE_PATH_STRING.isBlank() : "the file path should not be blank";

        try {
            if (!Files.exists(DIRECTORY_PATH)) {
                Files.createDirectories(DIRECTORY_PATH);
            }
            if (!Files.exists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
            }
        } catch (IOException e) {
            logger.info("Error clearing creating state storage: " + e.getMessage());
        }
    }

    /**
     * Clears all the text in the state storage file.
     */
    public static void clearState() throws IOException {
        Path filePath = Paths.get(FILE_PATH_STRING);
        Files.delete(filePath);
        Files.createFile(filePath);
    }

    /**
     * Checks if the state storage file exists.
     *
     * @return True if the file exists, else false.
     */
    public static boolean isStateStorageExists() {
        return Files.exists(FILE_PATH);
    }

    /**
     * Deletes the entire state storage file.
     */
    public static void deleteStateStorage() throws IOException {
        Files.delete(FILE_PATH);
    }

    /**
     * Saves the command to the state storage by writing to the file.
     *
     * @param input Updated command input (at every change) to be written to the storage file.
     */
    public void saveState(String input) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_STRING))) {
            writer.write(input);
        }
    }

    /**
     * Retrieves the past state of the command box if found, else it will return an empty command.
     *
     * @return The last input in the command box, or and empty string if not found.
     * @throws DataLoadingException If the file is not found or cannot be read.
     */
    public String readState() throws DataLoadingException {
        String lastCommand = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_STRING))) {
            String data = reader.readLine();

            while (data != null) {
                lastCommand = lastCommand + data;
                data = reader.readLine();
            }
        } catch (IOException e) {
            throw new DataLoadingException(e);
        }
        return lastCommand;
    }

    /**
     * Returns the location of the state file.
     *
     * @return The path of the state file.
     */
    public Path getStateStorageFilePath() {
        return FILE_PATH;
    }

    /**
     * Returns the location of the state file as a String.
     *
     * @return The path of the state file as a String.
     */
    public static String getFilePathString() {
        return FILE_PATH_STRING;
    }

    /**
     * Returns the location of the state directory.
     *
     * @return The path of the state directory.
     */
    public static Path getDirectoryPath() {
        return DIRECTORY_PATH;
    }
}
