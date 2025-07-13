package model;

public class Patient {

    /*
     * MySQL will generate and override the existing patient ID since the SQL schema
     * uses AUTO_INCREMENT. However, we default to -1 in Java for error tracing and to
     * represent an unsaved or uninitialized object (e.g., patient.isPersisted()).
     */
    private int patientId = -1;               
    private String firstName;
    private String lastName;
    private String gender;
    private java.sql.Date dateOfBirth;

    // Constructors
    public Patient() {}

    public Patient(String firstName, String lastName, String gender, java.sql.Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(int patientId, String firstName, String lastName, String gender, java.sql.Date dateOfBirth) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters
    public int getPatientId() { return patientId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public java.sql.Date getDateOfBirth() { return dateOfBirth; }

    // Setters
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDateOfBirth(java.sql.Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    /* 
     * Helper method to check if this patient has been saved to the database successfully
     */
    public boolean isPersisted() { return patientId > 0; }

    /* 
     * This can be used to display in a ComboBox GUI or in the console to debug
     */
    @Override
    public String toString() {
        return patientId + " | " +
               firstName + " " + lastName + " | " +
               "Gender: " + gender + " | " +
               "DOB: " + dateOfBirth;
    }
}
