package seedu.clinic.model.person;

import seedu.clinic.commons.util.ToStringBuilder;

/**
 * Represents a Doctor in the clinic.
 * A Doctor is a Person who can diagnose patients and prescribe treatments.
 *
 * TODO: Make Doctor extend Staff instead of Person once Staff hierarchy is implemented
 * TODO: Implement  diagnosis recording logic
 * TODO: Implement  prescription management logic
 * TODO: Add patient history retrieval functionality
 */
public class Doctor extends Staff {

    //private final Set<Tag> tags = new HashSet<>();
    public static final String ROLE = "Doctor";


    /*
    public Doctor(Name name, Phone phone, Email email, Set<Tag> tags) {
        super(name, phone, email);
        this.tags.addAll(tags);
    }
     */

    /**
     * Constructs a Doctor with the given details.
     * Every field must be present and not null.
     */
    public Doctor(Name name, Phone phone, Email email) {
        super(name, phone, email);
    }

    /**
     * Constructs a Doctor with the given details including ID.
     */
    public Doctor(Name name, Phone phone, Email email, int id) {
        super(name, phone, email, id);
    }


    public String getRole() {
        return ROLE;
    }

    /**
     * Records a diagnosis for a patient.
     */
    public void diagnose(Patient patient, Diagnosis diagnosis) {
        // TODO: Implement diagnosis recording logic
        // TODO: Persist diagnosis to patient record
        // TODO: Validate doctor permissions to diagnose
    }

    /**
     * Updates an existing diagnosis.
     */
    public void updateDiagnosis(Diagnosis oldDiagnosis, Diagnosis newDiagnosis) {
        // TODO: Implement diagnosis update logic
        // TODO: Update diagnosis in patient records
        // TODO: Track diagnosis history
    }

    /**
     * Prescribes a treatment to a patient.
     */
    public void prescribe(Patient patient, Prescription prescription) {
        // TODO: Implement prescription logic
        // TODO: Link prescription to diagnosis
        // TODO: Notify pharmacist of new prescription
    }

    /**
     * Updates an existing prescription.
     */
    public void updatePrescription(Prescription oldPrescription, Prescription newPrescription) {
        // TODO: Implement prescription update logic
        // TODO: Update prescription in system
        // TODO: Notify relevant pharmacist
    }

    /**
     * Views the medical history of a patient.
     */
    public void viewPatientHistory(Patient patient) {
        // TODO: Implement patient history retrieval
        // TODO: Fetch all diagnoses for patient
        // TODO: Fetch all prescriptions for patient
        // TODO: Fetch patient allergies and medical notes
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("id", getId())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("tags", getTags())
                .toString();
    }
}
