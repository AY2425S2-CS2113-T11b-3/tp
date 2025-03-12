package appointment;

import java.util.ArrayList;

public class Appointment {
    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private String notes;

    public Appointment() {
        name = "";
        startTime = "";
        endTime = "";
        date = "";
        notes = "";
    }

    public Appointment(String name, String startTime, String endTime, String date, String notes) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
    }

    public static void addAppt(ArrayList<Appointment> apptList, String name,
                               String startTime, String endTime, String date, String notes) {
        apptList.add(new Appointment(name, startTime, endTime, date, notes));
    }

    @Override
    public String toString() {
        return "Name: " + name + ", " +
                "From: " + startTime + ", " +
                "To: " + endTime + ", " +
                "Date: " + date + ", " +
                "Notes: " + notes;
    }
}


