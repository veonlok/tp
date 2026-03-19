package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.storage.JsonAdaptedPrescription.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Prescription;

public class JsonAdaptedPrescriptionTest {

    @Test
    public void toModelType_validPrescription_returnsPrescription() throws Exception {
        Prescription prescription = new Prescription("Paracetamol", "500mg", "daily", 3);
        JsonAdaptedPrescription adapted = new JsonAdaptedPrescription(prescription);

        Prescription model = adapted.toModelType();
        assertEquals("Paracetamol", model.getMedicationName());
        assertEquals("500mg", model.getDosage());
        assertEquals("daily", model.getFrequency());
        assertEquals(3, model.getDispensedBy());
    }

    @Test
    public void toModelType_nullMedication_throwsIllegalValueException() {
        JsonAdaptedPrescription adapted = new JsonAdaptedPrescription(null, "500mg", "daily", 3);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "medicationName");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullDosage_throwsIllegalValueException() {
        JsonAdaptedPrescription adapted = new JsonAdaptedPrescription("Paracetamol", null, "daily", 3);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "dosage");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullFrequency_throwsIllegalValueException() {
        JsonAdaptedPrescription adapted = new JsonAdaptedPrescription("Paracetamol", "500mg", null, 3);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "frequency");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_missingDispensedBy_throwsIllegalValueException() {
        JsonAdaptedPrescription adapted = new JsonAdaptedPrescription("Paracetamol", "500mg", "daily", 0);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "dispensedBy");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }
}
