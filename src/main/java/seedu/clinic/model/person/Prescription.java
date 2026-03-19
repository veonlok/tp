package seedu.clinic.model.person;

import static seedu.clinic.commons.util.AppUtil.checkArgument;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.clinic.commons.util.ToStringBuilder;

/**
 * Represents a prescription for medication.
 * TODO: Add dispensePrescription() to update prescription status
 * TODO: Implement dispensePrescription() to mark fulfilled prescriptions
 * TODO: Implement getSummary() method for prescription slip
 */
public class Prescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Prescription fields should not be blank";
    private static final String VALIDATION_REGEX = "[^\\s].*";

    private final String medicationName;
    private final String dosage;
    private final String frequency;
    private final int prescribedBy; // NOT USED
    private final int dispensedBy;
    private final boolean isDispensed;

    /**
     * Constructs a {@code Prescription}.
     */
    public Prescription(String medicationName, String dosage, String frequency, int dispensedBy) {
        this(medicationName, dosage, frequency, 0, dispensedBy);
    }

    /**
     * Constructs a {@code Prescription}.
     */
    public Prescription(String medicationName, String dosage, String frequency,
            int prescribedBy, int dispensedBy) {
        requireAllNonNull(medicationName, dosage, frequency, dispensedBy);
        checkArgument(isValidField(medicationName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidField(dosage), MESSAGE_CONSTRAINTS);
        checkArgument(isValidField(frequency), MESSAGE_CONSTRAINTS);
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.prescribedBy = prescribedBy;
        this.dispensedBy = dispensedBy;
        this.isDispensed = false;
    }

    /**
     * Returns true if a given string is a valid prescription field value.
     */
    public static boolean isValidField(String value) {
        requireAllNonNull(value);
        return value.matches(VALIDATION_REGEX);
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    /**
     * Returns the id of the doctor who prescribed this prescription, if available.
     */
    public int getPrescribedBy() {
        return prescribedBy;
    }

    /**
     * Returns the id of the pharmacist who dispensed this prescription.
     */
    public int getDispensedBy() {
        return dispensedBy;
    }

    /**
     * Returns whether this prescription has been dispensed.
     */
    public boolean isDispensed() {
        return isDispensed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("medicationName", medicationName)
                .add("dosage", dosage)
                .add("frequency", frequency)
                .add("prescribedBy", prescribedBy)
                .add("dispensedBy", dispensedBy)
                .add("isDispensed", isDispensed)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Prescription)) {
            return false;
        }

        Prescription otherPrescription = (Prescription) other;
        return medicationName.equals(otherPrescription.medicationName)
                && dosage.equals(otherPrescription.dosage)
                && frequency.equals(otherPrescription.frequency)
                && prescribedBy == otherPrescription.prescribedBy
                && dispensedBy == otherPrescription.dispensedBy
                && isDispensed == otherPrescription.isDispensed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationName, dosage, frequency, prescribedBy, dispensedBy, isDispensed);
    }
}
