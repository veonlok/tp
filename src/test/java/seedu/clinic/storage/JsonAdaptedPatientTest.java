package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.tag.Tag;

public class JsonAdaptedPatientTest {
    private static final int VALID_ID = 1;
    private static final String VALID_NAME = "Alice Patient";
    private static final String VALID_PHONE = "94351253";
    private static final String VALID_EMAIL = "alice@example.com";
    private static final String VALID_ADDRESS = "123, Jurong West Ave 6, #08-111";
    private static final List<JsonAdaptedTag> VALID_TAGS = List.of(new JsonAdaptedTag("friends"));
    private static final String VALID_NRIC = "S1166846A";
    private static final String VALID_DATE_OF_BIRTH = "2000-01-02";
    private static final String VALID_EMERGENCY_CONTACT = "Bob 98765432";

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        Diagnosis diagnosis = new Diagnosis("Flu", LocalDate.of(2024, 1, 2), 3);
        diagnosis.addSymptom("cough");

        JsonAdaptedPatient patient = new JsonAdaptedPatient(
                VALID_ID,
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                VALID_NRIC,
                VALID_DATE_OF_BIRTH,
                VALID_EMERGENCY_CONTACT,
                List.of(new JsonAdaptedDiagnosis(diagnosis)));

        Patient modelPatient = patient.toModelType();
        assertEquals(VALID_ID, modelPatient.getId());
        assertEquals(VALID_NAME, modelPatient.getName().fullName);
        assertEquals(VALID_PHONE, modelPatient.getPhone().value);
        assertEquals(VALID_EMAIL, modelPatient.getEmail().value);
        assertEquals(VALID_ADDRESS, modelPatient.getAddress().value);
        assertTrue(modelPatient.getTags().contains(new Tag("friends")));
        assertEquals(VALID_NRIC, modelPatient.getNric().value);
        assertEquals(LocalDate.parse(VALID_DATE_OF_BIRTH), modelPatient.getDateOfBirth());
        assertEquals(VALID_EMERGENCY_CONTACT, modelPatient.getEmergencyContact());
        assertEquals(1, modelPatient.getDiagnoses().size());
        assertEquals("Flu", modelPatient.getDiagnoses().get(0).getDescription());
        assertEquals(1, modelPatient.getDiagnoses().get(0).getSymptoms().size());
        assertEquals("cough", modelPatient.getDiagnoses().get(0).getSymptoms().get(0));
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(
                VALID_ID,
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                "INVALID",
                VALID_DATE_OF_BIRTH,
                VALID_EMERGENCY_CONTACT,
                List.of());
        assertThrows(IllegalValueException.class, NRIC.MESSAGE_CONSTRAINTS, patient::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(
                VALID_ID,
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null,
                VALID_DATE_OF_BIRTH,
                VALID_EMERGENCY_CONTACT,
                List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "NRIC");
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }
}
