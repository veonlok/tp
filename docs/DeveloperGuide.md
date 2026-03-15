---
layout: page
title: Developer Guide
---
* Table of Contents
  {:toc}

---

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

💡 **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the delete 1</code></code> Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).`<br>`
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.

  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.
* **Alternative 2:** Individual command knows how to undo/redo by
  itself.

  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

Clinic staff who manage patient and vendor information as part of daily clinic operations

* Registering patients
* Managing contact information (patients, vendors, etc.)
* Retrieving and updating records quickly

**Value proposition**:

- Digitises and reduce paper-based records
- Faster information retrieval
- Reduced human error (illegible handwriting, duplicate entries, etc.)
- Easy to learn and use, designed for staff with basic computer skills
- Lightweight and cost effective (minimal resources and no complex setup)
- Speed up patient registration during busy hours
- Improve data consistency across patient and vendor records
- Decrease dependency on individual staff memory
- Support quicker onboarding of new staff

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority  | As a…               | I can…                                                                                                      | So that…                                                                                   |
| --------- | -------------------- | ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------- |
| `* * `  | Doctor               | Update an existing patient's health information                                                              | I can make medical decisions based on the most current medical history.                     |
| `* * *` | Doctor               | Record an existing patient's symptoms, issue a diagnosis and generate prescriptions with specified dosages   | The patient receives accurate and timely treatment                                          |
| `* * *` | Doctor               | Retrieve an existing patient's medical history                                                               | We can make informed, clinical diagnosis                                                    |
| `* `    | Doctor               | Order lab or imaging tests                                                                                   | We can confirm or refine a diagnosis                                                        |
| `* *`   | Doctor               | Generate a medical certificate for a patient                                                                 | They can formally justify absence from work, school, or other obligations                   |
| `*`     | Doctor               | Generate a specialist referral                                                                               | The patient can receive expert evaluation or treatment for conditions beyond my scope       |
| `*`     | Doctor               | Document any reported side-effects linked to a specific medication and prescription after its administration | Adverse reactions are traceable and clinically actionable                                   |
| `*`     | Doctor               | Retrieve an existing patient's medical records from their caregiver or next-of-kin's name                    | We can access the correct patient records when the patient cannot provide identification    |
| `* `    | Doctor               | Record an existing patient's vital signs                                                                     | Changes in condition can be monitored                                                       |
| `* `    | Doctor               | Log medication administration details                                                                        | Treatment delivery is traceable                                                             |
| `* *`   | Doctor               | Schedule follow-up appointments before discharge                                                             | Continuity of care is maintained                                                            |
| `* * *` | Pharmacist           | Retrieve a patient's health information                                                                      | We can issue the right medication based on the doctor's recommendation                      |
| `* *`   | Pharmacist           | Update a prescription after identifying an incorrect medication or dosage                                    | The patient receives the correct treatment while maintaining a clear audit trail of changes |
| `* *`   | Pharmacist           | Mark a prescription as dispensed                                                                             | Medication fulfillment is fully tracked                                                     |
| `*`     | Pharmacist           | Generate a prescription record for a patient when the medication is unavailable                              | The patient can pick it up from another authorised pharmacy                                 |
| `* * *` | Existing patient     | View my medical records                                                                                      | I can understand my health condition and treatment history                                  |
| `*`     | Existing patient     | Request a refill on my prescription                                                                          | My treatment is not interrupted                                                             |
| `*`     | Existing patient     | Schedule an appointment                                                                                      | I receive timely medical care                                                               |
| `* *`   | Existing patient     | Update my contact information                                                                                | The clinic can reach me whenever necessary                                                  |
| `* *`   | Existing patient     | Grant access to my caregiver or next-of-kin                                                                  | They can assist in managing my healthcare                                                   |
| `* * *` | System Administrator | Purge a patient's record based on data retention policy                                                      | We maintain only the data that is required to operate compliantly                           |
| `* * *` | System Administrator | Register a new patient                                                                                       | The patient can be registered in the system and receive care                                |
| `* * *` | System Administrator | Register a new doctor                                                                                        | They can access the clinic system with the appropriate permissions                          |
| `* * *` | System Administrator | Register a new pharmacist                                                                                    | They can access the clinic system with the appropriate permissions                          |
| `* *`   | Registration staff   | Have phone numbers and IDs auto formatted                                                                    | Data is entered consistently                                                                |
| `* * *` | Registration staff   | Search for an existing patient before creating a new record                                                  | I can avoid creating a duplicated patient record                                            |
| `* * *` | Registration staff   | Search for patients by name, NRIC, or phone number                                                           | I can retrieve records quickly                                                              |
| `*`     | System Administrator | Import a patient medical history from an external clinic after verification                                  | The patient's records are complete and up-to-date                                           |

