package seedu.address.storage;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.util.RelatedList;

/**
 * Represents the database to store the previous relate of command before the application is closed.
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
        relatedIdTuples = new RelatedList();

        try {
            if (!Files.exists(DIRECTORY_PATH)) {
                Files.createDirectories(DIRECTORY_PATH);
            }
            if (!Files.exists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
            }
            relatedIdTuples = loadRelate();
        } catch (IOException e) {
            logger.info("Error clearing creating relate storage: " + e.getMessage());
        } catch (DataLoadingException e) {
            logger.info("Error loading relate storage: " + e.getMessage());
        }
    }

    /**
     * Saves the RelateList to the relate storage by writing to the file.
     *
     */
    public void writeRelate(RelatedList relatedIdTuples) {
        requireNonNull(relatedIdTuples);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(relatedIdTuples.toString());
        } catch (IOException e) {
            logger.info("Error saving relate to file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the past related from FILE_PATH if found, else it will return an empty RelatedList.
     *
     * @throws DataLoadingException If the file is not found or cannot be read.
     */
    public RelatedList loadRelate() throws DataLoadingException {
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

        return relatedIdTuples;
    }
}
