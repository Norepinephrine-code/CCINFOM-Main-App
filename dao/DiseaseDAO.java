package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Disease;

public class DiseaseDAO {
    private final Connection conn;

    public DiseaseDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert new disease and set generated ID
    public boolean insert(Disease disease) throws SQLException {

        String sql = "INSERT INTO disease (disease_name, description, classification, icd_code) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, disease.getDiseaseName());
        stmt.setString(2, disease.getDescription());
        stmt.setString(3, disease.getClassification());
        stmt.setString(4, disease.getIcdCode());

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                disease.setDiseaseId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete disease by ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM disease WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;
    }

    // Update an existing disease
    public boolean update(Disease disease) throws SQLException {
        String sql = "UPDATE disease SET disease_name = ?, description = ?, classification = ?, icd_code = ? WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, disease.getDiseaseName());
        stmt.setString(2, disease.getDescription());
        stmt.setString(3, disease.getClassification());
        stmt.setString(4, disease.getIcdCode());
        stmt.setInt(5, disease.getDiseaseId());

        return stmt.executeUpdate() > 0;
    }

    // Retrieve all diseases
    public List<Disease> getAll() throws SQLException {
        List<Disease> diseases = new ArrayList<>();
        String sql = "SELECT * FROM disease";
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("disease_id");
            String name = rs.getString("disease_name");
            String description = rs.getString("description");
            String classification = rs.getString("classification");
            String icd_code = rs.getString("icd_code");
            diseases.add(new Disease(id, name, description, classification, icd_code));
        }
        return diseases;
    }

    // Retrieve disease by ID
    public Disease getById(int id) throws SQLException {

        String sql = "SELECT * FROM disease WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String name = rs.getString("disease_name");
            String description = rs.getString("description");
            String classification = rs.getString("classification");
            String icd_code = rs.getString("icd_code");

            return new Disease(id, name, description, classification, icd_code);
        }

        return null;
    }
}
