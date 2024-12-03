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
    TEXT
) VALUES (
    'Rome'
),
(
    'Madrid'
),
(
    'Paris'
),
(
    'Berlin'
),
(
    'Python'
),
(
    'Java'
),
(
    'German'
),
(
    'Maven'
),
(
    '1000'
);

INSERT INTO QUESTION_ANSWER VALUES (
    1,
    1,
    1,
    'F'
),
(
    2,
    2,
    1,
    'F'
),
(
    3,
    3,
    1,
    'T'
),
(
    4,
    4,
    1,
    'F'
),
(
    5,
    5,
    2,
    'T'
),
(
    6,
    6,
    2,
    'T'
),
(
    7,
    7,
    2,
    'F'
),
(
    8,
    8,
    2,
    'F'
),
(
    9,
    9,
    3,
    'T'
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