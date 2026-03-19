package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Pharmacist;
import seedu.clinic.model.person.Phone;

public class JsonAdaptedPharmacistTest {

    @Test
    public void toModelType_validPharmacist_returnsPharmacist() throws IllegalValueException {
        JsonAdaptedPharmacist adapted = new JsonAdaptedPharmacist(1, "Pharmacist One", "91234567",
                "pharmacist@example.com", "1 Street", List.of());
        Pharmacist pharmacist = adapted.toModelType();

        assertEquals("Pharmacist One", pharmacist.getName().fullName);
        assertEquals(1, pharmacist.getId());
    }

    @Test
    public void constructorFromModel_copiesFields() throws IllegalValueException {
        Pharmacist pharmacist = new Pharmacist(new Name("Pharmacist One"), new Phone("91234567"),
                new Email("pharmacist@example.com"), new Address("1 Street"), java.util.Set.of(), 2);
        JsonAdaptedPharmacist adapted = new JsonAdaptedPharmacist(pharmacist);
        Pharmacist model = adapted.toModelType();
        assertEquals(pharmacist, model);
    }
}