## MVP User Stories

| Priority  | As a…               | I can…                                                                                                    | So that…                                                           |
| --------- | -------------------- | ---------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------- |
| `* * *` | Doctor               | Record an existing patient's symptoms, issue a diagnosis and generate prescriptions with specified dosages | The patient receives accurate and timely treatment                  |
| `* * *` | Doctor / Pharmacist  | Retrieve an existing patient's medical history                                                             | We can make informed, clinical diagnosis                            |
| `* * *` | System Administrator | Purge a patient's record based on data retention policy                                                    | We maintain only the data that is required to operate compliantly   |
| `* * *` | System Administrator | Register a new patient                                                                                     | The patient can be registered in the system and receive care        |
| `* * *` | System Administrator | Register a new doctor                                                                                      | They can access the clinic system with the appropriate permissions. |
| `* * *` | System Administrator | Register a new pharmacist                                                                                  | They can access the clinic system with the appropriate permissions. |
| `* * *` | Registration staff   | Search for patients by name, NRIC, or phone number                                                         | I can retrieve records quickly                                      |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add New Patient Record**

**MSS**

1. User requests to add a new patient.
2. ClinicBook requests for patient details.
3. User enters the patient's details.
4. User submits the details.
5. ClinicBook shows the details for confirmation.
6. User confirms.
7. ClinicBook adds the record.
   Use case ends.

**Extensions**

* 4a. ClinicBook finds a duplicate record with the same NRIC

  * 4a1. ClinicBook shows the potential duplicate record.
  * Use case ends.
* 4b. ClinicBook find a duplicate record with the same name or phone number

  * 4b1. ClinicBook shows the potential duplicate record.
  * 4b2. ClinicBook requests for confirmation.
  * 4b3. User amends if needed.
  * 4b4. User confirms.
  * Use case resumes at Step 7.
* 4c. Invalid input

  * 4c1. ClinicBook shows an error message indicating a correct input format.
  * Use case resumes at Step 2.
* 5a. User wants to edit

  * 5a1. User retracts the submission
  * Use case resumes at Step 2
* 5b. User doesn't want to add this record anymore

  * 5b1. User cancels the submission
  * Use case ends.

**Use case: UC2 - Get Patient's Medical History**

**MSS**

1. User requests to view patient's medical history.
2. ClinicBook requests for patient's information, NRIC / name.
3. User provides patient's NRIC / Name.
4. ClinicBook shows the medical history of this user.
   Use case ends.

**Extensions**

* 2a. ClinicBook cannot find the record

  * 2a1. ClinicBook notifies the user that no records were found.
    Use case ends.
* 3a. The patient's NRIC / Name is invalid.

  * 3a1. AddressBook shows an error message.
    Use case resumes at step 2.

**Use case: UC3 - Create patient diagnosis and prescription**

**Actor:** Doctor

**Preconditions:** Doctor is logged in; UC2 returns a valid patient record

**MSS**

1. Doctor `<u>`gets a patient's medical history (UC2)`</u>`
2. Doctor requests to create a new diagnosis
3. ClinicBook requests for diagnosis details
4. Doctor enters diagnosis, prescription details
5. ClinicBook requests for confirmation on recording of diagnosis, prescription
6. Doctor confirms
7. ClinicBook adds new diagnosis, prescription

Use case ends.

**Extensions**

* 4a. The diagnosis field is empty
  4a1. ClinicBook requests for a diagnosis and prescription
  4a2. Doctor enters data for the missing fields
  Steps 4a1 - 4a2 are repeated until the missing fields are filled
  Use case resumes at step 5
* *a. At any time, doctor chooses to cancel the diagnosis logging
  *a1. ClinicBook requests to confirm cancellation.
  *a2. Doctor confirms
  Use case ends.

**Use case: UC4 - Register a new doctor**

**Actor:** System Administrator

**Preconditions:** System Administrator is logged in

**MSS**

1. System Administrator requests to register a new doctor
2. ClinicBook requests for doctor's particulars
3. System Administrator enters doctor's particulars
4. ClinicBook requests for confirmation on registering the new doctor
5. System Administrator confirms
6. ClinicBook registers new doctor
   Use case ends.

**Extensions**

* 3a. At least one of the fields (name, NRIC, contact number) are empty
  3a1. ClinicBook requests for values for these fields
  3a2. System Administrator enters data for the missing fields
  Steps 3a1 - 3a2 are repeated until the missing fields are filled
  Use case resumes at step 4.
