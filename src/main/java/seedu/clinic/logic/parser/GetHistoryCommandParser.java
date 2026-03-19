package seedu.clinic.logic.parser;

import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.clinic.logic.commands.GetHistoryCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GetHistoryCommand object.
 */
public class GetHistoryCommandParser implements Parser<GetHistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetHistoryCommand
     * and returns a GetHistoryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetHistoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!argMultimap.getPreamble().isEmpty() || argMultimap.getValue(PREFIX_NRIC).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetHistoryCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);
        String nric = ParserUtil.parseNricForHistory(argMultimap.getValue(PREFIX_NRIC).get());
        return new GetHistoryCommand(nric);
    }
}
