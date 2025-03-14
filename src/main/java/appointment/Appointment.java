package appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Appointment {
    protected static ArrayList<Appointment> apptList = new ArrayList<Appointment>();
    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;
    private final String notes;
    private boolean isDone = false;


    public Appointment() {
        name = "";
        startTime = null;
        endTime = null;
        date = null;
        notes = "";
    }

    public Appointment(String name, LocalTime startTime, LocalTime endTime, LocalDate date, String notes) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
    }

    public static void addAppt(String name,
                               LocalTime startTime, LocalTime endTime, LocalDate date, String notes) {

        //TODO: throw error if start/end/date is invalid
        apptList.add(new Appointment(name, startTime, endTime, date, notes));
    }

    public static void deleteApptByIndex(int index) {

        //TODO: throw error if index is invalid
        apptList.remove(index);
    }

    public static void deleteApptByPatient(String name,
                                           LocalTime startTime, LocalDate date) {

        //TODO: throw error if start/end/date is invalid
        Appointment appointment = findAppointment(name,startTime,date);
        apptList.remove(appointment);
    }

    public static void markApptByIndex(int index) {

        //TODO: throw error if index is invalid
        Appointment appointment = apptList.get(index);
        appointment.isDone = true;
    }

    public static void markApptByPatient(String name,
                                         LocalTime startTime, LocalDate date) {

        //TODO: throw error if index is invalid
        Appointment appointment = findAppointment(name,startTime,date);
        appointment.isDone = true;
    }

    public static void unmarkApptByIndex(int index) {

        //TODO: throw error if index is invalid
        Appointment appointment = apptList.get(index);
        appointment.isDone = false;
    }

    public static void unmarkApptByPatient(String name,
                                         LocalTime startTime, LocalDate date) {

        //TODO: throw error if index is invalid
        Appointment appointment = findAppointment(name,startTime,date);
        appointment.isDone = false;
    }

    public static Appointment findAppointment(String name,
                                              LocalTime startTime, LocalDate date) {
        for (Appointment appointment : apptList) {
            if (appointment.name.toLowerCase().contains(name.toLowerCase())
                    && appointment.startTime.equals(startTime)
                    && appointment.date.equals(date)) {

                return appointment;
            }
        }
        //TODO: throw appointment not found error
        return null;
    }

    public boolean getStatus() {
        return this.isDone;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        return "Name: " + name + ", " +
                "From: " + formattedStartTime + ", " +
                "To: " + formattedEndTime + ", " +
                "Date: " + date + ", " +
                "Notes: " + notes;
    }
}
