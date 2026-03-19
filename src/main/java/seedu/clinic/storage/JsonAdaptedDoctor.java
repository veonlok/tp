package seedu.clinic.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Person;

/**
 * Jackson-friendly version of {@link Doctor}.
 */
class JsonAdaptedDoctor extends JsonAdaptedPerson {

    /**
     * Constructs a {@code JsonAdaptedDoctor} with the given doctor details.
     */
    @JsonCreator
    public JsonAdaptedDoctor(@JsonProperty("id") int id,
                             @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(id, name, phone, email, tags);
    }

    /**
     * Converts a given {@code Doctor} into this class for Jackson use.
     */
    public JsonAdaptedDoctor(Doctor source) {
        super(source);
    }

    /**
     * Converts this Jackson-friendly adapted doctor object into the model's {@code Doctor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Doctor toModelType() throws IllegalValueException {
        Person person = super.toModelType();
        return new Doctor(person.getName(), person.getPhone(), person.getEmail(), person.getId());
    }
}
