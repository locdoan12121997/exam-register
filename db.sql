DROP DATABASE IF EXISTS register_db;
CREATE DATABASE register_db;
USE register_db;


CREATE TABLE Account(
    userName       VARCHAR(255) NOT NULL,
    userPassword   VARCHAR(255) NOT NULL,
    firstName      VARCHAR(255) NOT NULL,
    lastName       VARCHAR(255) NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE Student(
    accountId		INTEGER NOT NULL,
    code            VARCHAR(255) NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    FOREIGN KEY (accountId) REFERENCES Account(id),
    UNIQUE(code)
);

CREATE TABLE Lecturer(
    accountId		INTEGER NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    FOREIGN KEY (accountId) REFERENCES Account(id)
);

CREATE TABLE Assistant(
    accountId		INTEGER NOT NULL,
    id				INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    FOREIGN KEY (accountId) REFERENCES Account(id)
);

CREATE TABLE Semester(
    fromDate		DATE    NOT NULL,
    toDate			DATE    NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE Module(
    id				INTEGER NOT NULL AUTO_INCREMENT,
    code			VARCHAR(20) UNIQUE NOT NULL,
    name			VARCHAR(100) NOT NULL,
    semesterId     INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (code, name, semesterId),
    FOREIGN KEY (semesterId) REFERENCES Semester(id)
);

CREATE TABLE ModuleSession(
    sessionDate		DATE    NOT NULL,
    fromTime		TIME    NOT NULL,
    toTime			TIME    NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    moduleId		INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (moduleId) REFERENCES Module(id)
);

CREATE TABLE Attendance(
    studentId		INTEGER NOT NULL,
    sessionId		INTEGER NOT NULL,
    PRIMARY KEY (studentId, sessionId),
    FOREIGN KEY (studentId) REFERENCES Student(id),
    FOREIGN KEY (sessionId) REFERENCES ModuleSession(id)
);
/*                     ------------------              */

CREATE TABLE Exam(
    examDate		DATE    NOT NULL,
    fromTime		TIME    NOT NULL,
    toTime			TIME    NOT NULL,
    deadline		DATE    NOT NULL,
    id              INTEGER NOT NULL AUTO_INCREMENT,
    moduleId		INTEGER NOT NULL,
    FOREIGN KEY (moduleId) REFERENCES Module(id),
    PRIMARY KEY (id)
);

CREATE TABLE ModuleList(
    studentId		INTEGER NOT NULL,
    moduleId		INTEGER NOT NULL,
    PRIMARY KEY (studentId, moduleId),
    FOREIGN KEY (studentId) REFERENCES Student(id),
    FOREIGN KEY (moduleId) REFERENCES Module(id)
);

CREATE TABLE RegisterList(
    studentId		INTEGER NOT NULL,
    examId			INTEGER NOT NULL,
    PRIMARY KEY (studentId, examId),
    FOREIGN KEY (studentId) REFERENCES Student(id),
    FOREIGN KEY (examId) REFERENCES Exam(id)
);

CREATE TABLE LecturerModule(
    lecturerId		INTEGER NOT NULL,
    moduleId		INTEGER NOT NULL,
    PRIMARY KEY (lecturerId, moduleId),
    FOREIGN KEY (lecturerId) REFERENCES Lecturer(id),
    FOREIGN KEY (moduleId) REFERENCES Module(id)
);

DELIMITER $$

-- Create semester
CREATE PROCEDURE CreateSemester(IN from_date DATE, IN to_date DATE)
BEGIN
    INSERT INTO Semester(fromDate, toDate)
    VALUE (from_date, to_date);
END$$

-- Get list of semester
CREATE PROCEDURE GetSemesters()
BEGIN
    SELECT *
    FROM Semester;
END$$

-- Get student account list                                                   OK
CREATE PROCEDURE GetSemesterById(IN semester_id INTEGER)
BEGIN
    SELECT * FROM Semester
    WHERE Semester.id = semester_id;
END$$

-- Delete semester by Id
CREATE PROCEDURE DeleteSemester(IN semester_id INTEGER)
BEGIN
    DELETE FROM Semester 
    WHERE id = semester_id;
END$$

-- Update semester by Id
CREATE PROCEDURE UpdateSemester(IN semester_id INTEGER, IN from_date DATE, IN to_date DATE)
BEGIN
	  UPDATE Semester
	  SET fromDate = from_date, toDate = to_date
	  WHERE id = semester_id;
END$$

-- -- Create an account ??
CREATE PROCEDURE CreateAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN
    INSERT INTO Account (userName, userPassword, firstName, lastName)
    VALUE (user_name, user_password, first_name, last_name);
END$$


-- Get student account list                                                   OK
CREATE PROCEDURE GetStudents()
BEGIN
    SELECT *
    FROM Student
    JOIN Account ON Student.accountId = Account.id;
END$$

-- Get student account list                                                   OK
CREATE PROCEDURE GetStudentById(IN student_id INTEGER)
BEGIN
    SELECT Student.*, Account.*
    FROM Student
    JOIN Account ON Student.accountId = Account.id
    WHERE Student.id = student_id;
END$$

-- Get module list for all students in all modules                            OK
CREATE PROCEDURE GetModulesByStudentId(IN student_id INTEGER)
BEGIN
    SELECT Module.*
    FROM ModuleList
    JOIN Student ON ModuleList.studentId = Student.id
    JOIN Module ON Module.id = ModuleList.moduleId
    WHERE Student.id = student_id;
END$$

-- Get registered exam of a student
CREATE PROCEDURE GetRegistersByStudentId(IN student_id INTEGER)
BEGIN
    SELECT *
    FROM Student
    JOIN RegisterList ON Student.id = RegisterList.studentId
    JOIN Exam ON RegisterList.examId = Exam.id
    JOIN Module ON Exam.module_id = Module.id
    WHERE Student.id = student_id;
END$$


CREATE PROCEDURE UpdateAccount(IN account_id INTEGER, IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN
	UPDATE Account
	SET userName = user_name, userPassword = user_password, firstName = first_name, lastName = last_name
	WHERE id = account_id;
END$$

-- Update student code                                                        OK
CREATE PROCEDURE UpdateStudent(IN student_id INTEGER, IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255), IN student_code VARCHAR(255))
BEGIN
    UPDATE Student
    SET code = student_code
    WHERE id = student_id;
    
    SELECT @accountId := `accountId` 
    FROM Student 
    WHERE id = student_id;
    
    CALL UpdateAccount(@accountId, user_name, user_password, first_name, last_name);
END$$

-- Delete a student from the system                                           OK
CREATE PROCEDURE DeleteStudentAccount(IN student_id INTEGER)
BEGIN
    DELETE Account, Student
    FROM Account
    INNER JOIN Student
    ON Account.id = Student.accountId
    WHERE Student.id = student_id;
END$$

-- -- Create a student account                                                          OK
CREATE PROCEDURE CreateStudentAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255), IN student_code VARCHAR(255))
BEGIN
    CALL CreateAccount(user_name, user_password, first_name, last_name);
    INSERT INTO Student (accountId, code)
    VALUE (LAST_INSERT_ID(), student_code);
