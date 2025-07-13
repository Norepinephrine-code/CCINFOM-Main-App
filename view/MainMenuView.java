package view;

import controller.PatientService;
import controller.DoctorService;
import controller.DiseaseService;
import controller.ProcedureService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.sql.Connection;

public class MainMenuView extends JFrame {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DiseaseService diseaseService;
    private final ProcedureService procedureService;

    public MainMenuView(Connection conn) {
        // Initialize services
        this.patientService = new PatientService(conn);
        this.doctorService = new DoctorService(conn);
        this.diseaseService = new DiseaseService(conn);
        this.procedureService = new ProcedureService(conn);

        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton patientBtn = new JButton("🧍 Patients");
        JButton doctorBtn = new JButton("👨‍⚕️ Doctors");
        JButton diseaseBtn = new JButton("🦠 Diseases");
        JButton procedureBtn = new JButton("💉 Procedures");

        add(patientBtn);
        add(doctorBtn);
        add(diseaseBtn);
        add(procedureBtn);

        // Events
        patientBtn.addActionListener(e -> new PatientView(patientService).setVisible(true));
        doctorBtn.addActionListener(e -> new DoctorView(doctorService).setVisible(true));
        diseaseBtn.addActionListener(e -> new DiseaseView(diseaseService).setVisible(true));
        procedureBtn.addActionListener(e -> new ProcedureView(procedureService).setVisible(true));
    }
}
