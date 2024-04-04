package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.NetConnect;
import seedu.address.model.ReadOnlyNetConnect;
import seedu.address.model.person.Person;
import seedu.address.model.util.IdTuple;

/**
 * An Immutable NetConnect that is serializable to JSON format.
 */
@JsonRootName(value = "netconnect")
class JsonSerializableNetConnect {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ID = "Persons list contains duplicate id(s).";
    public static final String MESSAGE_DUPLICATE_ID_TUPLE = "Related Ids list contains duplicate id(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedIdTuple> relatedIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableNetConnect} with the given persons.
     */
    @JsonCreator
    public JsonSerializableNetConnect(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                      @JsonProperty("relatedIds") List<JsonAdaptedIdTuple> relatedIds) {
        this.persons.addAll(persons);
        this.relatedIds.addAll(relatedIds);
    }

    /**
     * Converts a given {@code ReadOnlyNetConnect} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableNetConnect}.
     */
    public JsonSerializableNetConnect(ReadOnlyNetConnect source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        relatedIds.addAll(source.getListIdTuple().stream().map(JsonAdaptedIdTuple::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code NetConnect} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public NetConnect toModelType() throws IllegalValueException {
        NetConnect netConnect = new NetConnect();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (netConnect.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            if (netConnect.hasId(person.getId())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ID);
            }
            netConnect.addPerson(person);
        }
        for (JsonAdaptedIdTuple idTuple : relatedIds) {
            IdTuple idTupleModel = idTuple.toModelType();
            if (netConnect.hasRelatedId(idTupleModel)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ID_TUPLE);
            }
            netConnect.allowAddIdTuple(idTupleModel);
        }
        return netConnect;
    }

}
