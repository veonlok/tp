package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinic.commons.util.ToStringBuilder;
import seedu.clinic.model.Model;
import seedu.clinic.model.person.PatientHasNricPredicate;

/**
 * Finds a patient by NRIC to support medical history retrieval workflows.
 */
public class GetHistoryCommand extends Command {

    public static final String COMMAND_WORD = "get-history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds patient record(s) by NRIC for medical history retrieval.\n"
            + "Parameters: nric/NRIC\n"
            + "Example: " + COMMAND_WORD + " nric/S1234567D";

    public static final String MESSAGE_RESULT = "Found %2$d patient record(s) for NRIC %1$s. "
            + "Detailed medical history retrieval will be expanded in v1.3.";

    private final String nric;
    private final PatientHasNricPredicate predicate;

    /**
     * Creates a command that finds patient records by NRIC.
     */
    public GetHistoryCommand(String nric) {
        requireNonNull(nric);
        this.nric = nric;
        this.predicate = new PatientHasNricPredicate(nric);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_RESULT, nric, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GetHistoryCommand)) {
            return false;
        }

        GetHistoryCommand otherCommand = (GetHistoryCommand) other;
        return nric.equals(otherCommand.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", nric)
                .toString();
    }
}
