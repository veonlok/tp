package seedu.clinic.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Diagnoses (Diagnoses are considered duplicates if they have the
 * same identity).
 */
public class DuplicateDiagnosisException extends RuntimeException {
    public DuplicateDiagnosisException() {
        super("Operation would result in duplicate diagnoses");
    }
}
