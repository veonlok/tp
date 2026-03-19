package seedu.clinic.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DuplicateDiagnosisExceptionTest {

    @Test
    public void constructor_createsException() {
        DuplicateDiagnosisException exception = new DuplicateDiagnosisException();
        assertNotNull(exception);
    }
}
