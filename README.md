# Medical Record Management System - Java MVC Architecture

This project implements a structured Java MVC architecture for managing medical data. It separates logic into clear layers and uses `ServiceResult` to handle errors more effectively throughout the system.

---

## 📁 Folder Structure

```
src/
├── model/         # All 8 data table classes (core + transactional)
├── dao/           # All SQL logic (insert, delete, update, etc.)
├── dto/           # ServiceResult class for error handling
├── controller/    # Business logic and constraints
└── view/          # GUI code (Swing)
```

---

## 🧠 Model

This is where **all 8 database tables** are represented as Java classes with attributes, constructors, getters, and setters.

### Core Record Tables:
- `Patient`
- `Doctor`
- `Disease`
- `LabProcedure`

### Transaction Record Tables:
- `PatientVisit`
- `LabResult`
- `MedicalHistory`
- `Diagnosis`

Each class simply holds the structure of the table.

**Example:**
```java
public class Patient {
    private int id;
    private String name;
    private String birthdate;

    public Patient(int id, String name, String birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
}
```

---

## 🗃️ DAO (Data Access Object)

DAO classes contain all **SQL logic** in reusable methods like:

```java
public boolean insert(Patient patient);
public boolean delete(int id);
public boolean update(Patient patient);
public List<Patient> getAll();
public Patient getById(int id);
```

All SQL statements are centralized here to keep database access clean and modular.

---

## 📦 DTO (Data Transfer Object)

The DTO package includes:

### `ServiceResult.java`

This class returns both:
- `boolean success`
- `String message`

Useful for error handling at the controller and GUI level.

```java
public class ServiceResult {
    private boolean status;
    private String message;

    public ServiceResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() { return status; }
    public String getMessage() { return message; }

    public void setStatus(boolean status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
}
```

---

## 🧩 Controller

The Controller is where **business logic and validation** is applied. It uses the DAO to interact with the database but adds constraints such as:

- Don't insert if a patient already exists
- Don't delete if the record doesn't exist

It uses `ServiceResult` for returning both the status and reason of success/failure.

**Example:**
```java
public class PatientController {
    private PatientDAO dao = new PatientDAO();

    public ServiceResult addPatient(Patient patient) {
        if (dao.exists(patient.getId())) {
            return new ServiceResult(false, "Patient already exists.");
        }

        boolean success = dao.insert(patient);
        return success 
            ? new ServiceResult(true, "Patient successfully added.")
            : new ServiceResult(false, "Error inserting patient.");
    }
}
```

---

## 🖥️ View

The View is the **GUI layer**, using Swing or JavaFX. It listens for user input, then calls the Controller methods and displays the result using `ServiceResult`.

**Example:**
```java
addButton.addActionListener(e -> {
    Patient patient = new Patient(...); // get data from fields
    ServiceResult result = controller.addPatient(patient);
    JOptionPane.showMessageDialog(null, result.getMessage());
});
```

---

## ✅ Summary

| Layer       | Responsibility                                           |
|-------------|---------------------------------------------------------|
| Model       | Represents database structure                           |
| DAO         | Contains SQL logic in reusable methods                  |
| DTO         | Carries status and message using `ServiceResult`        |
| Controller  | Applies business logic and calls DAO methods            |
| View        | GUI layer: collects input, shows output, and calls Controller |

---

## 💡 Why use ServiceResult?

Using just a `boolean` doesn’t tell us **why** something failed. With `ServiceResult`, we know both:

- `false` → failure
- `"Patient already exists"` → reason

---

## 🔧 Tech Stack
- JDBC
- MySQL
- Swing
