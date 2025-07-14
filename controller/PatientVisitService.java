package controller;

import dao.PatientVisitDAO;
import dao.PatientDAO;
import dao.DoctorDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.PatientVisit;

public class PatientVisitService {
    private final PatientVisitDAO visitDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private static final Logger logger = Logger.getLogger(PatientVisitService.class.getName());

    public PatientVisitService(Connection conn) {
        this.visitDAO = new PatientVisitDAO(conn);
        this.patientDAO = new PatientDAO(conn);
        this.doctorDAO = new DoctorDAO(conn);
    }

    /**
     * Adds a new patient visit after validating fields.
     */
    public ServiceResult addVisit(PatientVisit visit) {
        if (!isValidFields(visit)) {
            String msg = "Validation failed: Missing required visit fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(visit.getPatientId())) {
            String msg = "Invalid patient ID: " + visit.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!doctorExists(visit.getDoctorId())) {
            String msg = "Invalid doctor ID: " + visit.getDoctorId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = visitDAO.insert(visit);
            if (success) {
                String msg = "Patient visit added successfully.";
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add patient visit.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert patient visit: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a patient visit.
     */
    public ServiceResult updateVisit(PatientVisit visit) {
        if (!isValidFields(visit)) {
            String msg = "Validation failed: Missing required visit fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(visit.getVisitId())) {
            String msg = "Validation failed: Visit ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!patientExists(visit.getPatientId())) {
            String msg = "Invalid patient ID: " + visit.getPatientId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }
        if (!doctorExists(visit.getDoctorId())) {
            String msg = "Invalid doctor ID: " + visit.getDoctorId();
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = visitDAO.update(visit);
            if (success) {
                String msg = "Patient visit updated: ID " + visit.getVisitId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update patient visit: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a patient visit.
     */
    public ServiceResult deleteVisit(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Visit with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = visitDAO.delete(id);
            if (success) {
                String msg = "Patient visit deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete visit.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete patient visit: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all patient visits.
     */
    public List<PatientVisit> getAllVisits() {
        try {
            return visitDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all visits: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a visit by ID.
     */
    public PatientVisit getVisitById(int id) {
        try {
            return visitDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch visit by ID: " + e.getMessage());
            return null;
        }
    }

    private boolean isValidFields(PatientVisit v) {
        return v.getPatientId() > 0 && v.getDoctorId() > 0 && v.getReason() != null && !v.getReason().isEmpty() && v.getDateTime() != null;
    }

    private boolean isValidId(int id) {
        try {
            return visitDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check visit ID: " + e.getMessage());
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
}
