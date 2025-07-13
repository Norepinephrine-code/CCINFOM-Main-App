package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Diagnosis;

public class DiagnosisDAO {
    private final Connection conn;

    public DiagnosisDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert new diagnosis and set generated ID
    public boolean insert(Diagnosis diagnosis) throws SQLException {
        String sql = "INSERT INTO diagnosis (patient_id, doctor_id, disease_id, date_diagnosis, notes) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, diagnosis.getPatientId());
        stmt.setInt(2, diagnosis.getDoctorId());
        stmt.setInt(3, diagnosis.getDiseaseId());
        stmt.setDate(4, diagnosis.getDateDiagnosis());
        stmt.setString(5, diagnosis.getNotes());

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                diagnosis.setDiagnosisId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete diagnosis by ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM diagnosis WHERE diagnosis_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;
    }

    // Update existing diagnosis
    public boolean update(Diagnosis diagnosis) throws SQLException {
        String sql = "UPDATE diagnosis SET patient_id = ?, doctor_id = ?, disease_id = ?, date_diagnosis = ?, notes = ? WHERE diagnosis_id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, diagnosis.getPatientId());
        stmt.setInt(2, diagnosis.getDoctorId());
        stmt.setInt(3, diagnosis.getDiseaseId());
        stmt.setDate(4, diagnosis.getDateDiagnosis());
        stmt.setString(5, diagnosis.getNotes());
        stmt.setInt(6, diagnosis.getDiagnosisId());

        return stmt.executeUpdate() > 0;
    }

    // Retrieve all diagnoses
    public List<Diagnosis> getAll() throws SQLException {

        String sql = "SELECT * FROM diagnosis";
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Diagnosis> diagnosises = new ArrayList<>();
        while (rs.next()) {

            int id = rs.getInt("diagnosis_id");
            int patientId = rs.getInt("patient_id");
            int doctorId = rs.getInt("doctor_id");
            int diseaseId = rs.getInt("disease_id");
            Date dateDiagnosis = rs.getDate("date_diagnosis");
            String notes = rs.getString("notes");
            diagnosises.add(new Diagnosis(id, patientId, doctorId, diseaseId, dateDiagnosis, notes));
        }

        return diagnosises;
    }

    // Retrieve diagnosis by ID
    public Diagnosis getById(int id) throws SQLException {
        String sql = "SELECT * FROM diagnosis WHERE diagnosis_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int patientId = rs.getInt("patient_id");
            int doctorId = rs.getInt("doctor_id");
            int diseaseId = rs.getInt("disease_id");
            Date dateDiagnosis = rs.getDate("date_diagnosis");
            String notes = rs.getString("notes");
            return new Diagnosis(id, patientId, doctorId, diseaseId, dateDiagnosis, notes);
        }
        return null;
    }
}
