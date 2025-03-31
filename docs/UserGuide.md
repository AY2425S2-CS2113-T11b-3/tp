# User Guide

## Introduction

NurseSched is a desktop app for managing a Nurse's schedule, optimized for use via a Command Line Interface (CLI). 
With NurseSched, users are able to manage patients’ appointments, nurses’ schedules and patients’ profiles.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `NurseSched` from [here](http://link.to/duke).
3. Open a command terminal, cd into the folder you put the jar file in, and use the java -jar tp 
   command to run the application.
4. Type the command in the command box and press Enter to execute it.

## Features 

### Adding a task: `task add`
Adds user’s task, due date and time to the user’s to-do list.

Format: `task add [td/TASK_DESCRIPTION] [d/DUE_DATE] [t/DUE_TIME]`

* Adds a task to be done by the due date and time set by the user.
* `DUE_TIME` must be in HH:mm format
* `DUE_DATE` must be in YYYY-MM-DD format

Example of usage: 

`task add td/Prepare injection tools for patient Jean d/2025-07-15 t/13:00`

### Marking a task: `task mark`
Marks a task to show its completion.

Format: `task mark [TASK_INDEX]`

* Marks task with index `[TASK_INDEX]` as completed
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list.

Example of usage: 

`task mark 3`

### Unmarking a task: `task unmark`
Unmarks a task to show that it is uncompleted.

Format: `task unmark [TASK_INDEX]`

* Unmarked a task with index `TASK_INDEX` as uncompleted
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list.

Example of usage: 

`task unmark 3`

### Editing a task: `task edit`
Searches for a specific keyword/phrase in the to-do list
and lists out all tasks containing the keyword/phrase

Format: `task edit [id/TASK_INDEX] [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]`

* Not all but at least one parameter, `NEW_DESCRIPTION`, `NEW_DUE_DATE`, `NEW_DUE_TIME` 
  needs to be input depending on what is to be edited
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list

Example of usage: 

`task edit id/1 td/Update medicine supply d/ t/13:00`

`task edit id/3 td/ d/2025-07-01 t/15:00`

`task edit id/5 td/ d/ t/23:59`

### List all tasks: `task list`
Lists all tasks, completion status, due date and time.

Format: `task list`

Example of usage: 

`task list`

### Add a patient profile : `pf add`
Creates a profile for a patient.

Format: `pf add [id/ID_NUMBER] [p/PATIENT_NAME] [a/AGE] [g/GENDER] [c/CONTACT] [n/NOTES]`

* Adds a profile for the specified patient: `PATIENT_NAME`.
* ID_NUMBER must be 4 digits only.
* All fields must be provided except for NOTES, n/ is still required.

Example:

`pf add id/1001 p/Jean a/25 g/F c/66887799 n/requires constant supervision`

This adds a profile for Jean, Female, 25 years old, with a profile ID of 1001. Jean also has a family contact number 
and an additional note which mentions that she needs constant supervision.

### Delete a patient profile : `pf del`
Delete a patient profile.

Format: `pf del [id/[ID_NUMBER]]`

* Deletes a profile for a patient with the specified ID number.
* The ID number for the patient should currently exist in the list for patient information.

Example:
* `pf del id/1000` 

### Search a patient profile : `pf search`
Search for a patient profile.

Format: `pf search [id/ID_NUMBER]`

* Search the patient profile by the patient's ID.
* Displays the patient information if the ID is found within the current list.
* The ID number for the patient should currently exist in the list for patient information.

Example:

* `pf search id/1000`

### List patient information : `pf list`
List out patient information for all patients within the list.

Format: `pf list`

* Displays all patient information stored in the list.

### Edit patient information : `pf edit`
Edit patient information based on the patient selected.

Format: `pf edit [id/ID_NUMBER] [p/PATIENT_NAME] [a/AGE] [g/GENDER] [c/CONTACT] [n/NOTES]`

* The selected profile depends on the patient ID.
* If there is a need to only edit certain fields, only include the required identifiers.

Example: 

* `pf edit id/1000 p/Jean a/22 g/F c/12345678 n/Allergic to peanuts`
* `pf edit id/1000 p/Jean`

In both examples, the chosen fields are updated for the patient accordingly.

### Adding medical test results : `pf result add`
Add medical tests and the results for the patient.

Format: `pf result add [id/ID_NUMBER] [t/TEST_NAME] [r/RESULTS]`

* Creates a medical test and the result for the patient based on the ID given.
* Multiple entries of the test and result can be added.

Example:

* `pf result add id/1000 t/X-Ray r/Fractures found.`
* `pf result add id/1000 t/Blood test r/No abnormalities found.`

### Deleting medical test results : `pf result delete`
Deletes *all* medical tests for the patient.

Format: `pf result del [id/ID_NUMBER]`

* Deletes all medical tests and results for the patient based on the ID given.

Example:

* `pf result del id/1000`

### List out medical test results : `pf result list`
List out the medical tests for the patient.

Format: `pf result list [id/ID_NUMBER]`

Example:

* `pf result list id/1000`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
