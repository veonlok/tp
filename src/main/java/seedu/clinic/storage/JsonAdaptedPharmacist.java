package seedu.clinic.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Person;
import seedu.clinic.model.person.Pharmacist;

/**
 * Jackson-friendly version of {@link Pharmacist}.
 */
class JsonAdaptedPharmacist extends JsonAdaptedPerson {

    /**
     * Constructs a {@code JsonAdaptedPharmacist} with the given pharmacist details.
     */
    @JsonCreator
    public JsonAdaptedPharmacist(@JsonProperty("id") int id,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("phone") String phone,
                                 @JsonProperty("email") String email,
                                 @JsonProperty("address") String address,
                                 @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(id, name, phone, email, tags);
    }

    /**
     * Converts a given {@code Pharmacist} into this class for Jackson use.
     */
    public JsonAdaptedPharmacist(Pharmacist source) {
        super(source);
    }

    /**
     * Converts this Jackson-friendly adapted pharmacist object into the model's {@code Pharmacist} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Pharmacist toModelType() throws IllegalValueException {
        Person person = super.toModelType();
        return new Pharmacist(person.getName(), person.getPhone(), person.getEmail(), person.getId());
    }
}
