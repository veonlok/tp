package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.ClinicBook;
import seedu.clinic.model.Model;
import seedu.clinic.model.ReadOnlyClinicBook;
import seedu.clinic.model.ReadOnlyUserPrefs;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Person;
import seedu.clinic.model.person.Pharmacist;
import seedu.clinic.model.person.Phone;

public class AddPharmacistCommandTest {

    @Test
    public void constructor_nullPharmacist_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPharmacistCommand(null));
    }

    @Test
    public void execute_pharmacistAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPharmacistAdded modelStub = new ModelStubAcceptingPharmacistAdded();
        Pharmacist pharmacist = createPharmacist("Donald Trump");

        CommandResult commandResult = new AddPharmacistCommand(pharmacist).execute(modelStub);

        assertEquals(String.format(AddPharmacistCommand.MESSAGE_SUCCESS, pharmacist), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(pharmacist), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePharmacist_throwsCommandException() {
        Pharmacist pharmacist = createPharmacist("Donald Trump");
        AddPharmacistCommand command = new AddPharmacistCommand(pharmacist);
        ModelStub modelStub = new ModelStubWithPerson(pharmacist);

        assertThrows(CommandException.class, AddPharmacistCommand.MESSAGE_DUPLICATE_PHARMACIST,
                () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Pharmacist first = createPharmacist("Donald Trump");
        Pharmacist second = createPharmacist("Ron Weasley");

        AddPharmacistCommand firstCommand = new AddPharmacistCommand(first);
        AddPharmacistCommand secondCommand = new AddPharmacistCommand(second);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new AddPharmacistCommand(first)));

        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    private Pharmacist createPharmacist(String name) {
        return new Pharmacist(new Name(name), new Phone("98765432"), new Email("test@example.com"));
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getClinicBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicBookFilePath(Path clinicBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicBook(ReadOnlyClinicBook clinicBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinicBook getClinicBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDoctor(seedu.clinic.model.person.Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDoctor(seedu.clinic.model.person.Doctor target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDoctor(seedu.clinic.model.person.Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDoctor(seedu.clinic.model.person.Doctor target, seedu.clinic.model.person.Doctor editedDoctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<seedu.clinic.model.person.Doctor> getFilteredDoctorList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDoctorList(Predicate<seedu.clinic.model.person.Doctor> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithPerson extends ModelStub {
        private final Person person;
        private final ClinicBook clinicBook;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
            clinicBook = new ClinicBook();
            clinicBook.addPerson(person);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public ReadOnlyClinicBook getClinicBook() {
            return clinicBook;
        }
    }

    private class ModelStubAcceptingPharmacistAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyClinicBook getClinicBook() {
            ClinicBook clinicBook = new ClinicBook();
            personsAdded.forEach(clinicBook::addPerson);
            return clinicBook;
        }
    }
}
