
package model;
import java.sql.Timestamp;

public class PatientVisit {

    private int visitId = -1;
    private int patientId = -1;
    private int doctorId = -1; // Default to -1 for error checking
    private String reason;
    private Timestamp dateTime;
    private String doctorNotes; // Doctor notes are optional

    // No-arg constructor for JDBC, JavaFX, etc.
    public PatientVisit() {}

    // Full constructor (e.g. for reading from DB)
    public PatientVisit(int visitId, int patientId, int doctorId, String reason, Timestamp dateTime, String doctorNotes) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.reason = reason;
        this.dateTime = dateTime;
        this.doctorNotes = doctorNotes;
    }

    // Getters
    public int getVisitId() { return visitId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getReason() { return reason; }
    public Timestamp getDateTime() { return dateTime; }
    public String getDoctorNotes() { return doctorNotes; }

    // Setters
    public void setVisitId(int visitId) { this.visitId = visitId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setReason(String reason) { this.reason = reason; }
    public void setDateTime(Timestamp dateTime) { this.dateTime = dateTime; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }

    // Check if visit is valid/persisted
    public boolean isPersisted() {
        return visitId > 0 && patientId > 0 && doctorId > 0;
    }

    @Override
    public String toString() {
        
        return "Date Time: " + dateTime +
               " | Visit ID: " + visitId +
               " | Patient ID: " + patientId +
               " | Doctor ID: " + doctorId +
               " | Reason: " + reason +
               " | Doctor's Notes: " + doctorNotes;
    }
}
