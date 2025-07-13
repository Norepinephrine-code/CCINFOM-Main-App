package model;
import java.sql.Date;

public class MedicalHistory {

    private int historyId = -1;
    private int patientId = -1;
    private Integer doctorId;       // Nullable
    private Integer conditionId;    // Nullable (refers to Disease)
    private String description;     // Optional
    private Date dateRecorded;      // Optional

    // No-arg constructor (required for JDBC and JavaFX)
    public MedicalHistory() {}

    // Constructor without ID (for inserting)
    public MedicalHistory(int patientId, Integer doctorId, Integer conditionId, String description, Date dateRecorded) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.conditionId = conditionId;
        this.description = description;
        this.dateRecorded = dateRecorded;
    }

    // Full constructor (for retrieving from DB)
    public MedicalHistory(int historyId, int patientId, Integer doctorId, Integer conditionId, String description, Date dateRecorded) {
        this.historyId = historyId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.conditionId = conditionId;
        this.description = description;
        this.dateRecorded = dateRecorded;
    }

    // Getters
    public int getHistoryId() { return historyId; }
    public int getPatientId() { return patientId; }
    public Integer getDoctorId() { return doctorId; }
    public Integer getConditionId() { return conditionId; }
    public String getDescription() { return description; }
    public Date getDateRecorded() { return dateRecorded; }

    // Setters
    public void setHistoryId(int historyId) { this.historyId = historyId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public void setConditionId(Integer conditionId) { this.conditionId = conditionId; }
    public void setDescription(String description) { this.description = description; }
    public void setDateRecorded(Date dateRecorded) { this.dateRecorded = dateRecorded; }

    // Check if the record is persisted
    public boolean isPersisted() {
        return historyId > 0;
    }

    @Override
    public String toString() {

    /* 
     * Since the condition could be entried even before hospital visit (e.g, childhood condition),
     * doctor ID is nullable, and condition might not be yet recorded in the condition table, so that too is nullable.
     * same applies for date.
     */

        return "History ID: " + historyId +
               " | Patient ID: " + patientId +
               " | Doctor ID (if any): " + (doctorId != null ? doctorId : "N/A") + 
               " | Condition ID: " + (conditionId != null ? conditionId : "None") +
               " | Date Recorded: " + (dateRecorded != null ? dateRecorded : "N/A") +
               " | Description: " + (description != null? description: "N/A");
    }
}
