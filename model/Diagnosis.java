package model;
import java.sql.Date;

public class Diagnosis {

    private int diagnosisId = -1;
    private int patientId = -1;
    private int doctorId = -1;
    private int diseaseId = -1;
    private Date dateDiagnosis;
    private String notes; // optional

    // No-arg constructor for JDBC, JavaFX, etc.
    public Diagnosis() {}

    // Constructor without ID (for insert)
    public Diagnosis(int patientId, int doctorId, int diseaseId, Date dateDiagnosis, String notes) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diseaseId = diseaseId;
        this.dateDiagnosis = dateDiagnosis;
        this.notes = notes;
    }

    // Full constructor (for retrieval)
    public Diagnosis(int diagnosisId, int patientId, int doctorId, int diseaseId, Date dateDiagnosis, String notes) {
        this.diagnosisId = diagnosisId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diseaseId = diseaseId;
        this.dateDiagnosis = dateDiagnosis;
        this.notes = notes;
    }

    // Getters
    public int getDiagnosisId() { return diagnosisId; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public int getDiseaseId() { return diseaseId; }
    public Date getDateDiagnosis() { return dateDiagnosis; }
    public String getNotes() { return notes; }

    // Setters
    public void setDiagnosisId(int diagnosisId) { this.diagnosisId = diagnosisId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setDiseaseId(int diseaseId) { this.diseaseId = diseaseId; }
    public void setDateDiagnosis(Date dateDiagnosis) { this.dateDiagnosis = dateDiagnosis; }
    public void setNotes(String notes) { this.notes = notes; }

    // Utility
    public boolean isPersisted() {
        return diagnosisId > 0;
    }

    @Override
    public String toString() {
        return  "Diagnosis ID: " + diagnosisId + 
                " | Patient ID: " + patientId + 
                " | Disease ID: " + diseaseId + 
                " | Date: " + dateDiagnosis +
                " | Notes: " + notes;
    }
}
