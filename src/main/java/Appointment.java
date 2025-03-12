import java.util.ArrayList;

public class Appointment {
    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private String notes;
    ArrayList<Appointment> apptList;

    public Appointment() {
        name = "";
        startTime = "";
        endTime = "";
        date = "";
        notes = "";
        apptList = new ArrayList<Appointment>();
    }

    public Appointment(String patient, String startTime, String endTime, String date, String notes) {
        this.name = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.notes = notes;
    }

    public void addPatient(String name, String startTime, String endTime, String date, String notes) {
        apptList.add(new Appointment(name, startTime, endTime, date, notes));
    }

    @Override
    public String toString() {
        return "Name: " + name + ", " + "From: " + startTime + ", " + "To: " + endTime + ", " + "Date: " + date + ", " + "Notes: " + notes;
    }
}

