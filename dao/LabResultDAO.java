package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.LabResult;

public class LabResultDAO {
    private final Connection conn;

    public LabResultDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert a new lab result
    public boolean insert(LabResult result) throws SQLException {
        
        String sql = "INSERT INTO lab_result (ordering_physician_id, patient_id, procedure_id, result, date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setInt(1, result.getOrderingPhysicianId());
        stmt.setInt(2, result.getPatientId());
        stmt.setInt(3, result.getProcedureId());
        stmt.setString(4, result.getResult());
        stmt.setDate(5, result.getDate());

        int rows = stmt.executeUpdate();
      
        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                result.setLabResultId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete lab result by ID
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM lab_result WHERE lab_result_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
      
        stmt.setInt(1, id);
        
        return stmt.executeUpdate() > 0;
    }

    // Update an existing lab result
    public boolean update(LabResult result) throws SQLException {
        String sql = "UPDATE lab_result SET ordering_physician_id = ?, patient_id = ?, procedure_id = ?, result = ?, date = ? WHERE lab_result_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
       
        stmt.setInt(1, result.getOrderingPhysicianId());
        stmt.setInt(2, result.getPatientId());
        stmt.setInt(3, result.getProcedureId());
        stmt.setString(4, result.getResult());
        stmt.setDate(5, result.getDate());
        stmt.setInt(6, result.getLabResultId());

        return stmt.executeUpdate() > 0;
    }

    // Retrieve all lab results
    public List<LabResult> getAll() throws SQLException {

        String sql = "SELECT * FROM lab_result";
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<LabResult> labResults = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("lab_result_id");
            int orderingPhysicianId = rs.getInt("ordering_physician_id");
            int patientId = rs.getInt("patient_id");
            int procedureId = rs.getInt("procedure_id");
            String resultText = rs.getString("result");
            java.sql.Date date = rs.getDate("date");
            labResults.add(new LabResult(id, orderingPhysicianId, patientId, procedureId, resultText, date));
        }
        return labResults;
    }

    // Retrieve lab result by ID
    public LabResult getById(int id) throws SQLException {

        String sql = "SELECT * FROM lab_result WHERE lab_result_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int orderingPhysicianId = rs.getInt("ordering_physician_id");
            int patientId = rs.getInt("patient_id");
            int procedureId = rs.getInt("procedure_id");
            String resultText = rs.getString("result");
            java.sql.Date date = rs.getDate("date");
            return new LabResult(id, orderingPhysicianId, patientId, procedureId, resultText, date);
        }
        return null;
    }
}
