package seedu.clinic.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.commons.util.ToStringBuilder;

/**
 * Checks whether a {@code Person} matches the supplied find criteria.
 */
public class PersonMatchesFindCriteriaPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final Optional<Phone> phone;
    private final Optional<NRIC> nric;

    /**
     * Creates a predicate that matches persons by the supplied criteria.
     *
     * @param nameKeywords Name keywords to match against a person's name; may be empty if unused.
     * @param phone Phone number to match exactly; may be empty if unused.
     * @param nric Patient NRIC to match exactly; may be empty if unused.
     */
    public PersonMatchesFindCriteriaPredicate(List<String> nameKeywords, Optional<Phone> phone, Optional<NRIC> nric) {
        requireNonNull(nameKeywords);
        requireNonNull(phone);
        requireNonNull(nric);
        this.nameKeywords = List.copyOf(nameKeywords);
        this.phone = phone;
        this.nric = nric;
    }

    @Override
    public boolean test(Person person) {
        boolean hasCriteria = !nameKeywords.isEmpty() || phone.isPresent() || nric.isPresent();
        if (!hasCriteria) {
            return false;
        }

        boolean matchesName = nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean matchesPhone = phone.map(person.getPhone()::equals).orElse(true);
        boolean matchesNric = nric.map(value -> person instanceof Patient
                && ((Patient) person).getNric().equals(value)).orElse(true);
        return matchesName && matchesPhone && matchesNric;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesFindCriteriaPredicate)) {
            return false;
        }

        PersonMatchesFindCriteriaPredicate otherPredicate = (PersonMatchesFindCriteriaPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && phone.equals(otherPredicate.phone)
                && nric.equals(otherPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("phone", phone)
                .add("nric", nric)
                .toString();
    }
}
