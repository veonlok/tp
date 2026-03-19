package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.storage.JsonAdaptedDiagnosis.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.person.Diagnosis;

public class JsonAdaptedDiagnosisTest {

    @Test
    public void toModelType_validDiagnosis_returnsDiagnosis() throws Exception {
        Diagnosis diagnosis = new Diagnosis("Flu", LocalDate.of(2026, 3, 1), 2);
        diagnosis.addSymptom("fever");
        JsonAdaptedDiagnosis adapted = new JsonAdaptedDiagnosis(diagnosis);

        Diagnosis model = adapted.toModelType();
        assertEquals("Flu", model.getDescription());
        assertEquals(LocalDate.of(2026, 3, 1), model.getVisitDate());
        assertEquals(2, model.getDiagnosedBy());
        assertEquals(List.of("fever"), model.getSymptoms());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedDiagnosis adapted = new JsonAdaptedDiagnosis(null, "2026-03-01", 1, List.of(), List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_nullVisitDate_throwsIllegalValueException() {
        JsonAdaptedDiagnosis adapted = new JsonAdaptedDiagnosis("Flu", null, 1, List.of(), List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "visitDate");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }

    @Test
    public void toModelType_missingDiagnosedBy_throwsIllegalValueException() {
        JsonAdaptedDiagnosis adapted = new JsonAdaptedDiagnosis("Flu", "2026-03-01", 0, List.of(), List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "diagnosedBy");
        assertThrows(IllegalValueException.class, expectedMessage, adapted::toModelType);
    }
}
