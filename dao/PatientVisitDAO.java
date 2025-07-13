package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.PatientVisit;

public class PatientVisitDAO {
    private final Connection conn;

    public PatientVisitDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert new patient visit
    public boolean insert(PatientVisit visit) throws SQLException {
        String sql = "INSERT INTO patient_visit (patient_id, doctor_id, reason, date_time, doctor_notes) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, visit.getPatientId());
        stmt.setInt(2, visit.getDoctorId());
        stmt.setString(3, visit.getReason());
        stmt.setTimestamp(4, visit.getDateTime());
        stmt.setString(5, visit.getDoctorNotes());

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                visit.setVisitId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete visit
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patient_visit WHERE visit_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }

    // Update visit
    public boolean update(PatientVisit visit) throws SQLException {
        String sql = "UPDATE patient_visit SET patient_id = ?, doctor_id = ?, reason = ?, date_time = ?, doctor_notes = ? WHERE visit_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, visit.getPatientId());
        stmt.setInt(2, visit.getDoctorId());
        stmt.setString(3, visit.getReason());
        stmt.setTimestamp(4, visit.getDateTime());
        stmt.setString(5, visit.getDoctorNotes());
        stmt.setInt(6, visit.getVisitId());
        return stmt.executeUpdate() > 0;
    }

    // Retrieve all visits
    public List<PatientVisit> getAll() throws SQLException {
        List<PatientVisit> list = new ArrayList<>();
        String sql = "SELECT * FROM patient_visit";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("visit_id");
            int patientId = rs.getInt("patient_id");
            int doctorId = rs.getInt("doctor_id");
            String reason = rs.getString("reason");
            Timestamp dateTime = rs.getTimestamp("date_time");
            String notes = rs.getString("doctor_notes");
            list.add(new PatientVisit(id, patientId, doctorId, reason, dateTime, notes));
        }
        return list;
    }

    // Retrieve visit by ID
    public PatientVisit getById(int id) throws SQLException {
        String sql = "SELECT * FROM patient_visit WHERE visit_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int patientId = rs.getInt("patient_id");
            int doctorId = rs.getInt("doctor_id");
            String reason = rs.getString("reason");
            Timestamp dateTime = rs.getTimestamp("date_time");
            String notes = rs.getString("doctor_notes");
            return new PatientVisit(id, patientId, doctorId, reason, dateTime, notes);
        }
        return null;
    }
}
