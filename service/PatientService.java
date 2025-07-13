package service;

import dao.PatientDAO;
import model.Patient;

import java.util.List;

public class PatientService {
    private PatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = new PatientDAO();
    }

    // Add a patient after validating input
    public boolean addPatient(Patient patient) {
        if (patient.getName() == null || patient.getName().isEmpty()) {
            System.out.println("Validation failed: Name is empty");
            return false;
        }

        try {
            patientDAO.insertPatient(patient);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving patient: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all patients
    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    // Optionally add delete/update later
}
