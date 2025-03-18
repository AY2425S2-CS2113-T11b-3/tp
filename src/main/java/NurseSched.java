import java.util.Scanner;

import command.Command;
import ui.Ui;
import appointment.Appointment;
import exception.NurseSchedException;
import parser.ShiftParser;
import parser.ApptParser;
import parser.Parser;
import parser.PatientParser;
import patient.Patient;
import shift.Shift;

public class NurseSched {
    private final Ui ui;


    public NurseSched() {
        ui = new Ui();
    }

    public void run() {
        String input = null;
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        ui.greetingMessage();

        Command.executeCommands(isExit, in, ui);
    }

    public static void main(String[] args) {
        new NurseSched().run();
    }
}
