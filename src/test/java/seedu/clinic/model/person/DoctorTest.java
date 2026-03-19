package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class DoctorTest {

    @Test
    public void methods_executeWithoutErrors() {
        Doctor doctor = new Doctor(
                new Name("Doctor One"),
                new Phone("92345678"),
                new Email("doctor@example.com"),
                new Address("2 Clinic Road"),
                Set.of(),
                1);

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
        Diagnosis diagnosis = new Diagnosis("Flu", 1);
        Prescription prescription = new Prescription("Paracetamol", "500mg", "daily", 3);

        doctor.diagnose(patient, diagnosis);
        doctor.updateDiagnosis(diagnosis, diagnosis);
        doctor.prescribe(patient, prescription);
        doctor.updatePrescription(prescription, prescription);
        doctor.viewPatientHistory(patient);

        assertNotNull(doctor.toString());
        assertEquals(1, doctor.getId());
    }
}
