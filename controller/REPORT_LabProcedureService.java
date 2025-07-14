package service;

import dao.REPORT_LabProcedureDAO;
import model.REPORT_LabProcedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class REPORT_LabProcedureService {

    private static final Logger logger = Logger.getLogger(REPORT_LabProcedureService.class.getName());
    private final REPORT_LabProcedureDAO dao;

    public REPORT_LabProcedureService(Connection connection) {
        this.dao = new REPORT_LabProcedureDAO(connection);
    }

    public List<REPORT_LabProcedure> generateReport(String periodPattern) {
        try {
            return dao.getByPeriod(periodPattern);
        } catch (SQLException e) {
            String msg = "Failed to generate lab procedure report: " + e.getMessage();
            logger.severe(msg);
            return null;
        }
    }
}
