package view;

import model.Patient;
import service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PatientForm extends JFrame {
    private JTextField nameField;
    private JButton saveButton;
    private JButton showPatientsButton;
    private JTextArea displayArea;

    private PatientService patientService;

    public PatientForm() {
        patientService = new PatientService();

        setTitle("Patient Registration");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        nameField = new JTextField(20);
        saveButton = new JButton("Save");
        showPatientsButton = new JButton("Show All");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(saveButton);
        inputPanel.add(showPatientsButton);

        // Display area in center
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Save patient
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            Patient patient = new Patient();
            patient.setName(name);

            if (patientService.addPatient(patient)) {
                JOptionPane.showMessageDialog(this, "Patient saved!");
                nameField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid name.");
            }
        });

        // Show all patients
        showPatientsButton.addActionListener(e -> {
            List<Patient> patients = patientService.getAllPatients();
            displayArea.setText("");
            for (Patient p : patients) {
                displayArea.append("ID: " + p.getId() + ", Name: " + p.getName() + "\n");
            }
        });
    }
}
