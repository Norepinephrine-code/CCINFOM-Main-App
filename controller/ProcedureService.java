package controller;

import dao.LabProcedureDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.LabProcedure;

public class ProcedureService {
    private final LabProcedureDAO procedureDAO;
    private static final Logger logger = Logger.getLogger(ProcedureService.class.getName());

    public ProcedureService(Connection conn) {
        this.procedureDAO = new LabProcedureDAO(conn);
    }

    /**
     * Adds a new lab procedure after validating fields.
     */
    public ServiceResult addProcedure(LabProcedure procedure) {
        if (!isValidFields(procedure)) {
            String msg = "Validation failed: Missing required procedure fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = procedureDAO.insert(procedure);
            if (success) {
                String msg = "Procedure added successfully: " + procedure.getName();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add procedure.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert procedure: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a lab procedure.
     */
    public ServiceResult updateProcedure(LabProcedure procedure) {
        if (!isValidFields(procedure)) {
            String msg = "Validation failed: Missing required procedure fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(procedure.getProcedureId())) {
            String msg = "Validation failed: Procedure ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = procedureDAO.update(procedure);
            if (success) {
                String msg = "Procedure updated: ID " + procedure.getProcedureId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update procedure: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a procedure by ID.
     */
    public ServiceResult deleteProcedure(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Procedure with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = procedureDAO.delete(id);
            if (success) {
                String msg = "Procedure deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete procedure.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete procedure: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all lab procedures.
     */
    public List<LabProcedure> getAllProcedures() {
        try {
            return procedureDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all procedures: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a lab procedure by ID.
     */
    public LabProcedure getProcedureById(int id) {
        try {
            return procedureDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch procedure by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validate procedure fields.
     */
    private boolean isValidFields(LabProcedure p) {
        return p.getName() != null && !p.getName().isEmpty() &&
               p.getCategory() != null && !p.getCategory().isEmpty() &&
               p.getCost() != null;
    }

    /**
     * Check if procedure ID exists.
     */
    private boolean isValidId(int id) {
        try {
            return procedureDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check procedure ID: " + e.getMessage());
            return false;
        }
    }
}
