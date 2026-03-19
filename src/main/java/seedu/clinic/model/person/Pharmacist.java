package seedu.clinic.model.person;

import java.util.Set;

import seedu.clinic.commons.util.ToStringBuilder;
import seedu.clinic.model.tag.Tag;

/**
 * Represents a Pharmacist in the clinic.
 * A Pharmacist is a Person who dispenses and manages prescriptions.
 *
 * TODO: Implement prescription dispensing logic
 * TODO: Implement prescription management logic
 * TODO: Add patient history retrieval functionality
 */
public class Pharmacist extends Staff {

    /**
     * Constructs a Pharmacist with the given details.
     * Every field must be present and not null.
     */
    public Pharmacist(Name name, Phone phone, Email email) {
        super(name, phone, email);
    }

    /**
     * Constructs a Pharmacist with the given details including ID.
     */
    public Pharmacist(Name name, Phone phone, Email email, int id) {
        super(name, phone, email, id);
    }

    /**
     * Compatibility constructor for callers still passing legacy address and tag data.
     */
    public Pharmacist(Name name, Phone phone, Email email, Address address, Set<Tag> tags, int id) {
        this(name, phone, email, id);
    }

    /**
     * Updates a prescription.
     */
    public void updatePrescription(Prescription oldPrescription, Prescription newPrescription) {
        // TODO: Implement prescription update logic
        // TODO: Update prescription in system
        // TODO: Track prescription modifications
    }

    /**
     * Mark a prescription as dispensed.
     */
    public void dispensePrescription(Prescription prescription) {
        // TODO: Implement prescription dispensing logic
        // TODO: Mark prescription as fulfilled
        // TODO: Update patient medication records
        // TODO: Generate dispensing records
    }

    /**
     * Views the medical history of a patient.
     */
    public void viewPatientHistory(Patient patient) {
        // TODO: Implement patient history retrieval for pharmacist
        // TODO: Fetch patient's active prescriptions
        // TODO: Fetch patient's allergies
        // TODO: Fetch medication history
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("id", getId())
                .add("phone", getPhone())
                .add("email", getEmail())
                .toString();
    }
}
