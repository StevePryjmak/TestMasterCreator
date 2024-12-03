SELECT
    LOGIN,
    T.*,
    (
        SELECT
            COUNT(*)
        FROM
            TEST_QUESTION
        WHERE
            TESTS_TESTID = T.TESTID
    ) N
FROM
    TESTS T
    JOIN USERS
    ON USERID = USERS_USERID;