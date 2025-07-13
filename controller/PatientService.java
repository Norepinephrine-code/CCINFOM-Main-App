package controller;

import dao.PatientDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.Patient;

public class PatientService {
    private final PatientDAO patientDAO;
    private static final Logger logger = Logger.getLogger(PatientService.class.getName());

    public PatientService(Connection conn) {
        this.patientDAO = new PatientDAO(conn);
    }

    /**
     * Adds a new patient after validating fields.
     * Constraints:
     * 1. No duplicate entry
     */
    public ServiceResult addPatient(Patient patient) {
        if (!isValidFields(patient)) {
            String msg = "Validation failed: Missing required patient fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = patientDAO.insert(patient);
            if (success) {
                String msg = "Patient added successfully: " + patient.getFirstName() + " " + patient.getLastName();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                String msg = "Failed to add patient.";
                logger.info(msg);
                return new ServiceResult(false, msg);
            }
        } catch (SQLException e) {
            String msg = "Failed to insert patient: " + e.getMessage();

            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }


    /**
     * Constraint:
     * 1. Make sure that patient exists in the first place
     */
    public ServiceResult updatePatient(Patient patient) {
        if (!isValidFields(patient)) {
            String msg = "Validation failed: Missing required patient fields.";

            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(patient.getPatientId())) {
            String msg = "Validation failed: Patient ID does not exist.";

            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = patientDAO.update(patient);
            if (success) {
                String msg = "Patient updated: ID " + patient.getPatientId();

                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                String msg = "Update failed.";

                logger.info(msg);
                return new ServiceResult(false, msg);
            }
        } catch (SQLException e) {
            String msg = "Failed to update patient: " + e.getMessage();

            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a patient by ID.
     */
    public ServiceResult deletePatient(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Patient with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = patientDAO.delete(id);
            if (success) {
                String msg = "Patient deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete patient.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete patient: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all patients from the database.
     */
    public List<Patient> getAllPatients() {
        try {
            return patientDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all patients: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a single patient by ID.
     */
    public Patient getPatientById(int id) {
        try {
            return patientDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch patient by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validates the required fields of a patient.
     */
    private boolean isValidFields(Patient p) {
        return p.getFirstName() != null && !p.getFirstName().isEmpty() &&
               p.getLastName() != null && !p.getLastName().isEmpty() &&
               p.getGender() != null && !p.getGender().isEmpty() &&
               p.getDateOfBirth() != null;
    }

    /**
     * Checks if a patient ID exists.
     */
    private boolean isValidId(int id) {
        try {
            return patientDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check patient ID: " + e.getMessage());
            return false;
        }
    }
}
