package model;

public class REPORT_PatientVisit {
    private int visitId;
    private int patientId;
    private String patientName;
    private String visitDate;

    public REPORT_PatientVisit() {}

    public REPORT_PatientVisit(int visitId, int patientId, String patientName, String visitDate) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.visitDate = visitDate;
    }

    public int getVisitId() { return visitId;}
    public int getPatientId() {return patientId;}
    public String getPatientName() {return patientName;}
    public String getVisitDate() {return visitDate;}

    public void setVisitDate(String visitDate) {this.visitDate = visitDate;}
    public void setVisitId(int visitId) {this.visitId = visitId;}
    public void setPatientId(int patientId) {this.patientId = patientId;}
    public void setPatientName(String patientName) {this.patientName = patientName;}
}
