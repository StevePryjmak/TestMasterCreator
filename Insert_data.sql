INSERT INTO USERS(
    LOGIN,
    PASSWORD,
    EMAIL,
	ISACTIVE
) VALUES (
    'system',
    'projektpap2024',
    'email@pw.edu.pl',
	'T'
),
(
    'Jacek15',
    'haslo',
    'jacek@pw.edu.pl',
	'T'
),
(
    'Kropka',
    'Maven',
    'najlepszy@pw.edu.pl',
	'T'
);

INSERT INTO TESTS(
    NAME,
    USERS_USERID,
    DESCRIPTION,
    CREATIONDATE,
    FIELD
) VALUES (
    'Testowy test',
    1,
    'Opis',
    '11-Nov-1999',
    'Programowanie aplikacyjne'
);

INSERT INTO TYPES(Description) VALUES (
    'Single choice'
),
(
    'Multiple choice'
),
(
    'Open'
);

INSERT INTO HINTS(Text) VALUES (
    'Hint1'
),
(
    'Hint2'
),
(
    'Hint3'
);

INSERT INTO QUESTIONS(
	TEXT,
	TYPES_TYPEID,
	POSITION,
	TESTS_TESTID
) VALUES (
    'What is the capital city of France?',
    1,
    1,
	1
),
(
    'Which of the following are programming languages?',
    2,
    2,
	1
),
(
    'How many meters are in a kilometer?',
    3,
    3,
	1
);

INSERT INTO ANSWERS(
    TEXT,
	ISCORRECT,
	QUESTIONS_QUESTIONID
) VALUES (
    'Rome',
	'F',
	1
),
(
    'Madrid',
	'F',
	1
),
(
    'Paris',
	'T',
	1
),
(
    'Berlin',
	'F',
	1
),
(
    'Python',
	'T',
	2
),
(
    'Java',
	'T',
	2
),
(
    'Germany',
	'F',
	2
),
(
    'Maven',
	'F',
	2
),
(
    '1000',
	'T',
	3
);

COMMIT;
INSERT INTO Users(Login, Password, Email) VALUES ('ala', 'ma kota', 'xd');
COMMIT;
INSERT INTO Users(Login, Password, Email) VALUES ('newuser', 'newpass', 'newemail@example.com');
COMMIT;
INSERT INTO Users(Login, Password, Email) VALUES ('newuser', 'newpass', 'newemail@example.com');
COMMIT;
INSERT INTO Users(Login, Password, Email) VALUES ('newuser', 'newpass', 'newemail@example.com');
COMMIT;