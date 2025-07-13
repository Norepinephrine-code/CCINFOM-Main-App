package dao;

import java.sql.*;
import java.util.*;
import model.Doctor;

public class DoctorDAO {
    private final Connection conn;

    public DoctorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctor (doctor_id, full_name, specialization) VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
        stmt.setInt(1, doctor.getDoctorId());
        stmt.setString(2, doctor.getFullName());
        stmt.setString(3, doctor.getSpecialization());

        int row = stmt.executeUpdate();

        if (row>0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                doctor.setDoctorId(rs.getInt(1));
            }
            return true;
        }
        return false;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM doctor WHERE doctor_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id);

        int row = stmt.executeUpdate();
        return row > 0;
    }

    public boolean update(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctor SET full_name = ?, specialization = ? WHERE doctor_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1,doctor.getFullName());
        stmt.setString(2, doctor.getSpecialization());
        stmt.setInt(3, doctor.getDoctorId());

        int row = stmt.executeUpdate();
        
        return row > 0;
    }

    public List<Doctor> getAll() throws SQLException {
        
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("doctor_id");
            String full_name = rs.getString("full_name");
            String specialization = rs.getString("specialization");

            doctors.add(new Doctor(id,full_name,specialization));
        }
        
        return doctors;
    }

    public Doctor getById (int id) throws SQLException {
        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {            // If a row exists execute this.

            int doctor_id = rs.getInt("doctor_id");
            String full_name = rs.getString("full_name");
            String specialization = rs.getString("specialization");

            return new Doctor(doctor_id, full_name, specialization);
        }

        return null;



    }



    // insert
    // delete
    // update
    // getAll
    // getById
}