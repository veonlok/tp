package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // TODO: Remove all dependencies to this class, make class abstract
    // Remove address field
    // default id for patient who has yet been assigned with an ID
    private static final int DEFAULT_ID = 0;
    // will be used later
    private static final String ID_FORMAT = "P%03d";

    // Identity fields
    private static final NRIC DEFAULT_NRIC = new NRIC("S0000000J");

    private final Name name;
    private final NRIC nric;
    private final Phone phone;
    private final Email email;
    private int id;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();


    /**
     * Every field must be present and not null.
     * ID will be assigned by AddressBook
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, int id) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.id = id;
    }

    /**
     * constructor for Person without ID assignment
     * ID will then be assigned by the AddressBook
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, DEFAULT_NRIC, phone, email, address, tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, NRIC nric, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, nric, phone, email, address, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.id = DEFAULT_ID;
    }


    public Name getName() {
        return name;
    }

    public NRIC getNric() {
        return nric;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
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
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
