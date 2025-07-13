package model;

public class REPORT_DoctorActivity {
    private int doctorId;
    private String doctorName;
    private int totalVisits;
    private int totalDiagnoses;
    private int totalLabOrders;

    public REPORT_DoctorActivity() {}

    public REPORT_DoctorActivity(int doctorId, String doctorName, int totalVisits, int totalDiagnoses, int totalLabOrders) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.totalVisits = totalVisits;
        this.totalDiagnoses = totalDiagnoses;
        this.totalLabOrders = totalLabOrders;
    }

    public int getDoctorId() { return doctorId; }
    public String getDoctorName() { return doctorName; }
    public int getTotalVisits() { return totalVisits; }
    public int getTotalDiagnoses() { return totalDiagnoses; }
    public int getTotalLabOrders() { return totalLabOrders; }

    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setTotalVisits(int totalVisits) { this.totalVisits = totalVisits; }
    public void setTotalDiagnoses(int totalDiagnoses) { this.totalDiagnoses = totalDiagnoses; }
    public void setTotalLabOrders(int totalLabOrders) { this.totalLabOrders = totalLabOrders; }
}
