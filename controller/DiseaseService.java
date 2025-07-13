package controller;

import dao.DiseaseDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.Disease;

public class DiseaseService {
    private final DiseaseDAO diseaseDAO;
    private static final Logger logger = Logger.getLogger(DiseaseService.class.getName());

    public DiseaseService(Connection conn) {
        this.diseaseDAO = new DiseaseDAO(conn);
    }

    /**
     * Adds a new disease after validating fields.
     */
    public ServiceResult addDisease(Disease disease) {
        if (!isValidFields(disease)) {
            String msg = "Validation failed: Missing required disease fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diseaseDAO.insert(disease);
            if (success) {
                String msg = "Disease added successfully: " + disease.getDiseaseName();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add disease.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert disease: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a disease.
     */
    public ServiceResult updateDisease(Disease disease) {
        if (!isValidFields(disease)) {
            String msg = "Validation failed: Missing required disease fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(disease.getDiseaseId())) {
            String msg = "Validation failed: Disease ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diseaseDAO.update(disease);
            if (success) {
                String msg = "Disease updated: ID " + disease.getDiseaseId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update disease: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a disease by ID.
     */
    public ServiceResult deleteDisease(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Disease with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = diseaseDAO.delete(id);
            if (success) {
                String msg = "Disease deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete disease.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete disease: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all diseases.
     */
    public List<Disease> getAllDiseases() {
        try {
            return diseaseDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all diseases: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a disease by ID.
     */
    public Disease getDiseaseById(int id) {
        try {
            return diseaseDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch disease by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validates required disease fields.
     */
    private boolean isValidFields(Disease d) {
        return d.getDiseaseName() != null && !d.getDiseaseName().isEmpty();
    }

    /**
     * Checks if a disease ID exists.
     */
    private boolean isValidId(int id) {
        try {
            return diseaseDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check disease ID: " + e.getMessage());
            return false;
        }
    }
}
