package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void testGetSkillsAsString() {
        // Create a set of skills
        Set<String> skillsSet = new HashSet<>();
        skillsSet.add("Java");
        skillsSet.add("Python");
        skillsSet.add("SQL");

        // Create an Employee object with the skills set
        Employee employee = new Employee(new Name("John"), new Phone("12345678"), new Email("john@example.com"),
            new Address("123 Street"), new Remark(""), new HashSet<>(), new Department("IT"),
            new JobTitle("Software Engineer"), new Skills(skillsSet));

        // Test the getSkillsAsString() method
        String expectedSkillsString = "\"Java, Python, SQL\"";
        String actualSkillsString = employee.getSkillsAsString();
        assertEquals(expectedSkillsString, actualSkillsString,
                "Skills string should be formatted correctly with double quotes and comma-separated values.");
    }

    @Test
    public void testGetSkillsAsStringWithEmptySkillsSet() {
        // Create an empty set of skills
        Set<String> emptySkillsSet = new HashSet<>();

        // Create an Employee object with the empty skills set
        Employee employee = new Employee(new Name("John"), new Phone("12345678"), new Email("john@example.com"),
            new Address("123 Street"), new Remark(""), new HashSet<>(), new Department("IT"),
            new JobTitle("Software Engineer"), new Skills(emptySkillsSet));

        // Test the getSkillsAsString() method with an empty skills set
        String expectedEmptySkillsString = "";
        String actualEmptySkillsString = employee.getSkillsAsString();
        assertEquals(expectedEmptySkillsString, actualEmptySkillsString,
                "Skills string should be empty when the skills set is empty.");
    }
}

