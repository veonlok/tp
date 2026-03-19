package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Phone;

public class JsonAdaptedDoctorTest {

    @Test
    public void toModelType_validDoctor_returnsDoctor() throws IllegalValueException {
        JsonAdaptedDoctor adapted = new JsonAdaptedDoctor(1, "Doctor One", "91234567",
                "doctor@example.com", "1 Street", List.of());
        Doctor doctor = adapted.toModelType();

        assertEquals("Doctor One", doctor.getName().fullName);
        assertEquals(1, doctor.getId());
    }

    @Test
    public void constructorFromModel_copiesFields() throws IllegalValueException {
        Doctor doctor = new Doctor(new Name("Doctor One"), new Phone("91234567"),
                new Email("doctor@example.com"), new Address("1 Street"), java.util.Set.of(), 2);
        JsonAdaptedDoctor adapted = new JsonAdaptedDoctor(doctor);
        Doctor model = adapted.toModelType();
        assertEquals(doctor, model);
    }
}
