package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.ClinicBook;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;
import seedu.clinic.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) for {@code GetHistoryCommand}.
 */
public class GetHistoryCommandTest {

    @Test
    public void equals() {
        GetHistoryCommand firstCommand = new GetHistoryCommand("S1234567D");
        GetHistoryCommand secondCommand = new GetHistoryCommand("T1234567J");

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new GetHistoryCommand("S1234567D")));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_matchingPatient_foundSinglePatient() {
        Model model = createModelWithSampleRecords();
        Model expectedModel = new ModelManager(model.getClinicBook(), new UserPrefs());

        GetHistoryCommand command = new GetHistoryCommand("S1234567D");
        command.execute(expectedModel);

        String expectedMessage = String.format(GetHistoryCommand.MESSAGE_RESULT, "S1234567D", 1);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonMatchingPatient_foundNoPatient() {
        Model model = createModelWithSampleRecords();
        Model expectedModel = new ModelManager(model.getClinicBook(), new UserPrefs());

        GetHistoryCommand command = new GetHistoryCommand("T0000000A");
        command.execute(expectedModel);

        String expectedMessage = String.format(GetHistoryCommand.MESSAGE_RESULT, "T0000000A", 0);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    private static Model createModelWithSampleRecords() {
        ClinicBook clinicBook = new ClinicBook();
        clinicBook.addPerson(new Patient(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Clementi Rd"),
                Set.of(),
                new NRIC("S1234567D"),
                LocalDate.of(1990, 1, 1),
            "91230000",
            1));
        clinicBook.addPerson(new Patient(
                new Name("Bob Lee"),
                new Phone("92345678"),
                new Email("bob@example.com"),
                new Address("456 Jurong West St"),
                Set.of(),
                new NRIC("T1234567J"),
                LocalDate.of(1988, 6, 15),
            "92340000",
            2));
        clinicBook.addPerson(new Person(
                new Name("Carl Helper"),
                new Phone("93456789"),
                new Email("carl@example.com"),
                new Address("789 Yishun Ave"),
                Set.of(),
                9));

        return new ModelManager(clinicBook, new UserPrefs());
    }
}
