package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.filter.NameContainsKeywordsPredicate;
import seedu.address.model.util.RelatedList;
import seedu.address.testutil.NetConnectBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new NetConnect(), new NetConnect(modelManager.getNetConnect()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setNetConnectFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setNetConnectFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setNetConnectFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setNetConnectFilePath(null));
    }

    @Test
    public void setNetConnectFilePath_validPath_setsNetConnectFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setNetConnectFilePath(path);
        assertEquals(path, modelManager.getNetConnectFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInNetConnect_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInNetConnect_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasId_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasId(null));
    }

    @Test
    public void hasId_idNotInNetConnect_returnsFalse() {
        assertFalse(modelManager.hasId(ID_FIRST_PERSON));
    }

    @Test
    public void hasId_idInNetConnect_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasId(ALICE.getId()));
    }

    @Test
    public void getPersonById_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.getPersonById(null));
    }

    @Test
    public void getPersonById_idNotInNetConnect_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.getPersonById(ALICE.getId()));
    }

    @Test
    public void getPersonById_idInNetConnect_returnsPerson() {
        modelManager.addPerson(ALICE);
        assertEquals(ALICE, modelManager.getPersonById(ALICE.getId()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        NetConnect netConnect = new NetConnectBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        RelatedList relatedList = new RelatedList();

        modelManager = new ModelManager(netConnect, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(netConnect, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(modelManager.equals(modelManager));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(modelManager.equals(null));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(modelManager.equals(5));
    }

    @Test
    public void equals_differentNetConnect_returnsFalse() {
        NetConnect netConnect = new NetConnectBuilder().withPerson(ALICE).withPerson(BENSON).build();
        NetConnect differentNetConnect = new NetConnect();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(netConnect, userPrefs);
        ModelManager otherModelManager = new ModelManager(differentNetConnect, userPrefs);

        assertFalse(modelManager.equals(otherModelManager));
    }

    @Test
    public void equals_differentFilteredPersonList_returnsFalse() {
        NetConnect netConnect = new NetConnectBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        modelManager = new ModelManager(netConnect, userPrefs);
        String[] keywords = ALICE.getName().fullName.split("\\s+");
      
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        ModelManager modelManagerCopy = new ModelManager(netConnect, userPrefs);
        assertFalse(modelManager.equals(modelManagerCopy));
    }


    @Test
    public void equals_differentUserPrefs_returnsFalse() {
        NetConnect netConnect = new NetConnectBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setNetConnectFilePath(Paths.get("differentFilePath"));

        modelManager = new ModelManager(netConnect, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(netConnect, differentUserPrefs);

        assertFalse(modelManager.equals(modelManagerCopy));
    }

}
