package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.person.Address;
import seedu.clinic.model.person.Email;
import seedu.clinic.model.person.NRIC;
import seedu.clinic.model.person.Name;
import seedu.clinic.model.person.Phone;
import seedu.clinic.model.person.Prescription;
import seedu.clinic.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_HISTORY_NRIC =
            "Invalid NRIC format. Expected 1 letter + 7 digits + 1 letter. E.g. T1234567Z";
    public static final String MESSAGE_INVALID_DATE = "Invalid date format. Expected yyyy-MM-dd E.g. 2002-03-01";
    private static final String HISTORY_NRIC_REGEX = "[A-Z]\\d{7}[A-Z]";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String nric} into an {@code NRIC}.
     * Leading and trailing whitespaces are trimmed and lower-case input is normalized.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static NRIC parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String normalizedNric = nric.trim().toUpperCase();
        if (!NRIC.isValidNric(normalizedNric)) {
            throw new ParseException(NRIC.MESSAGE_CONSTRAINTS);
        }
        return new NRIC(normalizedNric);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String nric} for get-history command into a normalized uppercase NRIC string.
     * Leading and trailing whitespaces are trimmed.
     *
     * @throws ParseException if the given {@code nric} does not match the command's accepted NRIC format.
     */
    public static String parseNricForHistory(String nric) throws ParseException {
        requireNonNull(nric);
        String normalized = nric.trim().toUpperCase();
        if (!normalized.matches(HISTORY_NRIC_REGEX)) {
            throw new ParseException(MESSAGE_INVALID_HISTORY_NRIC);
        }
        return normalized;
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * Expected format: yyyy-MM-dd (e.g. 2026-03-01)
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses prescription details into a {@code Prescription} object.
     *
     * @throws ParseException if any field is invalid
     */
    public static Prescription parsePrescription(
            String medicationName,
            String dosage,
            String frequency,
            int dispensedBy) throws ParseException {

        requireNonNull(medicationName);
        requireNonNull(dosage);
        requireNonNull(frequency);

        medicationName = medicationName.trim();
        dosage = dosage.trim();
        frequency = frequency.trim();

        if (medicationName.isEmpty()) {
            throw new ParseException("Medication name cannot be empty.");
        }
        if (dosage.isEmpty()) {
            throw new ParseException("Dosage cannot be empty.");
        }
        if (frequency.isEmpty()) {
            throw new ParseException("Frequency cannot be empty.");
        }

        return new Prescription(medicationName, dosage, frequency, dispensedBy);
    }
}
