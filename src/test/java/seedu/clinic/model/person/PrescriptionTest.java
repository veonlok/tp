package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PrescriptionTest {

    private static final int DEFAULT_PHARMACIST_ID = 1;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Prescription(null, "500mg", "Daily", DEFAULT_PHARMACIST_ID));
    }

    @Test
    public void constructor_invalidField_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()
            -> new Prescription("Paracetamol", "", "Daily", DEFAULT_PHARMACIST_ID));
    }

    @Test
    public void equals() {
        Prescription prescription = new Prescription("Paracetamol", "500mg", "Daily", DEFAULT_PHARMACIST_ID);

        assertTrue(prescription.equals(new Prescription("Paracetamol", "500mg", "Daily", DEFAULT_PHARMACIST_ID)));
        assertTrue(prescription.equals(prescription));
        assertFalse(prescription.equals(null));
        assertFalse(prescription.equals(5.0f));
        assertFalse(prescription.equals(new Prescription("Ibuprofen", "200mg", "Daily", DEFAULT_PHARMACIST_ID)));
    }
}
