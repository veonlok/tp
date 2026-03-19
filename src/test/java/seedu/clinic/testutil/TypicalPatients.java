package seedu.clinic.testutil;

import java.time.LocalDate;

import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Patient;

/**
 * A utility class containing typical {@code Patient} objects and values to be used in tests.
 */
public class TypicalPatients {

    public static final String NADIA_NAME = "Nadia Tan";
    public static final String NADIA_PHONE = "93456789";
    public static final String NADIA_EMAIL = "nadiatan@example.com";
    public static final String NADIA_ADDRESS = "Blk 10 Bedok North Ave 2, #03-12";
    public static final String NADIA_NRIC = "S1234567D";
    public static final LocalDate NADIA_DATE_OF_BIRTH = LocalDate.of(1992, 4, 12);
    public static final String NADIA_EMERGENCY_CONTACT = "Amir Tan";

    private TypicalPatients() {}

    /**
     * Returns a fresh Nadia patient fixture each time.
     */
    public static Patient createNadia() {
        return new Patient(new PersonBuilder().withName(NADIA_NAME).withPhone(NADIA_PHONE)
                .withEmail(NADIA_EMAIL).withTags("patient").build(),
                new Address(NADIA_ADDRESS), new NRIC(NADIA_NRIC), NADIA_DATE_OF_BIRTH, NADIA_EMERGENCY_CONTACT);
    }
}
