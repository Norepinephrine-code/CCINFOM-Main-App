package dao;

import model.REPORT_LabProcedure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class REPORT_LabProcedureDAO {

    private Connection connection;

    public REPORT_LabProcedureDAO(Connection connection) {
        this.connection = connection;
    }

    public List<REPORT_LabProcedure> getByPeriod(String periodPattern) throws SQLException {
        String sql = "SELECT lp.procedure_id, lp.procedure_name, p.patient_id, CONCAT(p.first_name, ' ', p.last_name) AS patient_name, lp.procedure_date " +
                     "FROM LabProcedure lp " +
                     "JOIN Patient p ON lp.patient_id = p.patient_id " +
                     "WHERE lp.procedure_date LIKE ? " +
                     "ORDER BY lp.procedure_date ASC";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, periodPattern + "%");

        ResultSet rs = ps.executeQuery();
        List<REPORT_LabProcedure> procedures = new ArrayList<>();

        while (rs.next()) {
            procedures.add(new REPORT_LabProcedure(
                rs.getInt("procedure_id"),
                rs.getString("procedure_name"),
                rs.getInt("patient_id"),
                rs.getString("patient_name"),
                rs.getString("procedure_date")
            ));
        }

        return procedures;
    }
}
