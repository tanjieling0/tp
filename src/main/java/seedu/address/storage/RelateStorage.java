package seedu.address.storage;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonTuple;
import seedu.address.model.util.RelatedList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Represents the database to store the previous relate of command before the application is closed.
 */
public class RelateStorage {
    private static final String filePath = "./data/relate.txt";
    private static final Path DIRECTORY_PATH = Paths.get("./data");
    private static final Path FILE_PATH = Paths.get(filePath);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static RelatedList relatedPersons = new RelatedList();

    /**
     * Constructor for the storage.
     * <p>
     * The constructor creates a new file and/or directory if the filepath for ./data/relate.txt does not exist.
     */
    public RelateStorage() {
        assert !filePath.isBlank() : "the file path should not be blank";

        try {
            if (!Files.exists(DIRECTORY_PATH)) {
                Files.createDirectories(DIRECTORY_PATH);
            }
            if (!Files.exists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
            }
        } catch (IOException e) {
            logger.info("Error clearing creating relate storage: " + e.getMessage());
        }
    }

    /**
     * Adds a new related person tuple to the list of related persons.
     *
     * @param personTuple The person tuple to be added.
     */
    public static void addRelatedPersonsTuple(PersonTuple personTuple) {
        assert personTuple != null : "PersonTuple should not be null";
        relatedPersons.add(personTuple);
    }


    /**
     * Clears all the text in the relate storage file.
     */
    public static void clearRelate() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("");
        } catch (IOException e) {
            logger.info("Error clearing relate text: " + e.getMessage());
            logger.info("Starting with a clean slate...");
            deleteRelateStorage();
            RelateStorage relateStorage = new RelateStorage();
        }
    }

    /**
     * Checks if the relate storage file exists.
     *
     * @return True if the file exists, else false.
     */
    public static boolean isRelateStorageExists() {
        return Files.exists(FILE_PATH);
    }

    /**
     * Deletes the entire relate storage file.
     */
    public static void deleteRelateStorage() {
        try {
            Files.delete(FILE_PATH);
        } catch (IOException e) {
            logger.info("Error deleting relate file: " + e.getMessage());
        }
    }

    /**
     * Saves the command to the relate storage by writing to the file.
     *
     * @param input Updated command input (at every change) to be written to the storage file.
     */
    public static void writeRelate(String input) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(input);
        } catch (IOException e) {
            logger.info("Error saving relate to file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the past relate of the command box if found, else it will return an empty command.
     *
     * @return The last input in the command box, or and empty string if not found.
     * @throws DataLoadingException If the file is not found or cannot be read.
     */
    public static String loadRelate() throws DataLoadingException {
        logger.info("Loading relate from " + FILE_PATH + "...");

        String lastCommand = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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
     * Instantiates a relateStorage file if it does not exist. If it already exists, it will return the last command.
     *
     * @return The last input in the command box, or and empty string new file created.
     * @throws DataLoadingException If the file is not found or cannot be read.
     */
    public static String getLastCommand() throws DataLoadingException {
        RelateStorage relateStorage = new RelateStorage();
        String lastCommand = loadRelate();

        return lastCommand;
    }

    /**
     * Returns the location of the relate file.
     *
     * @return The path of the relate file.
     */
    public static Path getFilePath() {
        return FILE_PATH;
    }

    /**
     * Returns the location of the relate file as a String.
     *
     * @return The path of the relate file as a String.
     */
    public static String getFilePathString() {
        return filePath;
    }

    /**
     * Returns the location of the relate directory.
     *
     * @return The path of the relate directory.
     */
    public static Path getDirectoryPath() {
        return DIRECTORY_PATH;
    }
}
