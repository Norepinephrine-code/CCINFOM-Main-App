package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import model.REPORT_DiseaseTrend;
import dao.REPORT_DiseaseTrendDAO;

public class REPORT_DiseaseTrendService {

    private static final Logger logger = Logger.getLogger(REPORT_DiseaseTrendService.class.getName());
    private final REPORT_DiseaseTrendDAO dao;

    public REPORT_DiseaseTrendService(Connection connection) {
        this.dao = new REPORT_DiseaseTrendDAO(connection);
    }

    public List<REPORT_DiseaseTrend> generateReport(String periodPattern) {
        try {
            return dao.getDiseaseTrendsByPeriod(periodPattern);
        } catch (SQLException e) {
            String msg = "Failed to generate disease trend report: " + e.getMessage();
            logger.severe(msg);
            return null;
        }
    }
}
