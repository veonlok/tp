package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.commons.util.JsonUtil;
import seedu.clinic.model.ClinicBook;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;
import seedu.clinic.testutil.TypicalPersons;

public class JsonSerializableClinicBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableClinicBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsClinicBook.json");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsClinicBook.json");
    private static final Path CLINIC_BOOK_WITH_PATIENT_FILE = TEST_DATA_FOLDER.resolve("clinicBookWithPatient.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonClinicBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonClinicBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableClinicBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableClinicBook.class).get();
        ClinicBook clinicBookFromFile = dataFromFile.toModelType();
        ClinicBook typicalPersonsClinicBook = TypicalPersons.getTypicalClinicBook();
        assertEquals(clinicBookFromFile, typicalPersonsClinicBook);
    }

    @Test
    public void toModelType_patientFile_success() throws Exception {
        JsonSerializableClinicBook dataFromFile = JsonUtil.readJsonFile(CLINIC_BOOK_WITH_PATIENT_FILE,
                JsonSerializableClinicBook.class).get();
        ClinicBook clinicBookFromFile = dataFromFile.toModelType();

        assertEquals(2, clinicBookFromFile.getPersonList().size());

        Person firstPerson = clinicBookFromFile.getPersonList().get(0);
        assertTrue(firstPerson instanceof Patient);

        Patient patient = (Patient) firstPerson;
        assertEquals("S1166846A", patient.getNric().value);
        assertEquals("2000-01-02", patient.getDateOfBirth().toString());
        assertEquals("Bob 98765432", patient.getEmergencyContact());
        assertEquals(1, patient.getDiagnoses().size());
        assertEquals("Flu", patient.getDiagnoses().get(0).getDescription());
        assertEquals(1, patient.getDiagnoses().get(0).getSymptoms().size());
        assertEquals("cough", patient.getDiagnoses().get(0).getSymptoms().get(0));
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableClinicBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableClinicBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableClinicBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableClinicBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableClinicBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
