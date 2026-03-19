package seedu.clinic.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.clinic.commons.util.ToStringBuilder;

/**
 * Tests if a {@code Person} is a {@code Patient} with a matching NRIC.
 */
public class PatientHasNricPredicate implements Predicate<Person> {
    private final String nric;

    /**
     * Creates a predicate that matches patients by NRIC.
     */
    public PatientHasNricPredicate(String nric) {
        requireNonNull(nric);
        this.nric = nric;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) person;
        return patient.getNric().value.equalsIgnoreCase(nric);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PatientHasNricPredicate)) {
            return false;
        }

        PatientHasNricPredicate otherPredicate = (PatientHasNricPredicate) other;
        return nric.equals(otherPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", nric)
                .toString();
    }
}
