package seedu.clinic.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Prescription;

/**
 * Jackson-friendly version of {@link Prescription}.
 */
class JsonAdaptedPrescription {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Prescription's %s field is missing!";

    private final String medicationName;
    private final String dosage;
    private final String frequency;
    private final int dispensedBy;

    /**
     * Constructs a {@code JsonAdaptedPrescription} with the given prescription details.
     */
    @JsonCreator
    public JsonAdaptedPrescription(@JsonProperty("medicationName") String medicationName,
                                   @JsonProperty("dosage") String dosage,
                                   @JsonProperty("frequency") String frequency,
                                   @JsonProperty("dispensedBy") int dispensedBy) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.dispensedBy = dispensedBy;
    }

    /**
     * Converts a given {@code Prescription} into this class for Jackson use.
     */
    public JsonAdaptedPrescription(Prescription source) {
        medicationName = source.getMedicationName();
        dosage = source.getDosage();
        frequency = source.getFrequency();
        dispensedBy = source.getDispensedBy();
    }

    /**
     * Converts this Jackson-friendly adapted prescription object into the model's {@code Prescription} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Prescription toModelType() throws IllegalValueException {
        if (medicationName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "medicationName"));
        }
        if (!Prescription.isValidField(medicationName)) {
            throw new IllegalValueException(Prescription.MESSAGE_CONSTRAINTS);
        }
        if (dosage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "dosage"));
        }
        if (!Prescription.isValidField(dosage)) {
            throw new IllegalValueException(Prescription.MESSAGE_CONSTRAINTS);
        }
        if (frequency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "frequency"));
        }
        if (!Prescription.isValidField(frequency)) {
            throw new IllegalValueException(Prescription.MESSAGE_CONSTRAINTS);
        }
        if (dispensedBy == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "dispensedBy"));
        }

        return new Prescription(medicationName, dosage, frequency, dispensedBy);
    }
}
