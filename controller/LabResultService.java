package controller;

import dao.LabResultDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.LabResult;

public class LabResultService {
    private final LabResultDAO labResultDAO;
    private static final Logger logger = Logger.getLogger(LabResultService.class.getName());

    public LabResultService(Connection conn) {
        this.labResultDAO = new LabResultDAO(conn);
    }

    /**
     * Adds a new lab result after validating fields.
     */
    public ServiceResult addLabResult(LabResult result) {
        if (!isValidFields(result)) {
            String msg = "Validation failed: Missing required lab result fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = labResultDAO.insert(result);
            if (success) {
                String msg = "Lab result added successfully.";
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add lab result.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert lab result: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a lab result.
     */
    public ServiceResult updateLabResult(LabResult result) {
        if (!isValidFields(result)) {
            String msg = "Validation failed: Missing required lab result fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(result.getLabResultId())) {
            String msg = "Validation failed: Lab result ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = labResultDAO.update(result);
            if (success) {
                String msg = "Lab result updated: ID " + result.getLabResultId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update lab result: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a lab result by ID.
     */
    public ServiceResult deleteLabResult(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Lab result with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = labResultDAO.delete(id);
            if (success) {
                String msg = "Lab result deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete lab result.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete lab result: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all lab results.
     */
    public List<LabResult> getAllLabResults() {
        try {
            return labResultDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all lab results: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a lab result by ID.
     */
    public LabResult getLabResultById(int id) {
        try {
            return labResultDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch lab result by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validate lab result fields.
     */
    private boolean isValidFields(LabResult r) {
        return r.getOrderingPhysicianId() > 0 &&
               r.getPatientId() > 0 &&
               r.getProcedureId() > 0 &&
               r.getResult() != null && !r.getResult().isEmpty() &&
               r.getDate() != null;
    }

    /**
     * Check if lab result ID exists.
     */
    private boolean isValidId(int id) {
        try {
            return labResultDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check lab result ID: " + e.getMessage());
            return false;
        }
    }
}
