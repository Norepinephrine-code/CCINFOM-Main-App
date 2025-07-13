package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.REPORT_PatientVisit;

public class REPORT_PatientVisitDAO {

    private Connection connection;

    public REPORT_PatientVisitDAO(Connection connection) {
        this.connection = connection;
    }

    public List<REPORT_PatientVisit> getByPeriod(String periodPattern) throws SQLException {
        String sql = "SELECT pv.visit_id, p.patient_id, CONCAT(p.first_name, ' ', p.last_name) AS patient_name, pv.visit_date " +
                     "FROM PatientVisit pv " +
                     "JOIN Patient p ON pv.patient_id = p.patient_id " +
                     "WHERE pv.visit_date LIKE ? " +
                     "ORDER BY pv.visit_date ASC";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, periodPattern + "%");

        ResultSet rs = ps.executeQuery();
        List<REPORT_PatientVisit> visits = new ArrayList<>();

        while (rs.next()) {
            visits.add(new REPORT_PatientVisit(
                rs.getInt("visit_id"),
                rs.getInt("patient_id"),
                rs.getString("patient_name"),
                rs.getString("visit_date")
            ));
        }

        return visits;
    }
}
