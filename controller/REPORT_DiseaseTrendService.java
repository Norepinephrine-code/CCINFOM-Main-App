package controller;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

import model.REPORT_DiseaseTrend;
import dao.REPORT_DiseaseTrendDAO;

public class REPORT_DiseaseTrendService {

    private REPORT_DiseaseTrendDAO dao;

    public REPORT_DiseaseTrendService(Connection connection) {
        this.dao = new REPORT_DiseaseTrendDAO(connection);
    }

    public List<REPORT_DiseaseTrend> generateReport(String periodPattern) {
        try {
            return dao.getDiseaseTrendsByPeriod(periodPattern);
        } catch (SQLException e) {
            String msg = "Failed to insert doctor: " + e.getMessage();
            logger.severe(msg);
            return null;
        }
    }
}
