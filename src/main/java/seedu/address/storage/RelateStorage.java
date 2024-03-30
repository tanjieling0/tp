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
import seedu.address.model.util.IdTuple;
import seedu.address.model.util.RelatedList;

/**
 * Represents the database to store the previous relate of command before the application is closed.
 * Note design decision here to store relations separately from UniquePersonList or InternalList as this is a relation
 * and not an entity, and should not need to change the list of persons. This is to prevent unnecessary coupling.
 * However, deletions in persons from central person list must coalesce with deletions in relations.
 */
public class RelateStorage {
    private static final String filePath = "./data/relate.txt";
    private static final Path DIRECTORY_PATH = Paths.get("./data");
    private static final Path FILE_PATH = Paths.get(filePath);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static RelatedList relatedIdTuples;

    /**
     * Constructor for the storage.
     * <p>
     * The constructor creates a new file and/or directory if the filepath for ./data/relate.txt does not exist.
     */
    public RelateStorage() {
        assert !filePath.isBlank() : "the file path should not be blank";

        relatedIdTuples = new RelatedList();

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
     * @param idTuple The id tuple to be added.
     */
    public static void addRelatedIdTuple(IdTuple idTuple) {
        assert idTuple != null : "idTuple should not be null";
        relatedIdTuples.add(idTuple);
    }

    /**
     * Removes a related person tuple from the list of related persons.
     *
     * @param idTuple The id tuple to be removed.
     */
    public static void removeRelatedIdTuple(IdTuple idTuple) {
        assert idTuple != null : "idTuple should not be null";
        relatedIdTuples.remove(idTuple);
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
     * Saves the RelateList to the relate storage by writing to the file.
     *
     */
    public static void writeRelate() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(relatedIdTuples.toString());
        } catch (IOException e) {
            logger.info("Error saving relate to file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the past relate of the command box if found, else it will return an empty command.
     *
     * @throws DataLoadingException If the file is not found or cannot be read.
     */
    public void loadRelate() throws DataLoadingException {
        logger.info("Loading relate from " + FILE_PATH + "...");

        RelatedList relatedListTemp = new RelatedList();

        String storedIdList = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String data = reader.readLine();

            while (data != null) {
                storedIdList = storedIdList + data;
                data = reader.readLine();
            }
        } catch (IOException e) {
            throw new DataLoadingException(e);
        }
        relatedIdTuples = relatedListTemp.toArrayList(storedIdList);
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