END$$

--  Find student account by username and password                         OK
CREATE PROCEDURE VerifyStudentAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255))
BEGIN
    SELECT Student.*, Account.*
    FROM Student
    JOIN Account ON Student.accountId = Account.id
    WHERE Account.userName = user_name
    AND Account.userPassword = user_password;
END$$

-- View attendance list by session id as Lecturer and Assistant
CREATE PROCEDURE GetAttendanceListBySessionId(IN session_id INTEGER)
BEGIN
    SELECT Student.*, Account.*
    FROM Student
    JOIN Attendance ON Student.id = Attendance.studentId
    JOIN Account ON Student.accountId = Account.id
    WHERE Attendance.sessionId = session_id;
END$$

--  View an register list by the exam id as Lecturer and Assistant
CREATE PROCEDURE GetRegistersByExamId(IN exam_id INTEGER)
BEGIN
    SELECT Student.*, Account.*
    FROM Student
    JOIN RegisterList ON RegisterList.studentId = Student.id
    JOIN Account ON Account.id = Student.id
    WHERE RegisterList.examId = exam_id;
END$$

--  View module as Lecturer
CREATE PROCEDURE GetModulesByLecturerId(IN lecturer_id INTEGER)
BEGIN
    SELECT *
    FROM Module
    JOIN LecturerModule ON Module.id = LecturerModule.moduleId
    WHERE lecturerId = lecturer_id;
END$$

CREATE PROCEDURE GetModulesBySemesterId(IN semester_id INTEGER)
BEGIN
	SELECT *
	FROM Module
	WHERE Module.semesterId = semester_id;
