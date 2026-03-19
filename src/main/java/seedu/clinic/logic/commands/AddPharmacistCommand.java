package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.clinic.commons.util.ToStringBuilder;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.Model;
import seedu.clinic.model.person.Pharmacist;

/**
 * Adds a pharmacist to the clinic book.
 */
public class AddPharmacistCommand extends Command {

    public static final String COMMAND_WORD = "add-pharmacist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pharmacist to the clinic book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS = "New pharmacist added: %1$s";
    public static final String MESSAGE_DUPLICATE_PHARMACIST = "This pharmacist already exists in the address book";

    private final Pharmacist newPharmacist;

    /**
     * Creates an AddPharmacistCommand to add the specified {@code Pharmacist}
     */
    public AddPharmacistCommand(Pharmacist pharmacist) {
        requireNonNull(pharmacist);
        newPharmacist = pharmacist;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(newPharmacist)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHARMACIST);
        }

        model.addPerson(newPharmacist);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newPharmacist));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPharmacistCommand)) {
            return false;
        }

        AddPharmacistCommand otherAddCommand = (AddPharmacistCommand) other;
        return newPharmacist.equals(otherAddCommand.newPharmacist);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Pharmacist", newPharmacist)
                .toString();
    }
}
