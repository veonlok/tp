package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalPersons.ALICE;
import static seedu.clinic.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.NameContainsKeywordsPredicate;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Pharmacist;
import seedu.clinic.model.person.Phone;
import seedu.clinic.testutil.ClinicBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ClinicBook(), new ClinicBook(modelManager.getClinicBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setClinicBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setClinicBookFilePath(Paths.get("new/address/book/file/path"));
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
    public void setClinicBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClinicBookFilePath(null));
    }

    @Test
    public void setClinicBookFilePath_validPath_setsClinicBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setClinicBookFilePath(path);
        assertEquals(path, modelManager.getClinicBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInClinicBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInClinicBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void addDiagnosis_andFilteredRoleLists_success() {
        ClinicBook clinicBook = new ClinicBook();
        Patient patient = new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                java.util.Set.of(),
                new NRIC("S1166846A"),
                java.time.LocalDate.of(2000, 1, 1),
                "91112222",
                1);
        Doctor doctor = new Doctor(
                new Name("Doctor One"),
                new Phone("92345678"),
                new Email("doctor@example.com"),
                new Address("2 Street"),
                java.util.Set.of(),
                2);
        Pharmacist pharmacist = new Pharmacist(
                new Name("Pharmacist One"),
                new Phone("93456789"),
                new Email("pharmacist@example.com"),
                new Address("3 Street"),
                java.util.Set.of(),
                3);

        clinicBook.addPerson(patient);
        clinicBook.addPerson(doctor);
        clinicBook.addPerson(pharmacist);

        modelManager = new ModelManager(clinicBook, new UserPrefs());

        Diagnosis diagnosis = new Diagnosis("Flu", 2);
        modelManager.addDiagnosis(patient, diagnosis);

        assertEquals(1, modelManager.getFilteredPatientList().size());
        assertEquals(1, modelManager.getFilteredDoctorList().size());
        assertEquals(1, modelManager.getFilteredPharmacistList().size());
        assertEquals(1, modelManager.getFilteredPatientList().get(0).getDiagnoses().size());
    }

    @Test
    public void equals() {
        ClinicBook clinicBook = new ClinicBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ClinicBook differentClinicBook = new ClinicBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(clinicBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(clinicBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different clinicBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentClinicBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(clinicBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClinicBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(clinicBook, differentUserPrefs)));
    }
}
