package model;

public class LabResult {

    private int labResultId = -1;
    private int orderingPhysicianId = -1;
    private int patientId = -1;
    private int procedureId = -1;
    private String result;
    private java.sql.Date date;

    // No-arg constructor
    public LabResult() {}

    // Constructor without ID (for inserts)
    public LabResult(int orderingPhysicianId, int patientId, int procedureId, String result, java.sql.Date date) {
        this.orderingPhysicianId = orderingPhysicianId;
        this.patientId = patientId;
        this.procedureId = procedureId;
        this.result = result;
        this.date = date;
    }

    // Full constructor (for data retrieval)
    public LabResult(int labResultId, int orderingPhysicianId, int patientId, int procedureId, String result, java.sql.Date date) {
        this.labResultId = labResultId;
        this.orderingPhysicianId = orderingPhysicianId;
        this.patientId = patientId;
        this.procedureId = procedureId;
        this.result = result;
        this.date = date;
    }

    // Getters
    public int getLabResultId() { return labResultId; }
    public int getOrderingPhysicianId() { return orderingPhysicianId; }
    public int getPatientId() { return patientId; }
    public int getProcedureId() { return procedureId; }
    public String getResult() { return result; }
    public java.sql.Date getDate() { return date; }

    // Setters
    public void setLabResultId(int labResultId) { this.labResultId = labResultId; }
    public void setOrderingPhysicianId(int orderingPhysicianId) { this.orderingPhysicianId = orderingPhysicianId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setProcedureId(int procedureId) { this.procedureId = procedureId; }
    public void setResult(String result) { this.result = result; }
    public void setDate(java.sql.Date date) { this.date = date; }

    // Utility: Check if record is saved
    public boolean isPersisted() {
        return labResultId > 0;
    }

    // Display in logs or ComboBox
    @Override
    public String toString() {
        return  "LabResult ID: " + labResultId + 
                " | Patient ID: " + patientId +
                " | Procedure ID: " + procedureId + 
                " | Date: " + date +
                " | Result: " + result;
    }
}
