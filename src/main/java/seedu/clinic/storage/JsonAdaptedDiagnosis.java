package seedu.clinic.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Prescription;

/**
 * Jackson-friendly version of {@link Diagnosis}.
 */
class JsonAdaptedDiagnosis {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diagnosis's %s field is missing!";

    private final String description;
    private final String visitDate;
    private final int diagnosedBy;
    private final List<String> symptoms = new ArrayList<>();
    private final List<JsonAdaptedPrescription> prescriptions = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDiagnosis} with the given diagnosis details.
     */
    @JsonCreator
    public JsonAdaptedDiagnosis(@JsonProperty("description") String description,
                                @JsonProperty("visitDate") String visitDate,
                                @JsonProperty("diagnosedBy") int diagnosedBy,
                                @JsonProperty("symptoms") List<String> symptoms,
                                @JsonProperty("prescriptions") List<JsonAdaptedPrescription> prescriptions) {
        this.description = description;
        this.visitDate = visitDate;
        this.diagnosedBy = diagnosedBy;

        if (symptoms != null) {
            this.symptoms.addAll(symptoms);
        }
        if (prescriptions != null) {
            this.prescriptions.addAll(prescriptions);
        }
    }

    /**
     * Converts a given {@code Diagnosis} into this class for Jackson use.
     */
    public JsonAdaptedDiagnosis(Diagnosis source) {
        description = source.getDescription();
        visitDate = source.getVisitDate().toString();
        diagnosedBy = source.getDiagnosedBy();
        symptoms.addAll(source.getSymptoms());
        for (Prescription prescription : source.getPrescriptions()) {
            prescriptions.add(new JsonAdaptedPrescription(prescription));
        }
    }

    /**
     * Converts this Jackson-friendly adapted diagnosis object into the model's {@code Diagnosis} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Diagnosis toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }
        if (!Diagnosis.isValidDiagnosis(description)) {
            throw new IllegalValueException(Diagnosis.MESSAGE_CONSTRAINTS);
        }
        if (visitDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "visitDate"));
        }
        if (diagnosedBy == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "diagnosedBy"));
        }

        Diagnosis modelDiagnosis = new Diagnosis(description, LocalDate.parse(visitDate), diagnosedBy);
        for (String symptom : symptoms) {
            modelDiagnosis.addSymptom(symptom);
        }
        for (JsonAdaptedPrescription prescription : prescriptions) {
            modelDiagnosis.addPrescription(prescription.toModelType());
        }
        return modelDiagnosis;
    }
}
