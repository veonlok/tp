package seedu.clinic.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinic.testutil.PersonBuilder;

public class PersonMatchesFindCriteriaPredicateTest {

    @Test
    public void equals() {
        PersonMatchesFindCriteriaPredicate firstPredicate = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("first"), Optional.empty(), Optional.empty());
        PersonMatchesFindCriteriaPredicate secondPredicate = new PersonMatchesFindCriteriaPredicate(
                Arrays.asList("first", "second"), Optional.of(new Phone("12345678")),
                Optional.of(new NRIC("S1234567D")));
        PersonMatchesFindCriteriaPredicate sameNameDifferentPhone = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("first"), Optional.of(new Phone("12345678")), Optional.empty());
        PersonMatchesFindCriteriaPredicate differentNameSamePhone = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("first"), Optional.empty(), Optional.of(new NRIC("S1234567D")));

        assertTrue(firstPredicate.equals(firstPredicate));

        PersonMatchesFindCriteriaPredicate firstPredicateCopy = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("first"), Optional.empty(), Optional.empty());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(sameNameDifferentPhone));
        assertFalse(firstPredicate.equals(differentNameSamePhone));
    }

    @Test
    public void test_matchesNameKeywords_returnsTrue() {
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("Alice"), Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Arrays.asList("Alice", "Bob"),
                Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Arrays.asList("Bob", "Carol"),
                Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Arrays.asList("aLIce", "bOB"),
                Optional.empty(), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_matchesPhone_returnsTrue() {
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.emptyList(), Optional.of(new Phone("12345678")), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_matchesNameAndPhone_returnsTrue() {
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.singletonList("Alice"), Optional.of(new Phone("12345678")), Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("12345678").build()));
    }

    @Test
    public void test_matchesNric_returnsTrue() {
        Patient patient = new Patient(new PersonBuilder().withName("Alice Bob").withPhone("12345678").build(),
                new Address("123 Street"), new NRIC("S1234567D"), LocalDate.of(1992, 4, 12), "Amir Tan");
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.emptyList(), Optional.empty(), Optional.of(new NRIC("S1234567D")));
        assertTrue(predicate.test(patient));
    }

    @Test
    public void test_doesNotMatchCriteria_returnsFalse() {
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                Collections.emptyList(), Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Collections.singletonList("Carol"),
                Optional.empty(), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Collections.emptyList(),
                Optional.of(new Phone("12345678")), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Arrays.asList("Carol"),
                Optional.of(new Phone("12345678")), Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("87654321").build()));

        predicate = new PersonMatchesFindCriteriaPredicate(Collections.emptyList(), Optional.empty(),
                Optional.of(new NRIC("S1234567D")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonMatchesFindCriteriaPredicate predicate = new PersonMatchesFindCriteriaPredicate(
                keywords, Optional.of(new Phone("12345678")), Optional.of(new NRIC("S1234567D")));

        String expected = PersonMatchesFindCriteriaPredicate.class.getCanonicalName()
                + "{nameKeywords=" + keywords + ", phone=Optional[12345678], nric=Optional[S1234567D]}";
        assertEquals(expected, predicate.toString());
    }
}
