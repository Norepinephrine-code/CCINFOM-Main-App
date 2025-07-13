Model - This is where ALL of the tables classes all. All 4 core record tables (patient, doctor, disease, labprocedures) are here, and the 4 transaction record tables (patient_visit, lab_result, medical_history, and diagnosis).
all of these eigth tables in total will have a model class. The model class simply has the attributes, setters, and getters.

Data Access Object (DAO) - This is where all of the SQL Statements are fixed into methods. For example, insert(), delete(), update(), getAll(), getById().

Data Transfer Object (DTO) - This contains a class ServiceResult that contains both a boolean and string for error handling and checking at the Controller and View Level.

Controller - This is the same as DAO but we combine it with business logic constraints. For example, we cannot insert a patient that already exists! We cannot delete a patient that does
not exist. So we put constraints of logic and checking before calling DAO. This is where all of the business logic will apply. All of the checking and error handling happens in this level. 
We use ServiceResult and not booleans, because we want to store both the Error Message as String, and Status as a boolean. If only used booleans as error checking, we would know it failed,
but we would not know where! But since ServiceResult stores both boolean and String, we can store the error message at the String of the ServiceResult.

View - This is the GUI. This is where the program listens for the input of the user, and then triggers the controller.
