package seedu.clinic.model.person;

import java.util.Collections;
import java.util.Set;

import seedu.clinic.commons.util.ToStringBuilder;
import seedu.clinic.model.tag.Tag;

/**
 * Represents a contact person in the clinic.
 * A ContactPerson is a basic person with contact information.
 *
 * TODO: NOT INTEGRATED
 */
public class ContactPerson extends Person {
    /**
     * Constructs a ContactPerson with all fields.
     */
    public ContactPerson(Name name, Phone phone, Email email) {
        super(name, phone, email, Collections.emptySet());
    }

    /**
     * Constructs a ContactPerson with tags.
     */
    public ContactPerson(Name name, Phone phone, Email email, Set<Tag> tags) {
        super(name, phone, email, tags);
    }

    /**
     * Constructs a ContactPerson with tags and ID.
     */
    public ContactPerson(Name name, Phone phone, Email email, Set<Tag> tags, int id) {
        super(name, phone, email, tags, id);
    }

    /**
     * Returns true if both contact persons are the same person.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        return super.isSamePerson(otherPerson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .toString();
    }
}
