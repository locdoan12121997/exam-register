USE register_db;
INSERT INTO Account (userName, userPassword, firstName, lastName)
VALUES
    ("Wallace", 6945, "Mooney", "Halee"),
    ("Darrel", 6104, "Burris", "Ferris"),
    ("Kathleen", 9660, "Mcpherson", "Maile"),
    ("Riley", 8480, "Heath", "Rhiannon"),
    ("Lars", 5689, "Bruce", "Susan"),
    ("Zeph", 7516, "Martinez", "Rose"),
    ("Price", 5747, "Vance", "Philip"),
    ("Uta", 8746, "Salazar", "Allistair"),
    ("Maryam", 6528, "Tyson", "May"),
    ("Jack", 9948, "Navarro", "Joan"),
    ("Mya", 948, "Navro", "Jon"),
    ("Mam", 9588, "Tyn", "My");

INSERT INTO Student (accountId, code)
VALUES
    (1, "fHUT4L"),
    (3, "7b2bpk"),
    (5, "krWMjW"),
    (7, "N4kwrb"),
    (9, "rQqGW6"),
    (11, "rasdf6");

INSERT INTO Lecturer(accountId)
VALUES
    (2),
    (6),
    (12);

INSERT INTO Assistant(accountId)
VALUES
    (4),
    (6);

INSERT INTO Semester(fromDate, toDate)
VALUES
    ('2012-09-01', '2013-01-31'),
    ('2013-03-01', '2013-07-31'),
    ('2013-09-01', '2014-01-31'),
    ('2014-03-01', '2014-07-31'),
    ('2014-09-01', '2015-01-31'),
    ('2015-03-01', '2015-07-31'),
    ('2015-09-01', '2016-01-31'),
    ('2016-03-01', '2016-07-31');

INSERT INTO Module(code, name, semesterId)
VALUES
    ("Pro12", "Programming", 1),
    ("Sta12", "Statistics", 2),
    ("Pro13", "Programming", 3),
    ("Sta13", "Statistics", 4),
    ("Pro14", "Programming", 5),
    ("Sta14", "Statistics", 6),
    ("Pro15", "Programming", 7),
    ("Sta15", "Statistics", 8);

INSERT INTO ModuleSession(sessionDate, fromTime, toTime, moduleId)
VALUES
    ('2012-09-01', '08:45:00', '12:00:00', 1),
    ('2012-09-01', '13:00:00', '16:15:00', 1),
    ('2012-11-01', '08:45:00', '12:00:00', 1),
    ('2012-11-01', '13:00:00', '16:15:00', 1),
    ('2013-01-01', '08:45:00', '12:00:00', 1),
    ('2013-01-01', '13:00:00', '16:15:00', 1),
    ('2015-03-01', '08:45:00', '12:00:00', 2),
    ('2015-03-01', '13:00:00', '16:15:00', 2),
    ('2015-05-01', '08:45:00', '12:00:00', 2),
    ('2015-05-01', '13:00:00', '16:15:00', 2),
    ('2015-07-01', '08:45:00', '12:00:00', 2),
    ('2015-07-01', '13:00:00', '16:15:00', 2),
    ('2012-06-01', '11:59:00', '12:01:00', 2);

INSERT INTO Attendance(studentId, sessionId)
VALUES
    (1, 1),    (1, 2),    (1, 3),    (1, 4),    (1, 7),    (1, 8),    (1, 10),    (1, 12),
    (2, 1),    (2, 3),    (2, 5),    (2, 7),    (2, 9),    (2, 11),
    (3, 2),    (3, 4),    (3, 6),    (3, 8),    (3, 10),    (3, 12),
    (4, 2),    (4, 4),    (4, 5),    (4, 8),    (4, 9),    (4, 11),
    (5, 1),    (5, 5),    (5, 6),    (5, 8),    (5, 10),    (5, 13);

INSERT INTO Exam(examDate, fromTime, toTime, deadline, moduleId)
VALUES
    ('2013-02-01', '08:45:00', '12:00:00', '2013-01-20', 1),
    ('2014-02-01', '08:45:00', '12:00:00', '2014-01-20', 2),
    ('2015-02-01', '08:45:00', '12:00:00', '2015-01-20', 3),
    ('2016-02-01', '08:45:00', '12:00:00', '2016-01-20', 4);

INSERT INTO ModuleList(studentId, moduleId)
VALUES
    (1, 1), (1, 2),
    (2, 1), (2, 2),
    (3, 1), (3, 2),
    (4, 1), (4, 2),
    (5, 1), (5, 2);

INSERT INTO RegisterList(studentId, examId)
VALUES
    (1, 1), (1, 2),
    (2, 1),
    (3, 1),
    (4, 2),
    (5, 2);

INSERT INTO LecturerModule(lecturerId, moduleId)
VALUES
    (1, 1), (1, 3), (1, 5), (1, 7),
	(2, 2), (2, 4), (2, 6), (2, 8);