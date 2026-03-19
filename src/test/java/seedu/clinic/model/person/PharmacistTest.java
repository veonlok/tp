package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PharmacistTest {

    @Test
    public void methods_executeWithoutErrors() {
        Pharmacist pharmacist = new Pharmacist(
                new Name("Pharmacist One"),
                new Phone("93456789"),
                new Email("pharmacist@example.com"),
                new Address("3 Clinic Road"),
                Set.of(),
                3);

        Patient patient = new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                2);
        Prescription prescription = new Prescription("Paracetamol", "500mg", "daily", 3);

        pharmacist.updatePrescription(prescription, prescription);
        pharmacist.dispensePrescription(prescription);
        pharmacist.viewPatientHistory(patient);

        assertNotNull(pharmacist.toString());
        assertEquals(3, pharmacist.getId());
    }
}
