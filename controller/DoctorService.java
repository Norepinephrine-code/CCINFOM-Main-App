package controller;

import dao.DoctorDAO;
import dto.ServiceResult;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.Doctor;

public class DoctorService {
    private final DoctorDAO doctorDAO;
    private static final Logger logger = Logger.getLogger(DoctorService.class.getName());

    public DoctorService(Connection conn) {
        this.doctorDAO = new DoctorDAO(conn);
    }

    /**
     * Adds a new doctor after validating fields.
     */
    public ServiceResult addDoctor(Doctor doctor) {
        if (!isValidFields(doctor)) {
            String msg = "Validation failed: Missing required doctor fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = doctorDAO.insert(doctor);
            if (success) {
                String msg = "Doctor added successfully: " + doctor.getFullName();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to add doctor.");
            }
        } catch (SQLException e) {
            String msg = "Failed to insert doctor: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Updates a doctor's info.
     */
    public ServiceResult updateDoctor(Doctor doctor) {
        if (!isValidFields(doctor)) {
            String msg = "Validation failed: Missing required doctor fields.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        if (!isValidId(doctor.getDoctorId())) {
            String msg = "Validation failed: Doctor ID does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = doctorDAO.update(doctor);
            if (success) {
                String msg = "Doctor updated: ID " + doctor.getDoctorId();
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Update failed.");
            }
        } catch (SQLException e) {
            String msg = "Failed to update doctor: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Deletes a doctor by ID.
     */
    public ServiceResult deleteDoctor(int id) {
        if (!isValidId(id)) {
            String msg = "Delete failed: Doctor with ID " + id + " does not exist.";
            logger.warning(msg);
            return new ServiceResult(false, msg);
        }

        try {
            boolean success = doctorDAO.delete(id);
            if (success) {
                String msg = "Doctor deleted: ID " + id;
                logger.info(msg);
                return new ServiceResult(true, msg);
            } else {
                return new ServiceResult(false, "Failed to delete doctor.");
            }
        } catch (SQLException e) {
            String msg = "Failed to delete doctor: " + e.getMessage();
            logger.severe(msg);
            return new ServiceResult(false, msg);
        }
    }

    /**
     * Gets all doctors from the database.
     */
    public List<Doctor> getAllDoctors() {
        try {
            return doctorDAO.getAll();
        } catch (SQLException e) {
            logger.severe("Failed to fetch all doctors: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a single doctor by ID.
     */
    public Doctor getDoctorById(int id) {
        try {
            return doctorDAO.getById(id);
        } catch (SQLException e) {
            logger.severe("Failed to fetch doctor by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validates the required fields of a doctor.
     */
    private boolean isValidFields(Doctor d) {
        return d.getFullName() != null && !d.getFullName().isEmpty() &&
               d.getSpecialization() != null && !d.getSpecialization().isEmpty();
    }

    /**
     * Checks if a doctor ID exists.
     */
    private boolean isValidId(int id) {
        try {
            return doctorDAO.getById(id) != null;
        } catch (SQLException e) {
            logger.severe("Failed to check doctor ID: " + e.getMessage());
            return false;
        }
    }
}
