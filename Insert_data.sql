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

INSERT INTO QUESTIONS VALUES (
    1,
    'What is the capital city of France?',
    1,
    1
),
(
    2,
    'Which of the following are programming languages?',
    2,
    2
),
(
    3,
    'How many meters are in a kilometer?',
    3,
    3
);

INSERT INTO ANSWERS(
    TEXT,
	ISCORRECT,
	QUESTIONS_QUESTION
) VALUES (
    'Rome',
	FALSE,
	1
),
(
    'Madrid',
	FALSE,
	1
),
(
    'Paris',
	TRUE,
	1
),
(
    'Berlin',
	FALSE,
	1
),
(
    'Python',
	TRUE,
	2
),
(
    'Java',
	TRUE,
	2
),
(
    'Germany',
	FALSE,
	2
),
(
    'Maven',
	FALSE,
	2
),
(
    '1000',
	TRUE,
	3
);

INSERT INTO TEST_QUESTION VALUES (
    1,
    1,
    1,
    1
),
(
    2,
    2,
    2,
    1
),
(
    3,
    3,
    3,
    1
);

COMMIT;