END$$

-- -- Create a lecturer account ??
CREATE PROCEDURE CreateLecturerAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN
    CALL CreateAccount(user_name, user_password, first_name, last_name);
    INSERT INTO Lecturer (accountId)
    VALUE (LAST_INSERT_ID());
END$$

CREATE PROCEDURE UpdateLecturer(IN lecturer_id INTEGER, IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN    
    SELECT @accountId := `accountId` 
    FROM Lecturer 
    WHERE id = lecturer_id;
    
    CALL UpdateAccount(@accountId, user_name, user_password, first_name, last_name);
END$$

-- Delete a lecturer from the system
CREATE PROCEDURE DeleteLecturerAccount(IN lecturer_id INTEGER)
BEGIN
    DELETE Account, Lecturer
    FROM Account
    INNER JOIN Lecturer
    ON Account.id = Lecturer.accountId
    WHERE Lecturer.id = lecturer_id;
END$$

--  Lecturer login
CREATE PROCEDURE VerifyLecturerAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255))
BEGIN
    SELECT Lecturer.*, Account.*
    FROM Lecturer
    JOIN Account ON Lecturer.account_id = Account.id
    WHERE Account.userName = user_name
    AND Account.userPassword = user_password;
END$$




-- -- Create an assistant account ??
CREATE PROCEDURE CreateAssistantAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN
    INSERT INTO Account (userName, userPassword, firstName, lastName)
    VALUE (user_name, user_password, first_name, last_name);
    INSERT INTO Assistant (accountId)
    VALUE (LAST_INSERT_ID());
END$$

--  Delete assistant account
CREATE PROCEDURE DeleteAssistantAccount(IN assistant_id INTEGER)
BEGIN
    DELETE Account, Assistant
    FROM Account
    INNER JOIN Assistant
    ON Account.id = Assistant.accountId
    WHERE Assistant.id = assistant_id;
END$$

CREATE PROCEDURE UpdateAssistant(IN assistant_id INTEGER, IN user_name VARCHAR(255), IN user_password VARCHAR(255), IN first_name VARCHAR(255), IN last_name VARCHAR(255))
BEGIN    
    SELECT @accountId := `accountId` 
    FROM Assistant 
    WHERE id = assistant_id;
    
    CALL UpdateAccount(@accountId, user_name, user_password, first_name, last_name);
END$$

--  Assistant login
CREATE PROCEDURE VerifyAssistantAccount(IN user_name VARCHAR(255), IN user_password VARCHAR(255))
BEGIN
    SELECT Assistant.*, Account.*
    FROM Assistant
    JOIN Account ON Assistant.accountId = Account.id
    WHERE Account.userName = user_name
    AND Account.userPassword = user_password;
END$$

--  List all overlap module
CREATE PROCEDURE GetOverlapModules()
BEGIN
    SELECT M1.*, M2.* FROM(
        SELECT S1.moduleId AS Module1Id,
            S1.id AS Session1Id,
            S1.sessionDate AS Date1,
            S1.fromTime AS Start1,
            S1.toTime AS End1,
            S2.moduleId AS Module2Id,
            S2.id AS Session2Id,
            S2.sessionDate AS Date2,
            S2.fromTime AS Start2,
            S2.toTime AS End2
        FROM ModuleSession S1, ModuleSession S2
        WHERE S1.id < S2.id AND
            S1.sessionDate = S2.sessionDate AND ((
                S1.fromTime < S2.fromTime AND S2.fromTime < S1.toTime)
                OR (S2.fromTime < S1.fromTime AND S1.fromTime < S2.toTime)
            )
        ) as SS
JOIN Module M1 ON SS.Module1Id = M1.id
JOIN Module M2 ON SS.Module2Id = M2.id;
END$$

--  Student can view the module enrollment list
CREATE PROCEDURE GetEnrollsByModuleId(IN module_id INTEGER)
BEGIN
    SELECT Student.*, Account.*
    FROM Student
    JOIN ModuleList ON Student.id = ModuleList.studentId
    JOIN Account ON Student.accountId = Account.id
	WHERE ModuleList.moduleId = moduleId;
END$$

--  Get all modules
CREATE PROCEDURE GetAllModulesDetails()
BEGIN
    SELECT *
    FROM Module;
END$$

-- View the details of a module
CREATE PROCEDURE GetModuleById(IN module_id INTEGER)
BEGIN
    SELECT *
    FROM Module
    WHERE id = module_id;
