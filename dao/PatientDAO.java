package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Patient;

public class PatientDAO {
    // FINAL makes the connection stable and permanent.
    private final Connection conn;          

    public PatientDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT new patient and retrieve generated ID
    public boolean insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient (first_name, last_name, gender, date_of_birth) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, patient.getFirstName());
        stmt.setString(2, patient.getLastName());
        stmt.setString(3, patient.getGender());
        stmt.setDate(4, patient.getDateOfBirth());

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                patient.setPatientId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

        // DELETE patient by ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE patient_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        return stmt.executeUpdate() > 0;
    }


    /*
     * The logic here is that you update the patient object first in the driver class
     * then pass it to PatientDAO.update so that it updates the database.
    */ 
    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patient SET first_name = ?, last_name = ?, gender = ? , date_of_birth = ? WHERE patient_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, patient.getFirstName());
        stmt.setString(2, patient.getLastName());
        stmt.setString(3, patient.getGender());
        stmt.setDate(4, patient.getDateOfBirth());
        stmt.setInt(5, patient.getPatientId());   

        return stmt.executeUpdate() > 0;
    }

    // GET all patients
    public List<Patient> getAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {     // Similar to CCPROG2, while the cursor is TRUE/EXISTS, keep extracting.

            int id = rs.getInt("patient_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String gender = rs.getString("gender");
            Date dob = rs.getDate("date_of_birth");

            patients.add(new Patient(id, firstName, lastName, gender, dob));
        }
        return patients;
    }

    // GET patient by ID
    public Patient getById(int id) throws SQLException {
        String sql = "SELECT * FROM patient WHERE patient_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String gender = rs.getString("gender");
            Date dob = rs.getDate("date_of_birth");

            return new Patient(id, firstName, lastName, gender, dob);
        }
        return null; // not found
    }

}
