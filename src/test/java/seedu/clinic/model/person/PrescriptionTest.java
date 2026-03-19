package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PrescriptionTest {

    private static final Pharmacist DEFAULT_PHARMACIST = new Pharmacist(
            new Name("Pharmacist One"),
            new Phone("91234567"),
            new Email("pharmacist@example.com"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Prescription(null, "500mg", "Daily", DEFAULT_PHARMACIST));
    }

    @Test
    public void constructor_invalidField_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()
            -> new Prescription("Paracetamol", "", "Daily", DEFAULT_PHARMACIST));
    }

    @Test
    public void equals() {
        Prescription prescription = new Prescription("Paracetamol", "500mg", "Daily", DEFAULT_PHARMACIST);

        assertTrue(prescription.equals(new Prescription("Paracetamol", "500mg", "Daily", DEFAULT_PHARMACIST)));
        assertTrue(prescription.equals(prescription));
        assertFalse(prescription.equals(null));
        assertFalse(prescription.equals(5.0f));
        assertFalse(prescription.equals(new Prescription("Ibuprofen", "200mg", "Daily", DEFAULT_PHARMACIST)));
    }
}
