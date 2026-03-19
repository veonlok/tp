package seedu.clinic.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.clinic.commons.core.GuiSettings;
import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;
import seedu.clinic.model.person.Pharmacist;

/**
 * Represents the in-memory model of clinic book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClinicBook clinicBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private Predicate<Doctor> filteredDoctorPredicate;

    /**
     * Initializes a ModelManager with the given clinicBook and userPrefs.
     */
    public ModelManager(ReadOnlyClinicBook clinicBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(clinicBook, userPrefs);

        logger.fine("Initializing with clinic book: " + clinicBook + " and user prefs " + userPrefs);

        this.clinicBook = new ClinicBook(clinicBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.clinicBook.getPersonList());
        filteredDoctorPredicate = PREDICATE_SHOW_ALL_DOCTORS;
    }

    public ModelManager() {
        this(new ClinicBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getClinicBookFilePath() {
        return userPrefs.getClinicBookFilePath();
    }

    @Override
    public void setClinicBookFilePath(Path clinicBookFilePath) {
        requireNonNull(clinicBookFilePath);
        userPrefs.setClinicBookFilePath(clinicBookFilePath);
    }

    //=========== ClinicBook ================================================================================

    @Override
    public void setClinicBook(ReadOnlyClinicBook clinicBook) {
        this.clinicBook.resetData(clinicBook);
    }

    @Override
    public ReadOnlyClinicBook getClinicBook() {
        return clinicBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return clinicBook.hasPerson(person);
    }

    @Override
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return getFilteredDoctorList().stream().anyMatch(doctor::isSamePerson);
    }

    @Override
    public void deletePerson(Person target) {
        clinicBook.removePerson(target);
    }

    @Override
    public void deleteDoctor(Doctor target) {
        clinicBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        clinicBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addDoctor(Doctor doctor) {
        clinicBook.addPerson(doctor);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        clinicBook.setPerson(target, editedPerson);
    }

    @Override
    public void setDoctor(Doctor target, Doctor editedDoctor) {
        requireAllNonNull(target, editedDoctor);
        clinicBook.setPerson(target, editedDoctor);
    }

    @Override
    public void addDiagnosis(Patient target, Diagnosis diagnosis) {
        clinicBook.addDiagnosis(target, diagnosis);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClinicBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return filteredPersons.stream()
                .filter(Doctor.class::isInstance)
                .map(Doctor.class::cast)
                .filter(filteredDoctorPredicate)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredDoctorList(Predicate<Doctor> predicate) {
        requireNonNull(predicate);
        filteredDoctorPredicate = predicate;
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPersons.filtered(Patient.class::isInstance)
                .stream()
                .map(Patient.class::cast)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<Pharmacist> getFilteredPharmacistList() {
        return filteredPersons.filtered(Pharmacist.class::isInstance)
                .stream()
                .map(Pharmacist.class::cast)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return clinicBook.equals(otherModelManager.clinicBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredDoctorPredicate.equals(otherModelManager.filteredDoctorPredicate);
    }
}
