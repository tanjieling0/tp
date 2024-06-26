package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.utils.CsvExporter;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.filter.Filter;
import seedu.address.model.person.filter.NetConnectPredicate;
import seedu.address.model.util.IdTuple;
import seedu.address.model.util.RelatedList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final NetConnect netConnect;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Filter filter = Filter.noFilter();

    /**
     * Initializes a ModelManager with the given netConnect and userPrefs.
     */
    public ModelManager(ReadOnlyNetConnect netConnect, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(netConnect, userPrefs);

        logger.fine("Initializing with address book: "
                + netConnect + " , user prefs "
                + userPrefs);

        this.netConnect = new NetConnect(netConnect);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.netConnect.getPersonList());
    }

    public ModelManager() {
        this(new NetConnect(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNetConnectFilePath() {
        return userPrefs.getNetConnectFilePath();
    }

    @Override
    public void setNetConnectFilePath(Path netConnectFilePath) {
        requireNonNull(netConnectFilePath);
        userPrefs.setNetConnectFilePath(netConnectFilePath);
    }

    // =========== NetConnect
    // ================================================================================

    @Override
    public void setNetConnect(ReadOnlyNetConnect netConnect) {
        this.netConnect.resetData(netConnect);
    }

    @Override
    public ReadOnlyNetConnect getNetConnect() {
        return netConnect;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return netConnect.hasPerson(person);
    }

    @Override
    public boolean hasId(Id id) {
        requireNonNull(id);
        return netConnect.hasId(id);
    }

    @Override
    public Person getPersonById(Id id) {
        requireNonNull(id);
        return netConnect.getPersonById(id);
    }

    @Override
    public int countPersonsWithName(Name name) {
        requireNonNull(name);
        return netConnect.countPersonsWithName(name);
    }

    @Override
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        return netConnect.getPersonByName(name);
    }

    @Override
    public void deletePerson(Person target) {
        netConnect.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        netConnect.addPerson(person);
        clearFilter();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        netConnect.setPerson(target, editedPerson);
    }

    @Override
    public boolean exportCsv(String filename) {
        requireNonNull(filename);
        CsvExporter exporter = new CsvExporter(filteredPersons, filename);
        exporter.execute();
        return exporter.getIsSuccessful();
    }

    // =========== Related List Accessors
    // =============================================================

    @Override
    public boolean hasRelatedIdTuple(IdTuple idTuple) {
        requireNonNull(idTuple);
        return netConnect.hasRelatedId(idTuple);
    }

    @Override
    public void addRelatedIdTuple(IdTuple idTuple) {
        requireNonNull(idTuple);
        netConnect.allowAddIdTuple(idTuple);
    }

    @Override
    public boolean removeRelatedIdTuple(IdTuple idTuple) {
        requireNonNull(idTuple);
        return netConnect.removeRelatedIdTuple(idTuple);
    }

    @Override
    public RelatedList getRelatedIdTuples() {
        return netConnect.getRelatedList();
    }

    // =========== Filtered Person List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the
     * internal list of {@code versionedNetConnect}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void clearFilter() {
        filter = Filter.noFilter();
        filteredPersons.setPredicate(filter);
    }

    @Override
    public void stackFilters(NetConnectPredicate<Person> predicate) {
        requireNonNull(predicate);

        filter = filter.add(predicate);
        filteredPersons.setPredicate(filter);
    }

    @Override
    public void updateFilteredList(NetConnectPredicate<Person> predicate) {
        requireNonNull(predicate);

        filter = Filter.noFilter();
        filteredPersons.setPredicate(Filter.of(Collections.singletonList(predicate)));
    }

    @Override
    public String printFilters() {
        return String.format(Filter.MESSAGE_FILTERS_APPLIED, filter.size(), filter.formatFilter());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return netConnect.equals(otherModelManager.netConnect)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
