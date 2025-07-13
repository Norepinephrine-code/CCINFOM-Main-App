package service;

import dao.REPORT_DoctorActivityDAO;
import model.REPORT_DoctorActivity;

import java.sql.Connection;
import java.sql.SQLException;

public class REPORT_DoctorActivityService {

    private REPORT_DoctorActivityDAO dao;

    public REPORT_DoctorActivityService(Connection connection) {
        this.dao = new REPORT_DoctorActivityDAO(connection);
    }

    public REPORT_DoctorActivity generateReport(int doctorId, String periodPattern) {
        try {
            return dao.getByDoctorAndPeriod(doctorId, periodPattern);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
