package seedu.clinic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalPatients.NADIA_NRIC;
import static seedu.clinic.testutil.TypicalPatients.createNadia;
import static seedu.clinic.testutil.TypicalPersons.ALICE;
import static seedu.clinic.testutil.TypicalPersons.getTypicalClinicBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;
import seedu.clinic.model.person.Phone;
import seedu.clinic.model.person.exceptions.DuplicatePersonException;
import seedu.clinic.testutil.PersonBuilder;

public class ClinicBookTest {

    private final ClinicBook clinicBook = new ClinicBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicBook.getPersonList());
        assertEquals(Collections.emptyList(), clinicBook.getDoctorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinicBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyClinicBook_replacesData() {
        ClinicBook newData = getTypicalClinicBook();
        clinicBook.resetData(newData);
        assertEquals(newData, clinicBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ClinicBookStub newData = new ClinicBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> clinicBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> clinicBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInClinicBook_returnsFalse() {
        assertFalse(clinicBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInClinicBook_returnsTrue() {
        clinicBook.addPerson(ALICE);
        assertTrue(clinicBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInClinicBook_returnsTrue() {
        clinicBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(clinicBook.hasPerson(editedAlice));
    }

    @Test
    public void addPatient_defaultId_preservesPatientSubtypeAndAssignsId() {
        clinicBook.addPerson(createNadia());

        Person storedPerson = clinicBook.getPersonList().get(0);
        assertTrue(storedPerson instanceof Patient);
        assertTrue(storedPerson.getId() > 0);
        assertEquals(new NRIC(NADIA_NRIC), ((Patient) storedPerson).getNric());
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> clinicBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ClinicBook.class.getCanonicalName() + "{persons=" + clinicBook.getPersonList()
                + ", doctors=" + clinicBook.getDoctorList() + "}";
        assertEquals(expected, clinicBook.toString());
    }

    @Test
    public void addDiagnosis_updatesPatient() {
        Patient patient = new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                java.time.LocalDate.of(2000, 1, 1),
                "91112222",
                1);
        clinicBook.addPerson(patient);

        Diagnosis diagnosis = new Diagnosis("Flu", 2);
        clinicBook.addDiagnosis(patient, diagnosis);

        Patient updated = (Patient) clinicBook.getPersonList().get(0);
        assertEquals(1, updated.getDiagnoses().size());
    }

    /**
     * A stub ReadOnlyClinicBook whose persons list can violate interface constraints.
     */
    private static class ClinicBookStub implements ReadOnlyClinicBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();

        ClinicBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
            this.doctors.setAll(doctors);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Doctor> getDoctorList() {
            return doctors;
        }
    }

}
