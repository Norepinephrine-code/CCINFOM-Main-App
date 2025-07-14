package controller;

import dao.DiagnosisDAO;
import dao.PatientDAO;
import dao.DoctorDAO;
import dao.DiseaseDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.Diagnosis;

public class DiagnosisService {
    private final DiagnosisDAO diagnosisDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final DiseaseDAO diseaseDAO;
    private static final Logger logger = Logger.getLogger(DiagnosisService.class.getName());

    public DiagnosisService(Connection conn) {
        this.diagnosisDAO = new DiagnosisDAO(conn);
        this.patientDAO = new PatientDAO(conn);
        this.doctorDAO = new DoctorDAO(conn);
        this.diseaseDAO = new DiseaseDAO(conn);
    }

    /**
     * Adds a new diagnosis after validating fields.
     */
    public ServiceResult addDiagnosis(Diagnosis diagnosis) {
        if (!isValidFields(diagnosis)) {
            String msg = "Validation failed: Missing required diagnosis fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(diagnosis.getPatientId())) {
            String msg = "Invalid patient ID: " + diagnosis.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!doctorExists(diagnosis.getDoctorId())) {
            String msg = "Invalid doctor ID: " + diagnosis.getDoctorId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!diseaseExists(diagnosis.getDiseaseId())) {
            String msg = "Invalid disease ID: " + diagnosis.getDiseaseId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diagnosisDAO.insert(diagnosis);
            if (success) {
                String msg = "Diagnosis added successfully.";
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add diagnosis.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert diagnosis: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a diagnosis.
     */
    public ServiceResult updateDiagnosis(Diagnosis diagnosis) {
        if (!isValidFields(diagnosis)) {
            String msg = "Validation failed: Missing required diagnosis fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(diagnosis.getDiagnosisId())) {
            String msg = "Validation failed: Diagnosis ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(diagnosis.getPatientId())) {
            String msg = "Invalid patient ID: " + diagnosis.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!doctorExists(diagnosis.getDoctorId())) {
            String msg = "Invalid doctor ID: " + diagnosis.getDoctorId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!diseaseExists(diagnosis.getDiseaseId())) {
            String msg = "Invalid disease ID: " + diagnosis.getDiseaseId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diagnosisDAO.update(diagnosis);
            if (success) {
                String msg = "Diagnosis updated: ID " + diagnosis.getDiagnosisId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update diagnosis: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a diagnosis by ID.
     */
    public ServiceResult deleteDiagnosis(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Diagnosis with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diagnosisDAO.delete(id);
            if (success) {
                String msg = "Diagnosis deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete diagnosis.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete diagnosis: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all diagnoses.
     */
    public List<Diagnosis> getAllDiagnoses() {
        try {
            return diagnosisDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all diagnoses: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a diagnosis by ID.
     */
    public Diagnosis getDiagnosisById(int id) {
        try {
            return diagnosisDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch diagnosis by ID: " + e.getMessage());
            return null;
        }
    }

    private boolean isValidFields(Diagnosis d) {
        return d.getPatientId() > 0 && d.getDoctorId() > 0 && d.getDiseaseId() > 0 && d.getDateDiagnosis() != null;
    }

    private boolean isValidId(int id) {
        try {
            return diagnosisDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check diagnosis ID: " + e.getMessage());
            return false;
        }
    }

    private boolean patientExists(int id) {
        try {
            return patientDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check patient ID: " + e.getMessage());
            return false;
        }
    }

    private boolean doctorExists(int id) {
        try {
            return doctorDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check doctor ID: " + e.getMessage());
            return false;
        }
    }

    private boolean diseaseExists(int id) {
        try {
            return diseaseDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check disease ID: " + e.getMessage());
            return false;
        }
    }
}
