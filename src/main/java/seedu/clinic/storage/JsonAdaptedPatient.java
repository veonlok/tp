package seedu.clinic.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;

/**
 * Jackson-friendly version of {@link Patient}.
 */
class JsonAdaptedPatient extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    private final String address;
    private final String nric;
    private final String dateOfBirth;
    private final String emergencyContact;
    private final List<JsonAdaptedDiagnosis> diagnoses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("id") int id,
                              @JsonProperty("name") String name,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email,
                              @JsonProperty("address") String address,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("nric") String nric,
                              @JsonProperty("dateOfBirth") String dateOfBirth,
                              @JsonProperty("emergencyContact") String emergencyContact,
                              @JsonProperty("diagnoses") List<JsonAdaptedDiagnosis> diagnoses) {
        super(id, name, phone, email, tags);
        this.address = address;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.emergencyContact = emergencyContact;
        if (diagnoses != null) {
            this.diagnoses.addAll(diagnoses);
        }
    }

    /**
     * Converts a given {@code Patient} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {
        super(source);
        address = source.getAddress().value;
        nric = source.getNric().value;
        dateOfBirth = source.getDateOfBirth().toString();
        emergencyContact = source.getEmergencyContact();
        diagnoses.addAll(source.getDiagnoses().stream()
                .map(JsonAdaptedDiagnosis::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted patient object into the model's {@code Patient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Patient toModelType() throws IllegalValueException {
        Person person = super.toModelType();

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "NRIC"));
        }
        if (!NRIC.isValidNric(nric)) {
            throw new IllegalValueException(NRIC.MESSAGE_CONSTRAINTS);
        }
        final NRIC modelNric = new NRIC(nric);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "dateOfBirth"));
        }
        final LocalDate modelDob = LocalDate.parse(dateOfBirth);

        if (emergencyContact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "emergencyContact"));
        }

        final List<Diagnosis> modelDiagnoses = new ArrayList<>();
        for (JsonAdaptedDiagnosis diagnosis : diagnoses) {
            modelDiagnoses.add(diagnosis.toModelType());
        }

        Patient patient = new Patient(person.getName(), person.getPhone(), person.getEmail(), modelAddress,
                person.getTags(), modelNric, modelDob, emergencyContact, person.getId());
        modelDiagnoses.forEach(patient::addDiagnosis);
        return patient;
    }
}
