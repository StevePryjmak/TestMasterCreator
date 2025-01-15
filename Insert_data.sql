insert into users (
   login,
   password,
   email,
   isactive
) values ( 'system',
           'projektpap2024',
           'email@pw.edu.pl',
           'T' ),( 'Jacek15',
                   'haslo',
                   'jacek@pw.edu.pl',
                   'T' ),( 'Kropka',
                           'Maven',
                           'najlepszy@pw.edu.pl',
                           'T' );

insert into tests (
   name,
   users_userid,
   description,
   creationdate,
   field
) values ( 'Testowy test',
           1,
           'Opis',
           '11-Nov-1999',
           'Programowanie aplikacyjne' );

insert into types ( description ) values ( 'Single choice' ),( 'Multiple choice' ),( 'Open' ),( 'Multiple choice with picture'
),( 'Single choice with picture' );

insert into hints ( text ) values ( 'Hint1' ),( 'Hint2' ),( 'Hint3' );

insert into questions (
   text,
   types_typeid,
   position,
   tests_testid
) values ( 'What is the capital city of France?',
           1,
           1,
           1 ),( 'Which of the following are programming languages?',
                 2,
                 2,
                 1 ),( 'How many meters are in a kilometer?',
                       3,
                       3,
                       1 );

insert into answers (
   text,
   iscorrect,
   questions_questionid
) values ( 'Rome',
           'F',
           1 ),( 'Madrid',
                 'F',
                 1 ),( 'Paris',
                       'T',
                       1 ),( 'Berlin',
                             'F',
                             1 ),( 'Python',
                                   'T',
                                   2 ),( 'Java',
                                         'T',
                                         2 ),( 'Germany',
                                               'F',
                                               2 ),( 'Maven',
                                                     'F',
                                                     2 ),( '1000',
                                                           'T',
                                                           3 );

commit;
insert into users (
   login,
   password,
   email
) values ( 'ala',
           'ma kota',
           'xd' );
commit;
insert into users (
   login,
   password,
   email
) values ( 'newuser',
           'newpass',
           'newemail@example.com' );
commit;
insert into users (
   login,
   password,
   email
) values ( 'newuser',
           'newpass',
           'newemail@example.com' );
commit;
insert into users (
   login,
   password,
   email
) values ( 'newuser',
           'newpass',
           'newemail@example.com' );
commit;