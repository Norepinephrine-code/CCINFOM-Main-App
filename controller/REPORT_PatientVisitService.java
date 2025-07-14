package controller;

import dao.REPORT_PatientVisitDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.REPORT_PatientVisit;

public class REPORT_PatientVisitService {

    private static final Logger logger = Logger.getLogger(REPORT_PatientVisitService.class.getName());
    private final REPORT_PatientVisitDAO dao;

    public REPORT_PatientVisitService(Connection connection) {
        this.dao = new REPORT_PatientVisitDAO(connection);
    }

    public List<REPORT_PatientVisit> generateReport(String periodPattern) {
        try {
            return dao.getByPeriod(periodPattern);
        } catch (SQLException e) {
            String msg = "Failed to generate patient visit report: " + e.getMessage();
            logger.severe(msg);
            return null;
        }
    }
}