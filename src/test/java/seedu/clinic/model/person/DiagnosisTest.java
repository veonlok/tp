package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class DiagnosisTest {

    private static final Doctor DEFAULT_DOCTOR = new Doctor(
            new Name("Doctor One"),
            new Phone("92345678"),
            new Email("doctor@example.com"));

    private static final Pharmacist DEFAULT_PHARMACIST = new Pharmacist(
            new Name("Pharmacist One"),
            new Phone("91234567"),
            new Email("pharmacist@example.com"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Diagnosis(null, DEFAULT_DOCTOR));
    }

    @Test
    public void constructor_invalidDiagnosis_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Diagnosis("", DEFAULT_DOCTOR));
    }

    @Test
    public void addAndRemoveMedicalDetails_success() {
        Diagnosis diagnosis = new Diagnosis("Influenza", LocalDate.of(2026, 3, 12), DEFAULT_DOCTOR);
        Prescription prescription = new Prescription("Paracetamol", "500mg", "Twice daily", DEFAULT_PHARMACIST);

        diagnosis.addSymptom("Fever");
        diagnosis.addPrescription(prescription);

        assertEquals(1, diagnosis.getSymptoms().size());
        assertEquals("Fever", diagnosis.getSymptoms().get(0));
        assertEquals(1, diagnosis.getPrescriptions().size());
        assertEquals(prescription, diagnosis.getPrescriptions().get(0));

        diagnosis.removeSymptom("Fever");
        diagnosis.removePrescription(prescription);

        assertTrue(diagnosis.getSymptoms().isEmpty());
        assertTrue(diagnosis.getPrescriptions().isEmpty());
    }

    @Test
    public void equals() {
        Diagnosis diagnosis = new Diagnosis("Influenza", LocalDate.of(2026, 3, 12), DEFAULT_DOCTOR);
        diagnosis.addSymptom("Fever");
        diagnosis.addPrescription(new Prescription("Paracetamol", "500mg", "Twice daily", DEFAULT_PHARMACIST));

        Diagnosis sameDiagnosis = new Diagnosis("Influenza", LocalDate.of(2026, 3, 12), DEFAULT_DOCTOR);
        sameDiagnosis.addSymptom("Fever");
        sameDiagnosis.addPrescription(new Prescription("Paracetamol", "500mg", "Twice daily", DEFAULT_PHARMACIST));

        assertTrue(diagnosis.equals(sameDiagnosis));
        assertTrue(diagnosis.equals(diagnosis));
        assertFalse(diagnosis.equals(null));
        assertFalse(diagnosis.equals(5.0f));
        assertFalse(diagnosis.equals(new Diagnosis("Cold", LocalDate.of(2026, 3, 12), DEFAULT_DOCTOR)));
    }
}
