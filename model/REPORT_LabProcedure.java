package model;

public class REPORT_LabProcedure {
    private int procedureId;
    private String procedureName;
    private int patientId;
    private String patientName;
    private String procedureDate;

    public REPORT_LabProcedure() {}

    public REPORT_LabProcedure(int procedureId, String procedureName, int patientId, String patientName, String procedureDate) {
        this.procedureId = procedureId;
        this.procedureName = procedureName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.procedureDate = procedureDate;
    }

    public int getProcedureId() { return procedureId; }
    public String getProcedureName() { return procedureName; }
    public int getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getProcedureDate() { return procedureDate; }

    public void setProcedureId(int procedureId) { this.procedureId = procedureId; }
    public void setProcedureName(String procedureName) { this.procedureName = procedureName; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setProcedureDate(String procedureDate) { this.procedureDate = procedureDate; }
}
