package model;

public class Doctor {

    /*
     * First and last names are not split for this design — we store the full name as one string.
     * This simplifies the model since patient name detail is more important than doctor name granularity.
     */

    private int doctorId = -1;
    private String fullName;
    private String specialization;

    // No-arg constructor (required for JDBC / JavaFX)
    public Doctor() {}

    // Constructor for insert
    public Doctor(String fullName, String specialization) {
        this.fullName = fullName;
        this.specialization = specialization;
    }

    // Full constructor for data retrieval
    public Doctor(int doctorId, String fullName, String specialization) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    // Getters
    public int getDoctorId() { return doctorId; }
    public String getFullName() { return fullName; }
    public String getSpecialization() { return specialization; }

    // Setters
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    // Utility method
    public boolean isPersisted() {
        return doctorId > 0;
    }

    @Override
    public String toString() {
        return doctorId + " | " + fullName + " | " + specialization;
    }
}
