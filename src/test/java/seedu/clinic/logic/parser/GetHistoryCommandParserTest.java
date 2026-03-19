package seedu.clinic.logic.parser;

import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.GetHistoryCommand;

public class GetHistoryCommandParserTest {

    private final GetHistoryCommandParser parser = new GetHistoryCommandParser();

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "S1234567D",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetHistoryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNricFormat_throwsParseException() {
        assertParseFailure(parser, " nric/T123 4567Z", ParserUtil.MESSAGE_INVALID_HISTORY_NRIC);
    }

    @Test
    public void parse_validNric_returnsGetHistoryCommand() {
        assertParseSuccess(parser, " nric/t1234567z", new GetHistoryCommand("T1234567Z"));
        assertParseSuccess(parser, " \n nric/S1234567D", new GetHistoryCommand("S1234567D"));
    }
}
