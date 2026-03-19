package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.clinic.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinic.testutil.TypicalPatients.NADIA_NAME;
import static seedu.clinic.testutil.TypicalPatients.NADIA_NRIC;
import static seedu.clinic.testutil.TypicalPatients.createNadia;
import static seedu.clinic.testutil.TypicalPersons.BENSON;
import static seedu.clinic.testutil.TypicalPersons.CARL;
import static seedu.clinic.testutil.TypicalPersons.ELLE;
import static seedu.clinic.testutil.TypicalPersons.FIONA;
import static seedu.clinic.testutil.TypicalPersons.getTypicalClinicBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.PersonMatchesFindCriteriaPredicate;
import seedu.clinic.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalClinicBookWithPatient(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getClinicBook(), new UserPrefs());

    private static seedu.clinic.model.ClinicBook getTypicalClinicBookWithPatient() {
        seedu.clinic.model.ClinicBook clinicBook = getTypicalClinicBook();
        clinicBook.addPerson(createNadia());
        return clinicBook;
    }

    @Test
    public void equals() {
        PersonMatchesFindCriteriaPredicate firstPredicate =
            new PersonMatchesFindCriteriaPredicate(Collections.singletonList("first"),
                    Optional.empty(), Optional.empty());
        PersonMatchesFindCriteriaPredicate secondPredicate =
            new PersonMatchesFindCriteriaPredicate(Collections.singletonList("second"),
                    Optional.empty(), Optional.empty());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noMatches_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonMatchesFindCriteriaPredicate predicate =
                new PersonMatchesFindCriteriaPredicate(Collections.singletonList("nomatch"),
                        Optional.empty(), Optional.empty());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonMatchesFindCriteriaPredicate predicate =
                new PersonMatchesFindCriteriaPredicate(Arrays.asList("Kurz", "Elle", "Kunz"),
                        Optional.empty(), Optional.empty());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_phoneNumber_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonMatchesFindCriteriaPredicate predicate =
                new PersonMatchesFindCriteriaPredicate(Collections.emptyList(),
                        Optional.of(new Phone("98765432")), Optional.empty());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndPhone_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("Benson"), Optional.of(new Phone("98765432")), Optional.empty());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_nric_onePatientFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.emptyList(), Optional.empty(), Optional.of(new NRIC(NADIA_NRIC)));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        Patient nadia = (Patient) model.getFilteredPersonList().get(0);
        assertEquals(1, model.getFilteredPersonList().size());
        assertEquals(new NRIC(NADIA_NRIC), nadia.getNric());
        assertEquals(NADIA_NAME, nadia.getName().fullName);
    }

    @Test
    public void execute_nameAndPhoneMismatch_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("Alice"), Optional.of(new Phone("9482427")), Optional.empty());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Arrays.asList("keyword"), Optional.of(new Phone("94351253")), Optional.of(new NRIC(NADIA_NRIC)));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
