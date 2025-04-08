# User Guide

## Introduction

NurseSched is a desktop app for managing a Nurse's schedule, optimized for use via a Command Line Interface (CLI).
With NurseSched, users are able to manage patients’ appointments, user’s schedules and patients’ profiles and medicine
supply.

* [Quick Start](#quick-start)
* [Features](#features)
* [Task List](#task-list)
    * [Adding a task](#adding-a-task-task-add)
    * [Deleting a task](#deleting-a-task-task-del)
    * [Marking a task](#marking-a-task-task-mark)
    * [Unmarking a task](#unmarking-a-task-task-unmark)
    * [Editing a task](#editing-a-task-task-edit)
    * [Finding a task](#finding-a-task-task-find)
    * [Listing all tasks](#listing-all-tasks-task-list)
* [Shift List](#shift-list)
    * [Adding a shift](#adding-a-shift-shift-add)
    * [Editing a shift](#editing-a-shift-shift-edit)
    * [Marking a shift](#marking-a-shift-shift-mark)
    * [Unmarking a shift](#unmarking-a-shift-shift-unmark)
    * [Deleting a shift](#deleting-a-shift-shift-del)
    * [Listing all shifts](#listing-all-shifts-shift-list)
* [Patient List](#patient-list)
    * [Adding a patient profile](#adding-a-patient-profile--pf-add)
    * [Deleting a patient profile](#deleting-a-patient-profile--pf-del)
    * [Searching for a patient profile](#finding-a-patient-profile--pf-find)
    * [Listing all patient profiles](#listing-all-patient-profiles--pf-list)
    * [Editing a patient information](#editing-a-patient-information--pf-edit)
    * [Adding a medical test result](#adding-a-medical-test-result--pf-result-add)
    * [Deleting a medical test result](#deleting-a-medical-test-result--pf-result-delete)
    * [Listing all medical test results](#listing-all-medical-test-results--pf-result-list)
* [Appointment List](#appointment-list)
    * [Adding an appointment](#adding-an-appointment-appt-add)
    * [Deleting an appointment](#deleting-an-appointment-appt-del)
    * [Marking an appointment](#marking-an-appointment-appt-mark)
    * [Unmarking an appointment](#unmarking-an-appointment-appt-unmark)
    * [Editing an appointment](#editing-an-appointment-appt-edit)
    * [Listing all appointments](#listing-all-appointments-appt-list)
    * [Find an appointment](#finding-an-appointment-appt-find)
    * [Sorting appointments](#sorting-appointments-appt-sort)
* [Medicine List](#medicine-list)
    * [Adding a medicine quantity](#adding-a-medicine-quantity-medicine-add)
    * [Removing a medicine quantity](#removing-a-medicine-quantity-medicine-remove)
    * [Listing all medicine supply](#listing-all-medicine-supply-medicine-list)
    * [Finding a medicine name](#finding-a-medicine-name-medicine-find)
    * [Deleting a medicine](#deleting-a-medicine-medicine-delete)
    * [Editing a medicine](#editing-a-medicine-medicine-edit)
    * [Restocking a medicine](#restocking-a-medicine-medicine-restock)
* [Other commands](#other-commands)
    * [Exit](#exit-exit-ns)
* [FAQ](#faq)
* [Command summary](#command-summary)

## Quick Start

1. Ensure that you have Java `17` or above installed.
2. Download the latest version of `NurseSched` from [here](https://github.com/AY2425S2-CS2113-T11b-3/tp/releases).
3. Open a command terminal, cd into the folder you put the `.jar` file in, and use the `java -jar nursesched.jar`
   command to run the application.
4. Type the command in the command box and press Enter to execute it.
5. Refer to the [Features](#features) below for details of each command.

## Features

> Notes about the command format:
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.<br> e.g `medicine add
    mn/MEDICINE_NAME q/QUANTITY`, `MEDICINE_NANE` and `QUANTITY` are parameters which can be used as `medicine add
    mn/Paracetamol q/50`.
> * Markers with `/` are compulsory. <br> e.g `medicine restock q/[QUANTITY]`, `q/` must be included in the command.
> * Order of markers must follow the command format exactly.<br> e.g
    `medicine edit mn/MEDICINE_NAME un/UPDATED_NAME uq/UPDATED_QUANTITY` cannot be typed in as `medicine edit
    un/UPDATED_NAME mn/MEDICINE_NAME uq/UPDATED_QUANTITY`
> * Items in square brackets are optional.<br> e.g
    `task edit id/TASK_INDEX [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]`, `[td/NEW_DESCRIPTION]`,
    `[d/NEW_DUE_DATE]` and `[t/NEW_DUE_TIME]` are optional fields.
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple
    lines as space characters surrounding line-breaks may be omitted when copied over to the application.

### Task List

### Adding a task: `task add`

Adds user’s task, due date and time to the user’s to-do list.

Format: `task add td/TASK_DESCRIPTION d/DUE_DATE t/DUE_TIME`

* Adds a task to be done by the due date and time set by the user.
* `DUE_TIME` must be in HH:mm format
* `DUE_DATE` must be in YYYY-MM-DD format

Example of usage:

`task add td/Prepare tools d/2025-07-15 t/13:00`

### Deleting a task: `task del`

Deletes a task from the task list.

Format: `task del id/TASK_INDEX`

* Deletes the task indexed with `TASK_INDEX` based on the task list.
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list.

Example of usage:

`task del id/3`

### Marking a task: `task mark`

Marks a task to show its completion.

Format: `task mark id/TASK_INDEX`

* Marks task with index `TASK_INDEX` as completed
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list.

Example of usage:

`task mark id/3`

### Unmarking a task: `task unmark`

Unmarks a task to show that it is uncompleted.

Format: `task unmark id/TASK_INDEX`

* Unmarks a task with index `TASK_INDEX` as uncompleted
* `TASK_INDEX` must be a number between 1 to the total number of tasks in the list.

Example of usage:

`task unmark id/3`

### Editing a task: `task edit`

Edits an existing task in the task list.

Format: `task edit id/TASK_INDEX [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]`

* Edits the task at the specified `TASK_INDEX`. The `TASK_INDEX` refers to the index number shown in the displayed task
  list. The index must be a positive integer 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example of usage:

* `task edit id/1 td/Remind Jean about appointment` Edits only the task description leaving the date and time unedited.
* `task edit id/1 td/Update medicine supply t/13:00` Edits the task description and due time of the 1st task in the
  task list while leaving the date unedited.
* `task edit id/3 d/2025-07-01 t/15:00` Edits the due date and time of the 3rd task in the task list while leaving the
  description unedited.

### Finding a task: `task find`

Lists all tasks with description containing the `KEYWORD`

Format: `task find td/KEYWORD`

* Only matches the `KEYWORD` to the task's description and displays it if it contains `KEYWORD`

Example of usage:

* `task find td/Jean` Displays all tasks with the `KEYWORD` "Jean" in its description.

### Listing all tasks: `task list`

Lists all tasks, completion status, due date and time.

Format: `task list`

Example of usage:

`task list`

### Shift List

### Adding a shift: `shift add`

Adds a shift, including the date, start time, end time and assigned task.

Format: `shift add s/START_TIME e/END_TIME d/DATE st/TASK_DESCRIPTION`

* Adds a shift with a time range and task for a specific date
* `START_TIME` and `END_TIME` must be in HH:mm format
* `DATE` must be in YYYY-MM-DD format

Example:

`shift add s/08:00 e/12:00 d/2025-04-10 st/Morning ward round`

### Editing a shift: `shift edit`

Edits an existing shift in the shift list.

Format: `shift edit id/SHIFT_INDEX s/[NEW_START_TIME] e/[NEW_END_TIME] d/[NEW_DATE] st/[NEW_TASK]`

* Edits the shift at the specified `SHIFT_INDEX`. The `SHIFT_INDEX` refers to the index number shown in the displayed
  shift list.
* All fields must be provided to perform the edit.
* `START_TIME` and `END_TIME` must be in HH:mm format
* `DATE` must be in YYYY-MM-DD format

Example:

`shift edit id/2 s/14:00 e/18:00 d/2025-04-12 st/Afternoon ER duty`

### Marking a shift: `shift mark`

Marks a shift to show its completion.

Format: `shift mark id/SHIFT_INDEX`

* Marks the shift with index `SHIFT_INDEX` as completed
* `SHIFT_INDEX` must be a number between 1 to the total number of shifts in the list.

Example:

`shift mark id/1`

### Unmarking a shift: `shift unmark`

Unmarks a shift to show that it is uncompleted.

Format: `shift unmark id/SHIFT_INDEX`

* Unmarks the shift with index `SHIFT_INDEX` as uncompleted
* `SHIFT_INDEX` must be a number between 1 to the total number of shifts in the list.

Example:

`shift unmark id/1`

### Deleting a shift: `shift del`

Deletes a shift from the list.

Format: `shift del id/SHIFT_INDEX`

* Deletes a shift with the specified index
* `SHIFT_INDEX` must be a number between 1 to the total number of shifts in the list.

Example:

`shift del id/3`

### Sorting shifts chronologically: `shift sort`

Sorts all existing shifts in the list in chronological order based on date and start time.

Format: `shift sort`

* After sorting, the updated order will be reflected when listing shifts.
* Sorting is based first on `DATE`, then on `START_TIME`.

Example:

`shift sort`

### Logging overtime for a shift: `shift logot`

Logs overtime hours worked for a shift.

Format: `shift logot id/SHIFT_INDEX h/OVERTIME_HOURS`

* Records the number of overtime hours worked for the shift at `SHIFT_INDEX`
* `OVERTIME_HOURS` must be a positive decimal or integer (e.g. 2 or 1.5)

Example:

`shift logot id/1 h/2.5`

### Listing all shifts: `shift list`

Lists all shifts, completion status, date, time, and task.

Format: `shift list`

Example:

`shift list`

### Patient List

### Adding a patient profile : `pf add`

Creates a profile for a patient.

Format: `pf add id/ID_NUMBER p/PATIENT_NAME a/AGE g/GENDER c/CONTACT n/[NOTES]`

* Adds a profile for the specified patient: `PATIENT_NAME`.
* Names are allowed to contain any characters or even numbers.
* ID_NUMBER must be 4 digits only.
* CONTACT must be 8 digits only.
* Leading zeros for age and contact are removed.
* All fields must be provided except for NOTES, n/ is still required.

Example:

`pf add id/1001 p/Jean a/25 g/F c/66887799 n/requires constant supervision`

This adds a profile for a patient with ID 1001 named Jean, 25 years old and is female. Jean also has a family contact
number and an additional note which mentions that she needs constant supervision.

### Deleting a patient profile : `pf del`

Delete a patient profile.

Format: `pf del id/ID_NUMBER`

* Deletes a profile for a patient with the specified ID number.
* The ID number for the patient should currently exist in the list for patient information.

Example:

* `pf del id/1000`

### Finding a patient profile : `pf find`

Search for a patient profile.

Format: `pf find id/ID_NUMBER`

* Search the patient profile by the patient's ID.
* Displays the patient information if the ID is found within the current list.
* The ID number for the patient should currently exist in the list for patient information.

Example:

* `pf find id/1000`

### Listing all patient profiles : `pf list`

List out patient information for all patients within the list.

Format: `pf list`

* Displays all patient information stored in the list.
* If notes is empty, "No notes were given." will be displayed by default.

### Editing a patient information : `pf edit`

Edit patient information based on the patient selected.

Format: `pf edit id/ID_NUMBER p/[PATIENT_NAME] a/[AGE] g/[GENDER] c/[CONTACT] n/[NOTES]`

* The selected profile depends on the patient ID.
* If there is a need to only edit certain fields, only include the required identifiers.
* At least one field should be updated.

Example:

* `pf edit id/1000 p/Jean a/22 g/F c/12345678 n/Allergic to peanuts`
* `pf edit id/1000 p/Jean`

In both examples, the chosen fields are updated for the patient accordingly.

### Adding a medical test result : `pf result add`

Add medical tests and the results for the patient.

Format: `pf result add id/ID_NUMBER t/TEST_NAME r/RESULTS`

* Creates a medical test and the result for the patient based on the ID given.
* Multiple entries of the test and result can be added.

Example:

* `pf result add id/1000 t/X-Ray r/Fractures found.`
* `pf result add id/1000 t/Blood test r/No abnormalities found.`

### Deleting a medical test result : `pf result delete`

Deletes *all* medical tests for the patient.

Format: `pf result del id/ID_NUMBER`

* Deletes all medical tests and results for the patient based on the ID given.

Example:

* `pf result del id/1000`

### Listing all medical test results : `pf result list`

List out the medical tests for the patient.

Format: `pf result list id/[ID_NUMBER]`

Example:

* `pf result list id/1000`

### Appointment list

### Adding an appointment: `appt add`

Adds user’s appointments, date and time to the user’s appointment list.

Format: `appt add id/PATIENT_ID s/START_TIME e/END_TIME d/DATE [im/IMPORTANCE_RANKING] [n/NOTES]`

* Adds an appointment for the patient with the patient ID: `PATIENT_ID`
* **Note:** Appointments can only be added for patients already in the patients list
* `START_TIME` and `END_TIME` must be in HH:mm format
* `DATE` must be in YYYY-MM-DD format
* `IMPORTANCE_RANKING` must be an integer between 1 and 3. (LOW, MEDIUM, HIGH)
* `IMPORTANCE_RANKING` and `NOTES` are optional fields. Omit `im/` or `n/` if field is not provided.
* Appointments with unassigned `IMPORTANCE_RANKING` are given an importance ranking of `MEDIUM` by default

Example:

`appt add id/1000 s/13:00 e/14:00 d/2025-02-12 im/2 n/super healthy`

### Deleting an appointment: `appt del`

Delete an appointment profile.

Format: `appt del aid/APPT_INDEX`

* Deletes an appointment with the specified index.
* The `APPT_INDEX` refers to the index number shown in the displayed appointment
  list. The index must be a positive integer 1, 2, 3, ...

Example: `appt del aid/10`

### Marking an appointment: `appt mark`

Marks an appointment to show its completion.

Format: `appt mark aid/APPT_INDEX`

* Marks appointment with index `APPT_INDEX` as completed
* The `APPT_INDEX` refers to the index number shown in the displayed appointment
  list. The index must be a positive integer 1, 2, 3, ...

Example of usage:

`appt mark aid/3`

### Unmarking an appointment: `appt unmark`

Unmarks an appointment to show that it is uncompleted.

Format: `appt unmark aid/APPT_INDEX`

* Unmarks appointment with index `APPT_INDEX` as uncompleted
* The `APPT_INDEX` refers to the index number shown in the displayed appointment
  list. The index must be a positive integer 1, 2, 3, ...

Example of usage:

`appt unmark aid/3`

### Editing an appointment: `appt edit`

Edits an existing appointment in the appointment list.

Format:
`appt edit aid/APPT_INDEX [id/NEW_PATIENT_ID] [s/NEW_START_TIME] [e/NEW_END_TIME] [d/NEW_DATE] [n/NEW_NOTES] [im/NEW_IMPORTANCE]`

* Edits the appointment at the specified `APPT_INDEX` with. The `APPT_INDEX` refers to the index number shown in the
  displayed
  appointment list. The index must be a positive integer 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example of usage:

* `appt edit aid/1 id/9999 s/13:00 e/15:00` Edits the appointment patient ID, start time and end time of the 1st
  appointment in the appointment list.
* `appt edit aid/3 im/1` Edits the importance ranking of the 3rd appointment in the appointment list.

### Listing all appointments: `appt list`

Lists all appointments, completion status, date, start time, end time, importance and notes.

Format: `appt list`

Example of usage:

`appt list`

### Finding an appointment: `appt find`

Search for an appointment using patient's name or ID.

#### Finding using Patient Name

Displays all appointments under `PATIENT_NAME` found within the current list.

Format: `appt find p/ PATIENT_NAME`

Example of usage:

`appt find p/ Jean Doe`

#### Finding using Patient ID

Displays all appointments under `PATIENT_ID` found within the current list.

Format: `appt find id/ PATIENT_ID`

Example of usage:

`appt find id/1010`

### Sorting appointments: `appt sort`

Sorts appointments based off importance or time.

#### Sorting by Time

Sorts appointments in chronological order.

Format: `appt sort by/ time`

Example of usage:

`appt sort by/ time`

#### Sorting by Importance

Sorts appointments based on their priority level.

Format: `appt sort by/ importance`

Example of usage:

`appt sort by/ importance`

### Medicine List

### Adding a medicine quantity: `medicine add`

Adds a specific quantity of medicine to the current supply list.

Format: `medicine add mn/MEDICINE_NAME q/QUANTITY`

Example:

* `medicine add mn/paracetamol q/2` Adds 2 quantity of paracetamol into the medicine supply list.

### Removing a medicine quantity: `medicine remove`

Removes a specific quantity of medicine from the current supply list.

Format: `medicine remove mn/MEDICINE_NAME q/QUANTITY`

Example:

* `medicine remove mn/paracetamol q/2` Removes 2 quantity of paracetamol from the medicine supply list.

### Listing all medicine supply: `medicine list`

Lists names of medicines and their respective quantity in the medicine supply.

Format: `medicine list`

* Note that medicines with quantity 0 will appear in the `medicine list` as they are still relevant medicines that the
  user needs to restock.

### Finding a medicine name: `medicine find`

Finds a specific medicine from the current supply.

Format: `medicine find mn/MEDICINE_NAME`

Example:

* `medicine find mn/paracetamol` Finds paracetamol in the medicine supply list and displays it with its quantity.

### Deleting a medicine: `medicine delete`

Deletes a specific medicine from the current supply.

Format: `medicine delete mn/MEDICINE_NAME`

Example:

* `medicine delete mn/paracetamol` Deletes paracetamol from the medicine supply list.

### Editing a medicine: `medicine edit`

Edits the information of a specific medicine from the current supply.

Format: `medicine edit mn/MEDICINE_NAME un/UPDATED_NAME uq/UPDATED_QUANTITY`

* Edits `MEDICINE_NAME` to `UPDATED_NAME` and its respective `QUANTITY` to `UPDATED_QUANTITY`
* `UPDATED_NAME` cannot already exist in `medicine list`.
* `MEDICINE_NAME` and `UPDATED_NAME` can be the same so that users are able to update the quantity directly without
  adding/removing.

Example:

* `medicine edit mn/paracetamo un/paracetamol uq/4` Updates the medicine name from paracetamo to paracetamol and its
  quantity to 4.

### Restocking a medicine: `medicine restock`

Checks which medicine needs restocking based on the input quantity.

Format: `medicine restock q/QUANTITY`

* Lists all medicines which have quantity lesser than `QUANTITY`.

Example:

* `medicine restock q/30` Lists all the medicine name and their respective quantity, for medicines which have quantity
  lesser than 30.

### Other commands

### Exit: `exit ns`

Exits the NurseSched app.

Format: `exit ns`

## FAQ

[//]: # (todo)
**Q**: How do I transfer my data to another computer?

**A**: All the data is stored in the /data/ folder. Simply transfer this folder to the other computer.

**Q** What happens if the data file is corrupted?

**A** NurseSched will continue to run, but there may be unpredictable behaviour. Please ensure that the
corrupted file is handled accordingly.

## Command Summary

| List        | Action  | Format                                                                                                                            |
|-------------|---------|-----------------------------------------------------------------------------------------------------------------------------------|
| Task        | Add     | `task add td/TASK_DESCRIPTION d/DUE_DATE t/DUE_TIME`                                                                              |
| Task        | Delete  | `task del id/TASK_INDEX`                                                                                                          |
| Task        | Mark    | `task mark id/TASK_INDEX`                                                                                                         |
| Task        | Unmark  | `task unmark id/TASK_INDEX`                                                                                                       |
| Task        | Edit    | `task edit id/TASK_INDEX [td/NEW_DESCRIPTION] [d/NEW_DUE_DATE] [t/NEW_DUE_TIME]`                                                  |
| Task        | Find    | `task find td/KEYWORD`                                                                                                            |
| Task        | List    | `task list`                                                                                                                       |
| Shift       | Add     | `shift add s/START_TIME e/END_TIME d/DATE st/TASK_DESCRIPTION`                                                                    |
| Shift       | Edit    | `shift edit id/SHIFT_INDEX s/[NEW_START_TIME] e/[NEW_END_TIME] d/[NEW_DATE] st/[NEW_TASK]`                                        |
| Shift       | Mark    | `shift mark id/SHIFT_INDEX`                                                                                                       |
| Shift       | Unmark  | `shift unmark id/SHIFT_INDEX`                                                                                                     |
| Shift       | Delete  | `shift del id/SHIFT_INDEX`                                                                                                        |
| Shift       | LogOt   | `shift logot id/SHIFT_INDEX h/OVERTIME_HOURS`                                                                                     |
| Shift       | Sort    | `shift sort`                                                                                                                      |
| Shift       | List    | `shift list`                                                                                                                      |
| Patient     | Add     | `pf add id/ID_NUMBER p/PATIENT_NAME a/AGE g/GENDER c/CONTACT n/[NOTES]`                                                           |
| Patient     | Delete  | `pf del id/ID_NUMBER`                                                                                                             |
| Patient     | Find    | `pf find id/ID_NUMBER`                                                                                                            |
| Patient     | List    | `pf list`                                                                                                                         |
| Patient     | Edit    | `pf edit id/ID_NUMBER p/[PATIENT_NAME] a/[AGE] g/[GENDER] c/[CONTACT] n/[NOTES]`                                                  |
| MedicalTest | Add     | `pf result add id/ID_NUMBER t/TEST_NAME r/RESULTS`                                                                                |
| MedicalTest | Delete  | `pf result del id/ID_NUMBER`                                                                                                      |
| MedicalTest | List    | `pf result list id/ID_NUMBER`                                                                                                     |
| Appointment | Add     | `appt add id/PATIENT_ID s/START_TIME e/END_TIME d/DATE [im/IMPORTANCE_RANKING] [n/NOTES]`                                         |
| Appointment | Delete  | `appt del aid/APPT_INDEX`                                                                                                         |
| Appointment | Mark    | `appt mark aid/APPT_INDEX`                                                                                                        |
| Appointment | Unmark  | `appt unmark aid/APPT_INDEX`                                                                                                      |
| Appointment | Edit    | `appt edit aid/APPT_INDEX [id/NEW_PATIENT_ID] [s/NEW_START_TIME] [e/NEW_END_TIME] [d/NEW_DATE] [n/NEW_NOTES] [im/NEW_IMPORTANCE]` |
| Appointment | List    | `appt list`                                                                                                                       |
| Appointment | Find    | `appt find p/PATIENT_NAME` or  `appt find id/PATIENT_ID`                                                                          |
| Appointment | Sort    | `appt sort by/time` or `appt sort by/importance`                                                                                  |
| Medicine    | Add     | `medicine add mn/MEDICINE_NAME q/QUANTITY`                                                                                        |
| Medicine    | Remove  | `medicine remove mn/MEDICINE_NAME q/QUANTITY`                                                                                     |
| Medicine    | List    | `medicine list`                                                                                                                   |
| Medicine    | Find    | `medicine find mn/MEDICINE_NAME`                                                                                                  |
| Medicine    | Delete  | `medicine delete mn/MEDICINE_NAME`                                                                                                |
| Medicine    | Edit    | `medicine edit mn/MEDICINE_NAME un/UPDATED_NAME uq/UPDATED_QUANTITY`                                                              |
| Medicine    | Restock | `medicine restock q/QUANTITY`                                                                                                     |
|             | Exit    | `exit ns`                                                                                                                         |
