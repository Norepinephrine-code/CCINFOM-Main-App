package model;

public class REPORT_DiseaseTrend {
    private String diseaseName;
    private int frequency;
    private String period;

    public REPORT_DiseaseTrend() {}

    public REPORT_DiseaseTrend(String diseaseName, int frequency, String period) {
        this.diseaseName = diseaseName;
        this.frequency = frequency;
        this.period = period;
    }
    public String getDiseaseName() { return diseaseName;}
    public int getFrequency() { return frequency;}
    public String getPeriod() {return period;}

    public void setFrequency(int frequency) {this.frequency = frequency;}
    public void setDiseaseName(String diseaseName) {this.diseaseName = diseaseName;}
    public void setPeriod(String period) {this.period = period;}
}