* 3b. ClinicBook finds a duplicate doctor with the same NRIC
  3b1. ClinicBook shows the duplicate record
  Use case ends.
* 3c. System Administrator enters invalid input.
* 3c1. ClinicBook shows an error message indicating the correct input format.
* 3c2. System Administrator re-enters the particulars.
  Use case resumes at step 4.
* *a. At any time, System Administrator chooses to cancel the doctor registration
  *a1. ClinicBook requests to confirm cancellation.
  *a2. System Administrator confirms
  Use case ends.


**Use case: UC5 - Search for Patient by Name, NRIC, or Phone Number**

**Actor:** Registration Staff

**Preconditions:** Registration Staff is logged in.

**MSS**

1. Registration Staff requests to search for a patient.
2. ClinicBook requests a search keyword.
3. Registration Staff enters a patient's name, NRIC, or phone number.
4. ClinicBook validates the search keyword.
5. ClinicBook searches for patient records matching the keyword.
6. ClinicBook displays a list of matching patient records.

Use case ends.

**Extensions**

* 3a. Registration Staff enters an empty search keyword.
  * 3a1. ClinicBook shows an error message.
  * 3a2. Registration Staff enters a valid search keyword.
  * Use case resumes at step 4.

* 4a. The search keyword is not in a valid name, NRIC, or phone number format.
  * 4a1. ClinicBook shows an error message.
  * 4a2. Registration Staff enters a valid search keyword.
  * Use case resumes at step 4.

* 5a. No patient records match the search keyword.
  * 5a1. ClinicBook informs Registration Staff that no matching records were found.
  * Use case ends.

* *a. At any time, Registration Staff chooses to cancel the search.
  * *a1. ClinicBook cancels the search request.
  * Use case ends.


**Use case: UC6 - Register a New Pharmacist**

**Actor:** System Administrator

**Preconditions:** System Administrator is logged in.

**MSS**

1. System Administrator requests to register a new pharmacist.
2. ClinicBook requests the pharmacist's particulars.
3. System Administrator enters the pharmacist's particulars.
4. ClinicBook shows the entered particulars for confirmation.
5. System Administrator confirms.
6. ClinicBook registers the new pharmacist.

Use case ends.

**Extensions**

* 3a. At least one of the fields (name, NRIC, contact number) are empty.
  * 3a1. ClinicBook shows an error message indicating the missing fields.
  * 3a2. System Administrator enters the missing information.
  Steps 3a1–3a2 are repeated until all compulsory fields are provided.
  Use case resumes at step 4.

* 3b. ClinicBook detects a duplicate pharmacist record with the same NRIC.
  * 3b1. ClinicBook shows the potential duplicate record.
  Use case ends.

* 3c. System Administrator enters invalid input.
  * 3c1. ClinicBook shows an error message indicating the correct input format.
  * 3c2. System Administrator re-enters the particulars.
  Use case resumes at step 4.

* *a. At any time, System Administrator chooses to cancel the registration.
  * *a1. ClinicBook requests confirmation for cancellation.
  * *a2. System Administrator confirms.
  Use case ends.




*{More to be added}*


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  All operations should complete within 2 seconds
5.  The system supports only one user accessing the data at a time.
6.  Data should persist unless the user deletes the data file.
7. The system should be able to recover gracefully from unexpected shutdowns without data loss for committed transactions.
8. The application should handle invalid or malformed data files without crashing and provide appropriate error messages.
9. The system should enforce role-based access control so that users can only perform actions permitted by their assigned roles (e.g., only System Administrators can register pharmacists).
10. The system should validate user input such as NRIC, phone numbers, and names before storing them to prevent invalid data.


### Glossary

* **Diagnosis**: A medical description of a patient's condition or disease based on symptoms, medical history, and clinical examination
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Patient Record**: A record containing a patient's personal information and medical history in ClinicBook.
* **Prescription**: A written order from a doctor specifying medication, dosage, and administration instructions for a patient's treatment
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Symptom**: Physical or mental signs experienced by a patient that indicate a medical condition or disease
* **Duplicate Record**: A record with the same NRIC / Name / Phone Number
* **NRIC**: National Registration Identity Card number used as a unique identifier for individuals in the system.
* **System User**: Any individual registered in ClinicBook, such as a patient, doctor, or pharmacist.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder
   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.
2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by double-clicking the jar file.`<br>`
      Expected: The most recent window size and location is retained.
3. _{ more test cases … }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.
   2. Test case: `delete 1<br>`
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.
   3. Test case: `delete 0<br>`
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.
   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)`<br>`
      Expected: Similar to previous.
2. _{ more test cases … }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_
2. _{ more test cases … }_