END$$

-- Create a module
CREATE PROCEDURE CreateModule(IN module_code VARCHAR(255), IN module_name VARCHAR(255), IN semester_id INTEGER)
BEGIN
    INSERT INTO Module(code, name, semesterId)
    VALUE (module_code, module_name, semester_id);
END$$

--  Delete a module
CREATE PROCEDURE DeleteModule(IN module_id INTEGER)
BEGIN
    DELETE
    FROM Module
    WHERE id = module_id;
END$$

--  Update an module information
CREATE PROCEDURE UpdateModule(IN module_id INTEGER, IN module_code VARCHAR(20), IN module_name VARCHAR(100))
BEGIN
    UPDATE Module
    SET code = module_code, name = module_name
    WHERE id = module_id;
END$$

--  Enroll a student
CREATE PROCEDURE EnrollStudent(IN student_id INTEGER, IN module_id INTEGER)
BEGIN
    INSERT INTO ModuleList(studentId, moduleId)
    VALUE (student_id, module_id);
END$$

--  Unenrollment for a student
CREATE PROCEDURE UnenrollStudent(IN student_id INTEGER, IN module_id INTEGER)
BEGIN
    DELETE
    FROM ModuleList
    WHERE studentId = student_id AND moduleId = module_id;
END$$

-- Get module list for all students in all modules
CREATE PROCEDURE GetEnrollmentLists()
BEGIN
    SELECT Account.*, Student.*, Module.*
    FROM ModuleList
    JOIN Student ON ModuleList.studentId = Student.id
    JOIN Account ON Student.accountId = Account.id
    JOIN Module ON Module.id = ModuleList.moduleId;
END$$

-- Create a session
CREATE PROCEDURE CreateModuleSession(IN session_date DATE, IN from_time TIME, IN to_time TIME, IN module_id INTEGER)
BEGIN
    INSERT INTO ModuleSession(sessionDate, fromTime, toTime, moduleId)
    VALUE (session_date, from_time, to_time, module_id);
END$$

-- Delete a session
CREATE PROCEDURE DeleteModuleSession(IN session_id INTEGER)
BEGIN
    DELETE FROM ModuleSession
    WHERE id = session_id;
END$$

-- Update a session
CREATE PROCEDURE UpdateModuleSession(IN session_id INTEGER, IN session_date DATE, IN fromTime TIME, IN toTime TIME)
BEGIN
    UPDATE ModuleSession
    SET sessionDate = session_date, fromTime = fromTime, toTime = toTime
    WHERE id = session_id;
END$$

CREATE PROCEDURE GetModuleSessions()
BEGIN
    SELECT *
    FROM ModuleSession;
END$$

-- View the details of a module session
CREATE PROCEDURE GetModuleSessionById(IN session_id INTEGER)
BEGIN
    SELECT *
    FROM ModuleSession
    WHERE id = session_id;
END$$

-- View the details of a module session
CREATE PROCEDURE GetModuleSessionsByModuleId(IN module_id INTEGER)
BEGIN
    SELECT *
    FROM ModuleSession
    WHERE moduleId = module_id;
END$$



-- Create exam
CREATE PROCEDURE CreateExam(IN exam_date DATE, IN from_time TIME, IN to_time TIME, IN dead_line DATE, IN module_id INTEGER)
BEGIN
	INSERT INTO Exam(examDate, fromTime, toTime, deadline, moduleId)
	VALUE (exam_date, from_time, to_time, dead_line, module_id);
END$$

--  Delete an exam
CREATE PROCEDURE DeleteExam(IN exam_id INTEGER)
BEGIN
    DELETE
    FROM Exam
    WHERE id = exam_id;
END$$

--  Update exam date/time
CREATE PROCEDURE UpdateExam(IN exam_id INTEGER, IN exam_date Date, IN from_time TIME, IN to_time TIME, IN dead_line Date)
BEGIN
    UPDATE Exam
    SET examDate = exam_date, fromTime = from_time, toTime = to_time, deadline = dead_line
    WHERE id = exam_id;
END$$

--  Get all exams
CREATE PROCEDURE GetExams()
BEGIN
    SELECT *
    FROM Exam;
END$$

-- List all the exams for a given semester
CREATE PROCEDURE GetExamById(IN exam_id INTEGER)
BEGIN
    SELECT *
    FROM Exam
    WHERE id = exam_id;
