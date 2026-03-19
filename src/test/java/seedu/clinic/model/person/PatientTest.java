package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PatientTest {

    @Test
    public void constructor_andGetters_success() {
        Patient patient = new Patient(
                new Name("Alice Patient"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                5);

        assertEquals("S1166846A", patient.getNric().value);
        assertEquals(LocalDate.of(2000, 1, 1), patient.getDateOfBirth());
        assertEquals("91112222", patient.getEmergencyContact());
    }

    @Test
    public void getAge_sameDayBirth_returnsExpectedYears() {
        LocalDate dob = LocalDate.now().minusYears(1);
        Patient patient = new Patient(
                new Name("Alice Patient"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                dob,
                "91112222",
                6);

        assertEquals(1, patient.getAge());
    }

    @Test
    public void addAndRemoveDiagnosis_success() {
        Patient patient = new Patient(
                new Name("Alice Patient"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                7);
        Diagnosis diagnosis = new Diagnosis("Flu", 2);

        patient.addDiagnosis(diagnosis);
        assertEquals(1, patient.getDiagnoses().size());

        patient.removeDiagnosis(diagnosis);
        assertTrue(patient.getDiagnoses().isEmpty());
    }

    @Test
    public void equalsAndHashCode() {
        Patient first = new Patient(
                new Name("Alice Patient"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                8);
        Patient second = new Patient(
                new Name("Alice Patient"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                8);

        assertTrue(first.equals(first));
        assertFalse(first.equals(null));
        assertFalse(first.equals(5));
        assertFalse(first.equals(second));
        assertNotEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void constructorFromPerson_setsId() {
        Person person = new Person(
                new Name("Base Person"),
                new Phone("91234567"),
                new Email("base@example.com"),
                new Address("1 Street"),
                Set.of(),
                10);

        Patient patient = new Patient(person, new Address("1 Street"),
                new NRIC("S1166846A"), LocalDate.of(2000, 1, 1), "91112222");
        assertEquals(10, patient.getId());
    }
}
