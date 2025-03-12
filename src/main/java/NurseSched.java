import java.util.Scanner;
import java.util.ArrayList;
import appointment.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

public class NurseSched {
    /**
     * Main entry-point for the NurseSched application.
     */
    public static void main(String[] args) {

        ArrayList<Appointment> apptList = new ArrayList<>();

        //test Appointment class methods
        String name = "James Gosling";
        String notes = "Test notes";
        LocalTime startTime = LocalTime.now();
        LocalDate date = LocalDate.now();

        Appointment.addAppt(apptList, name, startTime, startTime, date, notes);
        System.out.println("List after adding:" + apptList);

        System.out.println("Status of appointment: " + apptList.get(0).getStatus());
        Appointment.markApptByIndex(apptList,0);
        System.out.println("Status of appointment after marking by index: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByIndex(apptList,0);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.markApptByPatient(apptList,name, startTime,date);
        System.out.println("Status of appointment after marking by Patient name: " + apptList.get(0).getStatus());

        Appointment.unmarkApptByPatient(apptList,name, startTime,date);
        System.out.println("Status of appointment after unmarking by index: " + apptList.get(0).getStatus());

        Appointment.deleteApptByPatient(apptList,name, startTime,date);
        System.out.println("List after deleting: " + apptList);

        boolean isRunning = false;
        while (isRunning) {
            Scanner input = new Scanner(System.in);
        }
    }
}
