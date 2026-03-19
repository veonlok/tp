package seedu.clinic.logic.parser;

import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.PersonMatchesFindCriteriaPredicate;
import seedu.clinic.model.person.Phone;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, " Alice n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new PersonMatchesFindCriteriaPredicate(Arrays.asList("Alice", "Bob"),
                        Optional.empty(), Optional.empty()));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, " \n n/Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonMatchesFindCriteriaPredicate(Collections.emptyList(),
                        Optional.of(new Phone("98765432")), Optional.empty()));
        assertParseSuccess(parser, " p/98765432", expectedFindCommand);
    }

    @Test
    public void parse_validNricArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new PersonMatchesFindCriteriaPredicate(
                Collections.emptyList(), Optional.empty(), Optional.of(new NRIC("S1234567D"))));
        assertParseSuccess(parser, " nric/s1234567d", expectedFindCommand);
    }

    @Test
    public void parse_validNamePhoneAndNricArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new PersonMatchesFindCriteriaPredicate(
                Arrays.asList("Alice", "Bob"), Optional.of(new Phone("98765432")),
                Optional.of(new NRIC("S1234567D"))));
        assertParseSuccess(parser, " n/Alice Bob p/98765432 nric/S1234567D", expectedFindCommand);
    }

    @Test
    public void parse_emptyNameValue_throwsParseException() {
        assertParseFailure(parser, " n/   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPhoneValue_throwsParseException() {
        assertParseFailure(parser, " p/abc", Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNricValue_throwsParseException() {
        assertParseFailure(parser, " nric/S1234567A", NRIC.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " n/Alice n/Bob", getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(parser, " p/12345678 p/98765432", getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
        assertParseFailure(parser, " nric/S1234567D nric/T1234567J",
                getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));
    }
}