END$$

CREATE PROCEDURE GetExamsByModuleId(IN module_id INTEGER)
BEGIN
	SELECT *
	FROM Exam
	WHERE moduleId = module_id;
END$$

-- List all the exams for a given semester
CREATE PROCEDURE GetExamBySemesterId(IN semester_id INTEGER)
BEGIN
    SELECT Exam.*
    FROM Exam
    JOIN Module ON Exam.moduleId = Module.id
	WHERE Module.semesterId = semester_id;
END$$

CREATE PROCEDURE GetExamByStudentIDModuleId(IN student_id INTEGER, IN module_id INTEGER)
BEGIN
	SELECT Exam.*
	FROM Exam
	JOIN Module ON Module.id = Exam.moduleId
	JOIN ModuleList ON ModuleList.moduleId = Module.id
	WHERE ModuleList.studentId = student_id AND ModuleList.moduleId = module_id;
END$$


-- Register an exam for a student
CREATE PROCEDURE CreateRegister(IN student_id INTEGER, IN exam_id INTEGER)
BEGIN
    INSERT INTO RegisterList(studentId, examId)
    VALUES (student_id, exam_id);
END$$


-- Unregister
CREATE PROCEDURE DeleteRegister(IN student_id INTEGER, IN exam_id INTEGER)
BEGIN
    DELETE
    FROM RegisterList
    WHERE studentId = student_id AND examId = exam_id;
END$$

CREATE PROCEDURE GetRegisters()
BEGIN
    SELECT Student.*
    FROM Student
    JOIN RegisterList ON Student.id = RegisterList.studentId
    JOIN Account ON Student.accountId = Account.id
    JOIN Exam ON RegisterList.examId = Exam.id
    JOIN Module ON Exam.moduleId = Module.id;
END$$

-- Sign the attendance list
CREATE PROCEDURE CreateAttendance(IN student_id INTEGER, IN session_id INTEGER)
BEGIN
    INSERT INTO Attendance(studentId, sessionId)
    VALUES (student_id, session_id);
END$$


-- Unsign
CREATE PROCEDURE DeleteAttendance(IN student_id INTEGER, IN session_id INTEGER)
BEGIN
    DELETE
    FROM Attendance
    WHERE studentId = student_id AND sessionId = session_id;
END$$

-- Get Lecturer by ID

CREATE PROCEDURE GetLecturerById(IN lecture_id INTEGER)
BEGIN
    SELECT Lecturer.*, Account.*
    FROM Lecturer
    JOIN Account ON Lecturer.accountId = Account.id
    WHERE Lecturer.id = lecture_id;
END$$


-- Get Assistant by ID
CREATE PROCEDURE GetAssistantById(IN assistant_id INTEGER)
BEGIN
    SELECT Assistant.*, Account.*
    FROM Assistant
    JOIN Account ON Assistant.accountId = Account.id
    WHERE Assistant.id = assistant_id;
END$$


-- Get assistant account list                                                   OK
CREATE PROCEDURE GetAssistants()
BEGIN
    SELECT Assistant.*, Account.*
    FROM Assistant
    JOIN Account ON Assistant.accountId = Account.id;
END$$

-- Get lecturer account list
CREATE PROCEDURE GetLecturers()
BEGIN
    SELECT Lecturer.*, Account.*
    FROM Lecturer
    JOIN Account ON Lecturer.accountId = Account.id;
END$$



-- Get student session of a module and its attendance
CREATE PROCEDURE GetModuleSessionByStudentIDModuleId(IN module_id INTEGER, IN student_id INTEGER)
BEGIN
    SELECT ModuleSession.*, IF(EXISTS(
        SELECT *
        FROM Attendance
        WHERE Attendance.studentId = student_id AND Attendance.sessionId = ModuleSession.id
    ), "True", "False") AS Attended
    FROM ModuleSession
    WHERE ModuleSession.moduleId = module_id;
END$$

CREATE PROCEDURE GetModuleSessionByLecturerIDModuleId(IN module_id INTEGER, IN lecturer_id INTEGER)
BEGIN
    SELECT ModuleSession.*
    FROM ModuleSession
    JOIN LecturerModule ON LecturerModule.moduleId = ModuleSession.moduleId
    WHERE ModuleSession.moduleId = module_id AND LecturerModule.lecturerId = lecturer_id;
END$$

DELIMITER ;