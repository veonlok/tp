package seedu.clinic.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DiagnosisNotFoundExceptionTest {

    @Test
    public void constructor_createsException() {
        DiagnosisNotFoundException exception = new DiagnosisNotFoundException();
        assertNotNull(exception);
    }
}
