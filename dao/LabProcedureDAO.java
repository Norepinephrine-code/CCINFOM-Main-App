package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.LabProcedure;

public class LabProcedureDAO {
    private final Connection conn;

    public LabProcedureDAO(Connection conn) {
        this.conn = conn;
    }

    // Insert new lab procedure
    public boolean insert(LabProcedure procedure) throws SQLException {
        String sql = "INSERT INTO lab_procedure (name, category, cost) VALUES (?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, procedure.getName());
        stmt.setString(2, procedure.getCategory());
        stmt.setBigDecimal(3, procedure.getCost());

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                procedure.setProcedureId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    // Delete a procedure
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM lab_procedure WHERE procedure_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;
    }

    // Update a procedure
    public boolean update(LabProcedure procedure) throws SQLException {

        String sql = "UPDATE lab_procedure SET name = ?, category = ?, cost = ? WHERE procedure_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, procedure.getName());
        stmt.setString(2, procedure.getCategory());
        stmt.setBigDecimal(3, procedure.getCost());
        stmt.setInt(4, procedure.getProcedureId());

        return stmt.executeUpdate() > 0;
    }

    // Retrieve all procedures
    public List<LabProcedure> getAll() throws SQLException {

        String sql = "SELECT * FROM lab_procedure";
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

     List<LabProcedure> labProcedures = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("procedure_id");
            String name = rs.getString("name");
            String category = rs.getString("category");
            java.math.BigDecimal cost = rs.getBigDecimal("cost");
            list.add(new LabProcedure(id, name, category, cost));
        }
        return labProcedures;
    }

    // Retrieve procedure by ID
    public LabProcedure getById(int id) throws SQLException {

        String sql = "SELECT * FROM lab_procedure WHERE procedure_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            
            String name = rs.getString("name");
            String category = rs.getString("category");
            java.math.BigDecimal cost = rs.getBigDecimal("cost");
            return new LabProcedure(id, name, category, cost);
        }
        return null;
    }
}
