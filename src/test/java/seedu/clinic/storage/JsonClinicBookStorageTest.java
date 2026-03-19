package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalPatients.createNadia;
import static seedu.clinic.testutil.TypicalPersons.ALICE;
import static seedu.clinic.testutil.TypicalPersons.HOON;
import static seedu.clinic.testutil.TypicalPersons.IDA;
import static seedu.clinic.testutil.TypicalPersons.getTypicalClinicBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.clinic.commons.exceptions.DataLoadingException;
import seedu.clinic.model.ClinicBook;
import seedu.clinic.model.ReadOnlyClinicBook;
import seedu.clinic.model.person.Patient;
import seedu.clinic.model.person.Person;

public class JsonClinicBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClinicBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClinicBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClinicBook(null));
    }

    private java.util.Optional<ReadOnlyClinicBook> readClinicBook(String filePath) throws Exception {
        return new JsonClinicBookStorage(Paths.get(filePath)).readClinicBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClinicBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readClinicBook("notJsonFormatClinicBook.json"));
    }

    @Test
    public void readClinicBook_invalidPersonClinicBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClinicBook("invalidPersonClinicBook.json"));
    }

    @Test
    public void readClinicBook_invalidAndValidPersonClinicBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClinicBook("invalidAndValidPersonClinicBook.json"));
    }

    @Test
    public void readAndSaveClinicBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClinicBook.json");
        ClinicBook original = getTypicalClinicBook();
        JsonClinicBookStorage jsonClinicBookStorage = new JsonClinicBookStorage(filePath);

        // Save in new file and read back
        jsonClinicBookStorage.saveClinicBook(original, filePath);
        ReadOnlyClinicBook readBack = jsonClinicBookStorage.readClinicBook(filePath).get();
        assertEquals(original, new ClinicBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonClinicBookStorage.saveClinicBook(original, filePath);
        readBack = jsonClinicBookStorage.readClinicBook(filePath).get();
        assertEquals(original, new ClinicBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonClinicBookStorage.saveClinicBook(original); // file path not specified
        readBack = jsonClinicBookStorage.readClinicBook().get(); // file path not specified
        assertEquals(original, new ClinicBook(readBack));

    }

    @Test
    public void readAndSaveClinicBook_withPatient_preservesSubtypeAndFields() throws Exception {
        Path filePath = testFolder.resolve("TempClinicBookWithPatient.json");
        ClinicBook original = getTypicalClinicBook();
        Patient nadia = createNadia();
        original.addPerson(nadia);

        JsonClinicBookStorage jsonClinicBookStorage = new JsonClinicBookStorage(filePath);
        jsonClinicBookStorage.saveClinicBook(original, filePath);
        ReadOnlyClinicBook readBack = jsonClinicBookStorage.readClinicBook(filePath).get();

        Person readBackPerson = new ClinicBook(readBack).getPersonList().get(readBack.getPersonList().size() - 1);
        assertTrue(readBackPerson instanceof Patient);
        Patient readBackPatient = (Patient) readBackPerson;
        assertEquals(nadia.getName(), readBackPatient.getName());
        assertEquals(nadia.getPhone(), readBackPatient.getPhone());
        assertEquals(nadia.getEmail(), readBackPatient.getEmail());
        assertEquals(nadia.getAddress(), readBackPatient.getAddress());
        assertEquals(nadia.getNric(), readBackPatient.getNric());
        assertEquals(nadia.getDateOfBirth(), readBackPatient.getDateOfBirth());
        assertEquals(nadia.getEmergencyContact(), readBackPatient.getEmergencyContact());
    }

    @Test
    public void saveClinicBook_nullClinicBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinicBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code clinicBook} at the specified {@code filePath}.
     */
    private void saveClinicBook(ReadOnlyClinicBook clinicBook, String filePath) {
        try {
            new JsonClinicBookStorage(Paths.get(filePath))
                    .saveClinicBook(clinicBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClinicBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClinicBook(new ClinicBook(), null));
    }
}
