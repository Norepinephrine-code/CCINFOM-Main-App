package controller;

import dao.MedicalHistoryDAO;
import dao.PatientDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.MedicalHistory;

public class MedicalHistoryService {
    private final MedicalHistoryDAO historyDAO;
    private final PatientDAO patientDAO;
    private static final Logger logger = Logger.getLogger(MedicalHistoryService.class.getName());

    public MedicalHistoryService(Connection conn) {
        this.historyDAO = new MedicalHistoryDAO(conn);
        this.patientDAO = new PatientDAO(conn);
    }

    /**
     * Adds a new medical history record after validating fields.
     */
    public ServiceResult addMedicalHistory(MedicalHistory history) {
        if (!isValidFields(history)) {
            String msg = "Validation failed: Missing required medical history fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(history.getPatientId())) {
            String msg = "Invalid patient ID: " + history.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = historyDAO.insert(history);
            if (success) {
                String msg = "Medical history added successfully.";
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add medical history.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert medical history: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates medical history.
     */
    public ServiceResult updateMedicalHistory(MedicalHistory history) {
        if (!isValidFields(history)) {
            String msg = "Validation failed: Missing required medical history fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(history.getHistoryId())) {
            String msg = "Validation failed: History ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(history.getPatientId())) {
            String msg = "Invalid patient ID: " + history.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = historyDAO.update(history);
            if (success) {
                String msg = "Medical history updated: ID " + history.getHistoryId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update medical history: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a medical history record.
     */
    public ServiceResult deleteMedicalHistory(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: History record with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = historyDAO.delete(id);
            if (success) {
                String msg = "Medical history deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete medical history.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete medical history: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all medical history records.
     */
    public List<MedicalHistory> getAllMedicalHistory() {
        try {
            return historyDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch medical history: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets medical history by ID.
     */
    public MedicalHistory getMedicalHistoryById(int id) {
        try {
            return historyDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch medical history by ID: " + e.getMessage());
            return null;
        }
    }

    private boolean isValidFields(MedicalHistory h) {
        return h.getPatientId() > 0;
    }

    private boolean isValidId(int id) {
        try {
            return historyDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check history ID: " + e.getMessage());
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
}
