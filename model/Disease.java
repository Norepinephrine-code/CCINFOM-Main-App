package model;

public class Disease {

    private int diseaseId = -1;
    private String diseaseName;
    private String description;
    private String classification;
    private String icdCode;

    // No-arg constructor (required for JDBC/JavaFX)
    public Disease() {}

    // Constructor for inserting new record
    public Disease(String diseaseName, String description, String classification, String icdCode) {
        this.diseaseName = diseaseName;
        this.description = description;
        this.classification = classification;
        this.icdCode = icdCode;
    }

    // Full constructor (for reading from DB)
    public Disease(int diseaseId, String diseaseName, String description, String classification, String icdCode) {
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
        this.description = description;
        this.classification = classification;
        this.icdCode = icdCode;
    }

    // Getters
    public int getDiseaseId() { return diseaseId; }
    public String getDiseaseName() { return diseaseName; }
    public String getDescription() { return description; }
    public String getClassification() { return classification; }
    public String getIcdCode() { return icdCode; }

    // Setters
    public void setDiseaseId(int diseaseId) { this.diseaseId = diseaseId; }
    public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }
    public void setDescription(String description) { this.description = description; }
    public void setClassification(String classification) { this.classification = classification; }
    public void setIcdCode(String icdCode) { this.icdCode = icdCode; }

    // Check if saved
    public boolean isPersisted() {
        return diseaseId > 0;
    }

    // toString override for ComboBox or logs
    @Override
    public String toString() {
        return diseaseId + " | " + diseaseName + " | ICD: " + icdCode;
    }
}
