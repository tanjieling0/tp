package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.NetConnect;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.IdTupleBuilder;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableNetConnectTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableNetConnectTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsNetConnect.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonNetConnect.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonNetConnect.json");
    private static final Path DUPLICATE_ID_FILE = TEST_DATA_FOLDER.resolve("duplicateIdNetConnect.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableNetConnect dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableNetConnect.class).get();
        NetConnect netConnectFromFile = dataFromFile.toModelType();
        NetConnect typicalPersonsNetConnect = TypicalPersons.getTypicalNetConnect();
        assertEquals(netConnectFromFile, typicalPersonsNetConnect);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNetConnect dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableNetConnect.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableNetConnect dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableNetConnect.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNetConnect.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateIds_throwsIllegalValueException() throws Exception {
        JsonSerializableNetConnect dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ID_FILE,
                JsonSerializableNetConnect.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNetConnect.MESSAGE_DUPLICATE_ID,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_noDuplicatePersonsAndIds_success() throws Exception {
        List<JsonAdaptedPerson> persons = new ArrayList<>();
        persons.add(new JsonAdaptedPerson(new ClientBuilder().withName("one").build()));
        persons.add(new JsonAdaptedPerson(new ClientBuilder().withName("two").withId(2).build()));
        List<JsonAdaptedIdTuple> relatedIds = new ArrayList<>();
        relatedIds.add(new JsonAdaptedIdTuple(new IdTupleBuilder().build()));
        relatedIds.add(new JsonAdaptedIdTuple(new IdTupleBuilder().withFirstPersonId(4).build()));
        JsonSerializableNetConnect data = new JsonSerializableNetConnect(persons, relatedIds);

        NetConnect netConnect = data.toModelType();

        assertEquals(persons.size(), netConnect.getPersonList().size());
        assertEquals(relatedIds.size(), netConnect.getRelatedList().size());
    }

    @Test
    public void toModelType_duplicateIdTuples_throwsIllegalValueException() throws Exception {
        List<JsonAdaptedPerson> persons = new ArrayList<>();
        List<JsonAdaptedIdTuple> relatedIds = new ArrayList<>();
        relatedIds.add(new JsonAdaptedIdTuple(new IdTupleBuilder().build()));
        relatedIds.add(new JsonAdaptedIdTuple(new IdTupleBuilder().build()));
        JsonSerializableNetConnect data = new JsonSerializableNetConnect(persons, relatedIds);

        assertThrows(IllegalValueException.class, data::toModelType);
    }
}
