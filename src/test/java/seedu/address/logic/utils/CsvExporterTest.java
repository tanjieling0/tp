package seedu.address.logic.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Client;
import seedu.address.model.person.Person;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.SupplierBuilder;
import seedu.address.testutil.TypicalPersons;

public class CsvExporterTest {

    private static final String TEST_FILENAME = "test_export.csv";

    private CsvExporter csvExporter;
    private FilteredList<Person> persons;

    @BeforeEach
    public void setUp() {
        List<Person> personList = Arrays.asList(
                TypicalPersons.ALICE, TypicalPersons.BENSON,
                TypicalPersons.DANIEL, TypicalPersons.ELLE, TypicalPersons.FIONA);

        ObservableList<Person> sourceList = FXCollections.observableArrayList(personList);

        persons = new FilteredList<>(sourceList);

        csvExporter = new CsvExporter(persons, TEST_FILENAME);
    }

    @Test
    public void createDataList_correctlyConstructedList() {
        List<Person> persons = Arrays.asList(
                new EmployeeBuilder().withId(1).withName("John Doe").withPhone("12345678")
                        .withEmail("john@example.com").withAddress("123 Main St").withDepartment("HR")
                        .withJobTitle("Manager").withSkills("Java, SQL").build(),
                new ClientBuilder().withId(2).withName("Jane Smith").withPhone("98765432")
                        .withEmail("jane@example.com")
                        .withAddress("456 Elm St").withProducts("Product A").withPreferences("Likes discounts")
                        .build(),
                new SupplierBuilder().withId(3).withName("Acme Inc").withPhone("55555555").withEmail("info@acme.com")
                        .withAddress("789 Oak St").withProducts("Product X").withTermsOfService("30 days").build()
        );
        FilteredList<Person> filteredList = new FilteredList<>(FXCollections.observableArrayList(persons));
        CsvExporter csvExporter = new CsvExporter(filteredList, "test.csv");

        List<String[]> dataList = csvExporter.createDataList();

        assertTrue(dataList != null);
        assertTrue(dataList.size() == 4);

        assertArrayEquals(new String[]{"ID", "Name", "Phone", "Email", "Address", "Remark", "Tags", "Department",
            "Job Title", "Skills", "Products", "Preferences", "Terms of Service"}, dataList.get(0));

        assertArrayEquals(new String[]{"1", "John Doe", "12345678", "john@example.com", "\"123 Main St\"",
            "some remarks", "", "HR", "Manager", "Java, SQL", "", "", ""}, dataList.get(1));
        assertArrayEquals(new String[]{"2", "Jane Smith", "98765432", "jane@example.com", "\"456 Elm St\"",
            "some remarks", "", "", "", "", "Product A", "Likes discounts", ""}, dataList.get(2));
        assertArrayEquals(new String[]{"3", "Acme Inc", "55555555", "info@acme.com", "\"789 Oak St\"",
            "some remarks", "", "", "", "", "Product X", "", "30 days"}, dataList.get(3));
    }

    @Test
    public void execute_exportSuccess() {
        csvExporter.execute();
        assertTrue(csvExporter.getIsSuccessful());
        File exportedFile = new File(TEST_FILENAME);
        assertTrue(exportedFile.exists());
        exportedFile.delete(); // Clean up after test
    }

    @Test
    public void execute_exportFailure() {
        // Simulate a situation where IOException occurs during file write
        csvExporter = new CsvExporter(persons, ""); // Provide an invalid filename to trigger IOException
        csvExporter.execute();
        assertFalse(csvExporter.getIsSuccessful());
    }

    @Test
    public void convertPersonToStringArray() {
        Client person = TypicalPersons.ALICE;
        String[] expectedArray = new String[]{
            person.getId().toString(),
            person.getName().toString(),
            person.getPhone().toString(),
            person.getEmail().toString(),
            "\"" + person.getAddress().toString() + "\"",
            person.getRemark().toString(),
            "\"" + person.getTagsAsString() + "\"",
            "",
            "",
            "",
            person.getProducts().toString(),
            person.getPreferences().toString(),
            ""
        };
        assertArrayEquals(expectedArray, csvExporter.convertPersonToStringArray(person));
    }

    @Test
    public void getIsSuccessful_afterSuccessfulExecution() {
        csvExporter.execute();
        assertTrue(csvExporter.getIsSuccessful());
    }

    @Test
    public void getIsSuccessful_afterFailedExecution() {
        csvExporter = new CsvExporter(persons, "");
        csvExporter.execute();
        assertFalse(csvExporter.getIsSuccessful());
    }

    @Test
    public void convertPersonToStringArray_employee() {
        String[] expectedArray = new String[]{
            "3", "Daniel Meier", "87652533", "cornelia@example.com", "\"10th street\"", "some remarks",
            "\"friends\"", "Marketing", "Manager", "Digital Marketing, Public Speaking", "", "", ""
        };
        assertArrayEquals(expectedArray, csvExporter.convertPersonToStringArray(TypicalPersons.DANIEL));
    }

    @Test
    public void convertPersonToStringArray_client() {
        String[] expectedArray = new String[]{
            "1", "Alice Pauline", "94351253", "alice@example.com", "\"123, Jurong West Ave 6, #08-111\"",
            "some remarks", "\"friends\"", "", "", "", "Product1, Product2", "Vegan", ""
        };
        assertArrayEquals(expectedArray, csvExporter.convertPersonToStringArray(TypicalPersons.ALICE));
    }

    @Test
    public void convertPersonToStringArray_supplier() {
        String[] expectedArray = new String[]{
            "5", "Fiona Kunz", "94824271", "lydia@example.com", "\"little tokyo\"", "some remarks", "", "", "",
            "", "Office Supplies, Furniture", "", "Delivery within 2 weeks"
        };
        assertArrayEquals(expectedArray, csvExporter.convertPersonToStringArray(TypicalPersons.FIONA));
    }
}
