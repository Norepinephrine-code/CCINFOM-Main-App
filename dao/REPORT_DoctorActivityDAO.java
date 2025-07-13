package dao;

import model.REPORT_DoctorActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class REPORT_DoctorActivityDAO {

    private Connection connection;

    public REPORT_DoctorActivityDAO(Connection connection) {
        this.connection = connection;
    }

    public REPORT_DoctorActivity getByDoctorAndPeriod(int doctorId, String periodPattern) throws SQLException {
        int visits = 0, diagnoses = 0, labOrders = 0;
        String doctorName = "";

        // Get doctor name
        PreparedStatement nameStmt = connection.prepareStatement(
            "SELECT CONCAT(first_name, ' ', last_name) AS name FROM Doctor WHERE doctor_id = ?");
        nameStmt.setInt(1, doctorId);
        ResultSet nameRs = nameStmt.executeQuery();
        if (nameRs.next()) doctorName = nameRs.getString("name");

        // Count visits
        PreparedStatement visitStmt = connection.prepareStatement(
            "SELECT COUNT(*) AS total FROM PatientVisit WHERE doctor_id = ? AND visit_date LIKE ?");
        visitStmt.setInt(1, doctorId);
        visitStmt.setString(2, periodPattern + "%");
        ResultSet visitRs = visitStmt.executeQuery();
        if (visitRs.next()) visits = visitRs.getInt("total");

        // Count diagnoses
        PreparedStatement diagStmt = connection.prepareStatement(
            "SELECT COUNT(*) AS total FROM Diagnosis WHERE doctor_id = ? AND diagnosis_date LIKE ?");
        diagStmt.setInt(1, doctorId);
        diagStmt.setString(2, periodPattern + "%");
        ResultSet diagRs = diagStmt.executeQuery();
        if (diagRs.next()) diagnoses = diagRs.getInt("total");

        // Count lab orders
        PreparedStatement labStmt = connection.prepareStatement(
            "SELECT COUNT(*) AS total FROM LabProcedure WHERE doctor_id = ? AND procedure_date LIKE ?");
        labStmt.setInt(1, doctorId);
        labStmt.setString(2, periodPattern + "%");
        ResultSet labRs = labStmt.executeQuery();
        if (labRs.next()) labOrders = labRs.getInt("total");

        return new REPORT_DoctorActivity(doctorId, doctorName, visits, diagnoses, labOrders);
    }
}
