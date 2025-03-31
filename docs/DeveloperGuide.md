# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design

### UI component

API: `Ui.java`

[//]: # (todo: insert Ui diagram)

### Storage component

API: `Storage.java`

[//]: # (todo: insert Storage diagram)

The `Storage` component,

- can save the appointments list and medicine supply data in txt format, and read them back into the corresponding
  objects.
- depends on some classes in the `Appointment` and `Medicine` component (because the `Storage` components' job is to
  save/retrieve objects that belong to `Appointment` and `Medicine`)

### Common classes

Classes used by multiple components are in:

- `seedu.nursesched.exception` package

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### [Proposed] PatientInfo feature

#### Proposed Implementation

### Medicine delete feature

#### Implementation

The `deleteMedicine` method is responsible for deleting a specific medicine from the medicine list. The implementation
follows these steps:

1. Assertion and Logging: The method first checks if the `medicineName` is not null, throwing an assertion error if it
   is. It then logs an informational message about the attempt to delete the specified medicine.
2. Medicine removal: It uses the `removeIf` method to attempt to remove a medicine from the `medicineList` by comparing
   the name of each medicine (ignoring case). If the medicine is found and removed, ti proceeds to overwrite the save
   file with the updated list.
3. Failure handling: If no medicine matching the given name is found, it logs a warning and throws a
   `NurseSchedException` with the appropriate error message.

Given below is an example usage scenario and how the delete medicine mechanism behaves at each step.

[//]: # (todo: add diagrams)
Step 1. The user launches the application for the first time. The `medicineList` will be initialized with the medicine
data stored (if exists).

Step 2. The user then adds a medicine using the `addMedicine` operation. If successful, the system logs the addition
and updates the saved file to reflect the change.

Step 3. The user then realised that the medicine has expired, thus she needs to delete it from the medicine supply list.
The user initiates the deletion of a medicine by calling the `deleteMedicine` function with the name of the medicine
to be deleted.

Step 4. The system attempts to find and remove the specified medicine from the list. If successful, the system logs the
deletion and updates the saved file to reflect the change.

Step 5. If the medicine is not found in the list, the system logs a warning and throws a custom exception,
`NurseSchedException`, with a relevant message indicating that the medicine does not exist.

Step 6. The system outputs a confirmation message or an error based on whether the medicine was successfully deleted or
not.

#### Design considerations

Aspect: How delete medicine executes:

- Alternative 1 (current choice): Perform a case-sensitive comparison for medicine name deletion.
    - Pros: Ensures that only the exact medicine name is removed.
    - Cons: May lead to issues where users input medicine names with different capitalizations.
- Alternative 2: Remove the medicine by other unique identifiers like a medicine ID.
    - Pros: Ensure precise deletion, particularly if multiple medicines share similar names.
    - Cons: Requires the medicine to have a unique identifier in the system, adding complexity

## Product scope

### Target user profile:

- nurses working in hospitals
- have shifts, schedules, patients profiles, medicine supplies and appointments to manage
- can type fast
- prefer desktop apps over other types
- is reasonably comfortable using CLI apps
- prefers typing to mouse interaction

### Value proposition

We want to help nurses organise different lists faster than a typical mouse/GUI driven app or digging out
information from many different sources.
This is so that they can retrieve information quickly, especially with how hectic their schedules are.

## User Stories

| Version | As a ... | I want to ...                                  | So that I can ...                                                   |
|---------|----------|------------------------------------------------|---------------------------------------------------------------------|
| v2.0    | Nurse    | Add tasks to my todo list                      | keep track of my things to do                                       |
| v2.0    | Nurse    | List out my tasks                              | I can view all the things to be completed                           |
| v2.0    | Nurse    | Check off things from my to-do list            | I know which tasks have been completed                              |
| v2.0    | Nurse    | Delete my tasks                                | I can remove irrelevant tasks                                       |
| v2.0    | Nurse    | Edit my to-do list                             | I can fill it with updated information that I need to keep track of |
| v2.0    | Nurse    | Search for a task                              | I can locate a specific task with a keyword                         |
| v2.0    | Nurse    | add in an amount of medicine to the list       | update my medicine supply                                           |
| v2.0    | Nurse    | remove an amount of medicine from the list     | update my medicine supply                                           |
| v2.0    | Nurse    | search for a specific medicine from the list   | see how much of the medicine I have left                            |
| v2.0    | Nurse    | edit the information of the medicine           | ensure that my medicine supply is up-to-date                        |
| v2.0    | Nurse    | delete a specific medicine                     | entirely remove a medicine from the list                            |
| v2.0    | Nurse    | view the total supply of medicine left         | know what needs to be restocked                                     |
| v2.0    | Nurse    | save the medicine supply list                  | keep track of the supply                                            |
| v2.0    | Nurse    | search for an appointment                      | filter the list of appointments                                     |
| v2.0    | Nurse    | edit appointment dates and time                | update my schedule                                                  |
| v2.0    | Nurse    | arrange appointments in chronological order    | view my upcoming appointments first                                 | 
| v2.0    | Nurse    | save appointment information                   | retrieve previously stored appointment information                  | 
| v2.0    | Nurse    | rank importance of appointments                | arrange my appointments based off priority                          |
| v2.0    | Nurse    | list medicine that is below a certain quantity | know which medicine to restock                                      |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
