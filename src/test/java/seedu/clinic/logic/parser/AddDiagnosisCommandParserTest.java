package seedu.clinic.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.AddDiagnosisCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;

public class AddDiagnosisCommandParserTest {

    private final AddDiagnosisCommandParser parser = new AddDiagnosisCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = " id/1 desc/Flu vd/2026-03-01 diagnosed/2"
                + " sym/fever med/Paracetamol dose/500mg freq/3 times daily dispensed/3";

        AddDiagnosisCommand command = parser.parse(userInput);
        assertTrue(command.toString().contains("Flu"));
    }

    @Test
    public void parse_missingRequiredPrefix_throwsParseException() {
        String userInput = " id/1 vd/2026-03-01 diagnosed/2 sym/fever"
                + " med/Paracetamol dose/500mg freq/3 times daily dispensed/3";
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiagnosisCommand.MESSAGE_USAGE), () ->
                        parser.parse(userInput));
    }

    @Test
    public void parse_noSymptoms_throwsParseException() {
        String userInput = " id/1 desc/Flu vd/2026-03-01 diagnosed/2"
                + " med/Paracetamol dose/500mg freq/3 times daily dispensed/3";
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiagnosisCommand.MESSAGE_USAGE), () ->
                        parser.parse(userInput));
    }

    @Test
    public void parse_noPrescriptions_throwsParseException() {
        String userInput = " id/1 desc/Flu vd/2026-03-01 diagnosed/2 sym/fever";
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiagnosisCommand.MESSAGE_USAGE), () ->
                        parser.parse(userInput));
    }

    @Test
    public void parse_mismatchedPrescriptionFields_throwsParseException() {
        String userInput = " id/1 desc/Flu vd/2026-03-01 diagnosed/2"
                + " sym/fever med/Paracetamol dose/500mg";
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDiagnosisCommand.MESSAGE_USAGE), () ->
                        parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = " id/1 id/2 desc/Flu vd/2026-03-01 diagnosed/2"
                + " sym/fever med/Paracetamol dose/500mg freq/3 times daily dispensed/3";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
