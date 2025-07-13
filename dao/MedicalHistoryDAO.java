package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MedicalHistory;

public class MedicalHistoryDAO {
    private final Connection conn;

    public MedicalHistoryDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert new medical history
    public boolean insert(MedicalHistory history) throws SQLException {
        String sql = "INSERT INTO medical_history (patient_id, doctor_id, condition_id, description, date_recorded) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, history.getPatientId());

        if (history.getDoctorId() != null) {
            stmt.setInt(2, history.getDoctorId());
        } else {
            stmt.setNull(2, Types.INTEGER);
        }
        if (history.getConditionId() != null) {
            stmt.setInt(3, history.getConditionId());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }
        stmt.setString(4, history.getDescription());
        if (history.getDateRecorded() != null) {
            stmt.setDate(5, history.getDateRecorded());
        } else {
            stmt.setNull(5, Types.DATE);
        }

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                history.setHistoryId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete record
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM medical_history WHERE history_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;
    }

    // Update record
    public boolean update(MedicalHistory history) throws SQLException {
        String sql = "UPDATE medical_history SET patient_id = ?, doctor_id = ?, condition_id = ?, description = ?, date_recorded = ? WHERE history_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, history.getPatientId());
        if (history.getDoctorId() != null) {
            stmt.setInt(2, history.getDoctorId());
        } else {
            stmt.setNull(2, Types.INTEGER);
        }
        if (history.getConditionId() != null) {
            stmt.setInt(3, history.getConditionId());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }
        stmt.setString(4, history.getDescription());
        if (history.getDateRecorded() != null) {
            stmt.setDate(5, history.getDateRecorded());
        } else {
            stmt.setNull(5, Types.DATE);
        }
        stmt.setInt(6, history.getHistoryId());
        return stmt.executeUpdate() > 0;
    }

    // Retrieve all histories
    public List<MedicalHistory> getAll() throws SQLException {
        List<MedicalHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM medical_history";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("history_id");
            int patientId = rs.getInt("patient_id");
            Integer doctorId = rs.getObject("doctor_id") != null ? rs.getInt("doctor_id") : null;
            Integer conditionId = rs.getObject("condition_id") != null ? rs.getInt("condition_id") : null;
            String description = rs.getString("description");
            Date dateRecorded = rs.getDate("date_recorded");
            list.add(new MedicalHistory(id, patientId, doctorId, conditionId, description, dateRecorded));
        }
        return list;
    }

    // Retrieve by ID
    public MedicalHistory getById(int id) throws SQLException {
        String sql = "SELECT * FROM medical_history WHERE history_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int patientId = rs.getInt("patient_id");
            Integer doctorId = rs.getObject("doctor_id") != null ? rs.getInt("doctor_id") : null;
            Integer conditionId = rs.getObject("condition_id") != null ? rs.getInt("condition_id") : null;
            String description = rs.getString("description");
            Date dateRecorded = rs.getDate("date_recorded");
            return new MedicalHistory(id, patientId, doctorId, conditionId, description, dateRecorded);
        }
        return null;
    }
}
