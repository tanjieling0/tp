package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.storage.TextStateStorage.clearState;
import static seedu.address.storage.TextStateStorage.deleteStateStorage;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;

public class TextStateStorageTest {

    private TextStateStorage textStateStorage;

    @BeforeEach
    public void setUp() {
        this.textStateStorage = new TextStateStorage();
    }
    @AfterAll
    public static void tearDown() throws IOException {
        clearState();
    }

    @Test
    public void createStorage_successfullyCreated_throwsNullPointerException() throws NullPointerException {
        assertNotNull(textStateStorage);
    }


    @Test
    public void getFilePath_successfullyReturned() {
        assertNotNull(textStateStorage.getStateStorageFilePath());
        assertEquals(Paths.get("./data/state.txt"), textStateStorage.getStateStorageFilePath());

    }

    @Test
    public void getFilePathString_successfullyReturned() {
        assertEquals("./data/state.txt", TextStateStorage.getFilePathString());
    }

    @Test
    public void getDirectoryPath_successfullyReturned() {
        assertEquals(Paths.get("./data"), TextStateStorage.getDirectoryPath());
    }


    @Test
    public void loadState_emptyFile_successfullyLoaded() throws DataLoadingException, IOException {
        clearState();
        String expected = "";
        String actual = textStateStorage.readState();
        assertEquals(expected, actual);
    }

    @Test
    public void getLastCommand_emptyFile_successfullyLoaded() throws DataLoadingException, IOException {
        clearState();
        String expected = "";
        String actual = textStateStorage.readState();
        assertEquals(expected, actual);
    }

    @Test
    public void writeState_successfullyWritten() throws IOException, DataLoadingException {
        String expected = "test123abc cba321tset";
        textStateStorage.saveState(expected);
        String actual = textStateStorage.readState();
        assertEquals(expected, actual);
    }

    @Test
    public void overWriteState_successfullyOverWritten() throws DataLoadingException, IOException {
        String expected = "test123abc cba321tset";
        textStateStorage.saveState(expected);
        String actual = textStateStorage.readState();
        assertEquals(expected, actual);

        String overWriteString = "newTest";
        textStateStorage.saveState(overWriteString);
        String actualOverWrittenString = textStateStorage.readState();
        assertEquals(overWriteString, actualOverWrittenString);
    }

    @Test
    public void loadFromFile_fileDoesNotExist_handlesDataLoadingException() throws IOException {
        deleteStateStorage();
        assertThrows(DataLoadingException.class, () -> textStateStorage.readState());
    }

    @Test
    public void isStateStorageExists_fileDoesNotExist_returnsFalse() throws IOException {
        deleteStateStorage();
        assertEquals(false, TextStateStorage.isStateStorageExists());
    }
}
