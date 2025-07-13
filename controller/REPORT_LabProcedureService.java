package service;

import dao.REPORT_LabProcedureDAO;
import model.REPORT_LabProcedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class REPORT_LabProcedureService {

    private REPORT_LabProcedureDAO dao;

    public REPORT_LabProcedureService(Connection connection) {
        this.dao = new REPORT_LabProcedureDAO(connection);
    }

    public List<REPORT_LabProcedure> generateReport(String periodPattern) {
        try {
            return dao.getByPeriod(periodPattern);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
