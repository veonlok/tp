package seedu.clinic.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ID = new Prefix("id/"); // patient id
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("nric/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_VISIT_DATE = new Prefix("vd/");
    public static final Prefix PREFIX_DIAGNOSED_BY = new Prefix("diagnosed/");
    public static final Prefix PREFIX_SYMPTOM = new Prefix("sym/");
    public static final Prefix PREFIX_MEDICATION = new Prefix("med/");
    public static final Prefix PREFIX_DOSAGE = new Prefix("dose/");
    public static final Prefix PREFIX_FREQ = new Prefix("freq/");
    public static final Prefix PREFIX_PRESCRIBED_BY = new Prefix("prescribed/");
    public static final Prefix PREFIX_DISPENSED_BY = new Prefix("dispensed/");

}
