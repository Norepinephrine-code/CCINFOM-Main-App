package controller;

import dao.REPORT_DoctorActivityDAO;
import model.REPORT_DoctorActivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class REPORT_DoctorActivityService {

    private static final Logger logger = Logger.getLogger(REPORT_DoctorActivityService.class.getName());
    private final REPORT_DoctorActivityDAO dao;

    public REPORT_DoctorActivityService(Connection connection) {
        this.dao = new REPORT_DoctorActivityDAO(connection);
    }

    public REPORT_DoctorActivity generateReport(int doctorId, String periodPattern) {
        try {
            return dao.getByDoctorAndPeriod(doctorId, periodPattern);
        } catch (SQLException e) {
            String msg = "Failed to generate doctor activity report: " + e.getMessage();
            logger.severe(msg);
            return null;
        }
    }
}
