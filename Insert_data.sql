INSERT INTO Users(Login, Password, Email) VALUES
    ('system', 'projektpap2024', 'email@pw.edu.pl'),
    ('Jacek15', 'haslo', 'jacek@pw.edu.pl'),
    ('Kropka', 'Maven', 'najlepszy@pw.edu.pl');
    
INSERT INTO Tests(Name, Users_UserId, Description, CreationDate, field) VALUES
    ('Testowy test', 1, 'Opis', '11-Nov-1999', 'Programowanie aplikacyjne');

INSERT INTO Types VALUES
    (1, 'Single choice'),
    (2, 'Multiple choice');

INSERT INTO Hints VALUES
    (1, 'Hint1'),
    (2, 'Hint2');

INSERT INTO Questions VALUES
    (1, 'What is the capital city of France?', 1, 1),
    (2, 'Which of the following are programming languages?', 2, 2);
    
INSERT INTO Answers(Text) VALUES
    ('Rome'),
    ('Madrid'),
    ('Paris'),
    ('Berlin'),
    ('Python'),
    ('Java'),
    ('German'),
    ('Maven');

INSERT INTO Question_answer VALUES
    (1, 1, 1, 'F'),
    (2, 2, 1, 'F'),
    (3, 3, 1, 'T'),
    (4, 4, 1, 'F'),
    (5, 5, 2, 'T'),
    (6, 6, 2, 'T'),
    (7, 7, 2, 'F'),
    (8, 8, 2, 'F');

INSERT INTO Test_question VALUES
    (1, 1, 1, 1),
    (2, 2, 2, 1);
    
    
COMMIT;