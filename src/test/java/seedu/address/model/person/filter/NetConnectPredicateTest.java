package seedu.address.model.person.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.ClientBuilder;

class NetConnectPredicateTest {

    @Test
    public void box() {
        // always true
        Person person = new ClientBuilder().withName(VALID_NAME_AMY).build();
        NetConnectPredicate<Person> predicate = NetConnectPredicate.box(p -> true);
        assertTrue(predicate.test(person));

        // matching person
        predicate = NetConnectPredicate.box(p -> p.getName().fullName.equals(VALID_NAME_AMY));
        assertTrue(predicate.test(person));

        // non-matching person
        predicate = NetConnectPredicate.box(p -> p.getName().fullName.equals(VALID_NAME_BOB));
        assertFalse(predicate.test(person));

        assertNull(predicate.formatFilter());
    }
}
