# Medical Record Management System - Java MVC Architecture

This repository showcases a compact sample of the Model–View–Controller pattern implemented with plain JDBC and Swing.  Each package in the source tree maps to a layer of the MVC architecture and the classes are small enough to easily trace how data flows from the GUI down to SQL statements.

---

## 📁 Folder Structure

```
├── model/       # Java classes representing the database tables
├── dao/         # JDBC CRUD and report queries
├── controller/  # Validation and business rules
├── dto/         # Utility objects like ServiceResult
├── view/        # Basic Swing windows
├── AppDriver.java
```

---

## 🧠 Model

The **model** package mirrors the database schema.  Every table has a matching Java class with fields, constructors and convenience helpers.  For example, `Patient` keeps the generated ID in memory and exposes `isPersisted()` and `toString()` for debugging:

```java
public class Patient {
    // ... fields omitted for brevity
    private int patientId = -1;
    // ... getters/setters
    public boolean isPersisted() { return patientId > 0; }
    @Override
    public String toString() {
        return patientId + " | " + firstName + " " + lastName +
               " | Gender: " + gender + " | DOB: " + dateOfBirth;
    }
}
```

The package contains core records (`Patient`, `Doctor`, `Disease`, `LabProcedure`), transactional records (`PatientVisit`, `Diagnosis`, `LabResult`, `MedicalHistory`), and a few simple classes for report rows such as `REPORT_DiseaseTrend`.

---

## 🗃️ DAO (Data Access Object)

Each model has a DAO dedicated to SQL operations.  These classes accept a `java.sql.Connection` and use prepared statements.  Below is the insertion logic from `PatientDAO`:

```java
public boolean insert(Patient patient) throws SQLException {
    String sql = "INSERT INTO patient (first_name, last_name, gender, date_of_birth) VALUES (?, ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    stmt.setString(1, patient.getFirstName());
    stmt.setString(2, patient.getLastName());
    stmt.setString(3, patient.getGender());
    stmt.setDate(4, patient.getDateOfBirth());
    int rows = stmt.executeUpdate();
    if (rows > 0) {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) { patient.setPatientId(rs.getInt(1)); }
        return true;
    }
    return false;
}
```

Report DAOs follow the same style.  For instance `REPORT_DiseaseTrendDAO` performs an aggregate query and maps the result set into a list of `REPORT_DiseaseTrend` objects.

---

## 📦 DTO (Data Transfer Object)

The **dto** package currently holds a single helper used across the project:

```java
public class ServiceResult {
    private final boolean status;
    private final String message;
    public ServiceResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    public boolean getStatus() { return status; }
    public String getMessage() { return message; }
}
```

A controller returns a `ServiceResult` so the GUI always receives both a boolean success flag and a readable explanation.

---

## 🧩 Controller

Service classes apply validation before calling the DAOs.  `PatientService.addPatient()` demonstrates the pattern:

```java
public ServiceResult addPatient(Patient patient) {
    if (!isValidFields(patient)) {
        String msg = "Validation failed: Missing required patient fields.";
        logger.warning(msg);
        return new ServiceResult(false, msg);
    }
    try {
        boolean success = patientDAO.insert(patient);
        if (success) {
            String msg = "Patient added successfully: " + patient.getFirstName() + " " + patient.getLastName();
            logger.info(msg);
            return new ServiceResult(true, msg);
        } else {
            String msg = "Failed to add patient.";
            logger.info(msg);
            return new ServiceResult(false, msg);
        }
    } catch (SQLException e) {
        String msg = "Failed to insert patient: " + e.getMessage();
        logger.severe(msg);
        return new ServiceResult(false, msg);
    }
}
```

Other services follow the same template and also check that referenced records exist (e.g. `PatientVisitService` ensures valid patient and doctor IDs).

---

## 🖥️ In FREAKING PROGRESS +IGNORE+ View

The GUI layer is intentionally minimal.  `MainMenuView` wires up services and displays a few buttons:

```java
public MainMenuView(Connection conn) {
    this.patientService = new PatientService(conn);
    this.doctorService  = new DoctorService(conn);
    this.diseaseService = new DiseaseService(conn);
    this.procedureService = new ProcedureService(conn);
    setTitle("Main Menu");
    setSize(400, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(4, 1, 10, 10));
    JButton patientBtn   = new JButton("Patients");
    JButton doctorBtn    = new JButton("Doctors");
    JButton diseaseBtn   = new JButton("Diseases");
    JButton procedureBtn = new JButton("Procedures");
    add(patientBtn); add(doctorBtn); add(diseaseBtn); add(procedureBtn);
    patientBtn.addActionListener(e -> new PatientView(patientService).setVisible(true));
    doctorBtn.addActionListener(e -> new DoctorView(doctorService).setVisible(true));
    diseaseBtn.addActionListener(e -> new DiseaseView(diseaseService).setVisible(true));
    procedureBtn.addActionListener(e -> new ProcedureView(procedureService).setVisible(true));
}
```

Only `PatientView` exists and is just a stub; the other referenced screens have yet to be implemented.

The application starts in `AppDriver` which opens the main menu once a connection is provided:

```java
SwingUtilities.invokeLater(() -> {
    Connection conn = DBConnection.getConnection();
    if (conn != null) {
        new MainMenuView(conn).setVisible(true);
    } else {
        System.out.println("ERROR: Failed to connect to database.");
    }
});
```

---

## ✅ Summary

| Layer       | Responsibility                                   |
|-------------|---------------------------------------------------|
| Model       | Plain Java objects mirroring database tables      |
| DAO         | Low-level JDBC operations and report queries      |
| DTO         | Small helper objects (`ServiceResult`)            |
| Controller  | Validation, logging and delegation to DAOs        |
| View        | Swing windows calling controller methods          |

---

## 💡 Why use ServiceResult?

Returning only a boolean would drop the reason for failure.  `ServiceResult` guarantees that every service call explains what happened, making it easier to surface messages in the GUI.

---

## 🔧 Tech Stack

* JDBC with MySQL
* Java Swing
