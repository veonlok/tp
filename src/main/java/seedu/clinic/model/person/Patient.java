package seedu.clinic.model.person;

import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.model.tag.Tag;

/**
 * Represents a Patient in the clinic.
 * A Patient is a Person who receives medical services.
 *
 * TODO: Replace emergencyContact string with EmergencyContact objects collection
 * TODO: Add Sex enum field for biological sex
 * TODO: Use {@code Set<EmergencyContact>} instead of String for emergency contacts
 * TODO: Implement allergies management
 * TODO: Create patientId field
 * TODO: Extract address to separate structure
 */
public class Patient extends ContactPerson {

    private static final int DEFAULT_PATIENT_ID = 0;
    private static int nextPatientId = DEFAULT_PATIENT_ID + 1;

    private final int patientId;
    private final Address address;
    private final NRIC nric;
    private final LocalDate dateOfBirth;
    private final String emergencyContact;
    private final List<Diagnosis> diagnoses = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            NRIC nric, LocalDate dateOfBirth, String emergencyContact) {
        super(name, phone, email, tags);
        requireAllNonNull(address, nric, dateOfBirth, emergencyContact);
        this.patientId = getNextPatientId();
        this.address = address;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContact = emergencyContact;
    }

    /**
     * Constructs a Patient with ID.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
            NRIC nric, LocalDate dateOfBirth, String emergencyContact, int id) {
        super(name, phone, email, tags, id);
        requireAllNonNull(address, nric, dateOfBirth, emergencyContact);
        this.patientId = getNextPatientId();
        this.address = address;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContact = emergencyContact;
    }

    /**
     * Reuses an existing person as the shared identity and contact details for a patient.
     */
    public Patient(Person person, Address address, NRIC nric, LocalDate dob, String emergencyContact) {
        this(person.getName(), person.getPhone(), person.getEmail(), address, person.getTags(),
                nric, dob, emergencyContact, person.getId());
    }

    private static int getNextPatientId() {
        return nextPatientId++;
    }

    public NRIC getNric() {
        return nric;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the auto-generated patient identifier.
     */
    public int getPatientId() {
        return patientId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns the age of the patient in years.
     */
    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * Adds a diagnosis to the patient's diagnosis list.
     */
    public void addDiagnosis(Diagnosis diagnosis) {
        requireAllNonNull(diagnosis);
        diagnoses.add(diagnosis);
    }

    /**
     * Removes a diagnosis from the patient's diagnosis list.
     */
    public void removeDiagnosis(Diagnosis diagnosis) {
        requireAllNonNull(diagnosis);
        diagnoses.remove(diagnosis);
    }

    public List<Diagnosis> getDiagnoses() {
        return Collections.unmodifiableList(diagnoses);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return super.equals(otherPatient)
                && patientId == otherPatient.patientId
                && address.equals(otherPatient.address)
                && nric.equals(otherPatient.nric)
                && dateOfBirth.equals(otherPatient.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), patientId, address, nric, dateOfBirth);
    }
}
