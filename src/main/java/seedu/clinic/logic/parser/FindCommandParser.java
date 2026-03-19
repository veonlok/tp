package seedu.clinic.logic.parser;

import static seedu.clinic.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.PersonMatchesFindCriteriaPredicate;
import seedu.clinic.model.person.Phone;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Ensure each supported prefix appears at most once.
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC);

        List<String> nameKeywords = List.of();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameInput = argMultimap.getValue(PREFIX_NAME).get();
            if (nameInput.isBlank()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            nameKeywords = Arrays.asList(nameInput.split("\\s+"));
        }

        Optional<Phone> phone = Optional.empty();
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = Optional.of(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        Optional<NRIC> nric = Optional.empty();
        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            nric = Optional.of(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get()));
        }

        return new FindCommand(new PersonMatchesFindCriteriaPredicate(nameKeywords, phone, nric));
    }

    /**
     * Returns true if at least one of the prefixes contains a value.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
