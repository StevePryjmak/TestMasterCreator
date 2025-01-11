INSERT INTO USERS(
    LOGIN,
    PASSWORD,
    EMAIL
) VALUES (
    'system',
    'projektpap2024',
    'email@pw.edu.pl'
),
(
    'Jacek15',
    'haslo',
    'jacek@pw.edu.pl'
),
(
    'Kropka',
    'Maven',
    'najlepszy@pw.edu.pl'
);

INSERT INTO TESTS(
    TESTID,
    NAME,
    USERS_USERID,
    DESCRIPTION,
    CREATIONDATE,
    FIELD
) VALUES (
    1,
    'Testowy test',
    1,
    'Opis',
    '11-Nov-1999',
    'Programowanie aplikacyjne'
);

INSERT INTO TYPES VALUES (
    1,
    'Single choice'
),
(
    2,
    'Multiple choice'
),
(
    3,
    'Open'
);

INSERT INTO HINTS VALUES (
    1,
    'Hint1'
),
(
    2,
    'Hint2'
),
(
    3,
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