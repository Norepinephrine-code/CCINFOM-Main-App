package dao;

import java.sql.*;
import java.util.*;
import model.YourEntity;

public class DiseaseDAO {
    private final Connection conn;

    public DiseaseDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert (Disease disease) throws SQLException {
        String sql = "INSERT INTO disease (disease_id, disease_name, description, classification, icd-code) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, disease.getDiseaseId());
        stmt.setString(2, disease.getDiseaseName());
        stmt.setString(3, disease.getDescription());
        stmt.setString(4, disease.getClassification());
        stmt.setString(5, disease.getIcdCode());

        int row = stmt.executeUpdate();

        if (row>0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                disease.setDiseaseId(rs.getInt(1));
            }

            return true;
        }
        return false;
    }

    public boolean delete (int id) throws SQLException { 
        
        String sql = "DELETE FROM disease WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        int row = stmt.executeUpdate();
        
        return row > 0;
    }

    public boolean update (Disease disease) throws SQLException {
        String sql = "UPDATE disease SET disease_name = ?, description = ?, classification = ?, `icd-code` = ? WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, disease.getDiseaseName());
        stmt.setString(2, disease.getDescription());
        stmt.setString(3, disease.getClassification());
        stmt.setString(4, disease.getIcdCode());
        stmt.setInt(5, disease.getDiseaseId());

        return stmt.executeUpdate() > 0;

    }

    public List<Disease> getAll() throws SQLException {

        List<Disease> diseases = new ArrayList<>();

        String sql = "SELECT * FROM disease";       
        Statement stmt = conn.createStatement();    
        ResultSet rs = stmt.executeQuery(sql);    
        
        while (rs.next()) {         // <----- MOVE THE CURSOR FROM BEGINNING UNTIL FUCKING END
            int disease_id = rs.getInt("disease_id");
            String disease_name = rs.getString("disease_name");
            String description = rs.getString("description");
            String classification = rs.getString("classficiation");
            String icd-code = rs.getString("icd-code");
        
            diseases.add(new Disease(disease_id, disease_name, description, classification, icd));
        }
        return diseases;

    }

    public Disease getById (int id) throws SQLException { 

        String sql = "SELECT * FROM disease WHERE disease_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) { // <------- MOVES THE CURSOR ONCE
            int disease_id = rs.getInt("disease_id");
            String disease_name = rs.getString("disease_name");
            String description = rs.getString("description");
            String classification = rs.getString("classficiation");
            String icdCode = rs.getString("icd-code");

            return new Disease(disease_id, disease_name, description, classification, icdCode);
        }

        return null;

    }

    // insert
    // delete
    // update
    // getAll
    // getById
    
}