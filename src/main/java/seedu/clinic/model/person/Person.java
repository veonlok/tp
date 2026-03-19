package seedu.clinic.model.person;

import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.clinic.commons.util.ToStringBuilder;
import seedu.clinic.model.tag.Tag;

/**
 * Represents a Person in clinic book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 *
 * TODO: Make `Person` class abstract
 * TODO: Remove Address field and move to Patient subclass
 * TODO: Remove Tags field from Person
 * TODO: Simplify constructor to only require Name
 * TODO: Remove ID management from Person - implement in Staff/Patient subclasses
 * TODO: Implement automatic ID generation and formatting using ID_FORMAT
 */
public class Person {

    // May change to Person
    public static final String ROLE = "Patient";
    // TODO: Move this to Staff/Patient subclasses
    private static final int DEFAULT_ID = 0;
    // TODO: Implement ID_FORMAT usage in automatic ID assignment (e.g., P001, P002)
    // This format will be used when generating IDs from DEFAULT_ID or similar constants
    private static final String ID_FORMAT = "P%03d";

    private final Name name;
    private final Phone phone;
    private final Email email;
    private int id;

    // Data fields
    // TODO: Remove Tags
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     * ID will be assigned by ClinicBook
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags, int id) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.id = id;
    }

    /**
     * Constructor for Person with automatic ID assignment.
     */
    public Person(Name name, Phone phone, Email email, Set<Tag> tags) {
        this(name, phone, email, tags, DEFAULT_ID);
    }

    public String getRole() {
        return ROLE;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getId() == getId()
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;

        return id == otherPerson.id
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, phone, email, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .toString();
    }

}
