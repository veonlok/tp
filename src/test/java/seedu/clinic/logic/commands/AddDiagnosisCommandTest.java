package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.ClinicBook;
import seedu.clinic.model.Model;
import seedu.clinic.model.ModelManager;
import seedu.clinic.model.UserPrefs;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Diagnosis;
import seedu.clinic.model.person.Doctor;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Pharmacist;
import seedu.clinic.model.person.Phone;
import seedu.clinic.model.person.Prescription;

public class AddDiagnosisCommandTest {

    private static final int PATIENT_ID = 1;
    private static final int DOCTOR_ID = 2;
    private static final int PHARMACIST_ID = 3;

    @Test
    public void execute_validDiagnosis_success() throws Exception {
        Model model = createModelWithAllRoles();
        Diagnosis diagnosis = createDiagnosis(DOCTOR_ID, PHARMACIST_ID);
        AddDiagnosisCommand command = new AddDiagnosisCommand(Index.fromOneBased(PATIENT_ID), diagnosis);

        CommandResult result = command.execute(model);

        assertEquals(String.format(AddDiagnosisCommand.MESSAGE_SUCCESS, diagnosis), result.getFeedbackToUser());
        assertEquals(1, model.getFilteredPatientList().get(0).getDiagnoses().size());
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Model model = createModelWithAllRoles();
        Diagnosis diagnosis = createDiagnosis(DOCTOR_ID, PHARMACIST_ID);
        AddDiagnosisCommand command = new AddDiagnosisCommand(Index.fromOneBased(99), diagnosis);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AddDiagnosisCommand.MESSAGE_INVALID_PATIENT, exception.getMessage());
    }

    @Test
    public void execute_invalidDoctor_throwsCommandException() {
        Model model = createModelWithPatientOnly();
        Diagnosis diagnosis = createDiagnosis(DOCTOR_ID, PHARMACIST_ID);
        AddDiagnosisCommand command = new AddDiagnosisCommand(Index.fromOneBased(PATIENT_ID), diagnosis);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AddDiagnosisCommand.MESSAGE_INVALID_DOCTOR, exception.getMessage());
    }

    @Test
    public void execute_invalidPharmacist_throwsCommandException() {
        Model model = createModelWithPatientAndDoctor();
        Diagnosis diagnosis = createDiagnosis(DOCTOR_ID, PHARMACIST_ID);
        AddDiagnosisCommand command = new AddDiagnosisCommand(Index.fromOneBased(PATIENT_ID), diagnosis);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(AddDiagnosisCommand.MESSAGE_INVALID_PHARMACIST, exception.getMessage());
    }

    @Test
    public void equals() {
        Diagnosis diagnosis = createDiagnosis(DOCTOR_ID, PHARMACIST_ID);
        Index index = Index.fromOneBased(PATIENT_ID);
        AddDiagnosisCommand command = new AddDiagnosisCommand(index, diagnosis);

        assertTrue(command.equals(command));
        assertTrue(command.equals(new AddDiagnosisCommand(index, diagnosis)));
    }

    private static Diagnosis createDiagnosis(int doctorId, int pharmacistId) {
        Diagnosis diagnosis = new Diagnosis("Flu", LocalDate.of(2026, 3, 1), doctorId);
        diagnosis.addSymptom("fever");
        diagnosis.addPrescription(new Prescription("Paracetamol", "500mg", "3 times daily", pharmacistId));
        return diagnosis;
    }

    private static Model createModelWithAllRoles() {
        ClinicBook clinicBook = new ClinicBook();
        clinicBook.addPerson(new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                PATIENT_ID));
        clinicBook.addPerson(new Doctor(
                new Name("Doctor One"),
                new Phone("92345678"),
                new Email("doctor@example.com"),
                new Address("2 Street"),
                Set.of(),
                DOCTOR_ID));
        clinicBook.addPerson(new Pharmacist(
                new Name("Pharmacist One"),
                new Phone("93456789"),
                new Email("pharmacist@example.com"),
                new Address("3 Street"),
                Set.of(),
                PHARMACIST_ID));
        return new ModelManager(clinicBook, new UserPrefs());
    }

    private static Model createModelWithPatientOnly() {
        ClinicBook clinicBook = new ClinicBook();
        clinicBook.addPerson(new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                PATIENT_ID));
        return new ModelManager(clinicBook, new UserPrefs());
    }

    private static Model createModelWithPatientAndDoctor() {
        ClinicBook clinicBook = new ClinicBook();
        clinicBook.addPerson(new Patient(
                new Name("Patient One"),
                new Phone("91234567"),
                new Email("patient@example.com"),
                new Address("1 Street"),
                Set.of(),
                new NRIC("S1166846A"),
                LocalDate.of(2000, 1, 1),
                "91112222",
                PATIENT_ID));
        clinicBook.addPerson(new Doctor(
                new Name("Doctor One"),
                new Phone("92345678"),
                new Email("doctor@example.com"),
                new Address("2 Street"),
                Set.of(),
                DOCTOR_ID));
        return new ModelManager(clinicBook, new UserPrefs());
    }
}
