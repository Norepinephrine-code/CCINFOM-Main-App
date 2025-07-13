package controller;

import dao.REPORT_PatientVisitDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.REPORT_PatientVisit;

public class REPORT_PatientVisitService {

    private REPORT_PatientVisitDAO dao;

    public REPORT_PatientVisitService(Connection connection) {
        this.dao = new REPORT_PatientVisitDAO(connection);
    }

    public List<REPORT_PatientVisit> generateReport(String periodPattern) {
        try {
            return dao.getByPeriod(periodPattern);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}