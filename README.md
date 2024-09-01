# TestMasterCreator
A simple tool for creating and practicing tests. Includes a user-friendly GUI for easy test creation and practice sessions.


# My notes

I whant create project which will be have options to create test and practis 
Main problems Dont sure for now how to handle difrent types of questin i thougth about 
making base one test class and then extends form it and save like in C++ pointer on base class and use dynamik casting
but problem is i thinking aobut making gui and will extend from gui elements probably i can save like pointer on base class of gui
but not shure if it is best aprouch

Second problem i'm thinking about how to save tset i thoud about Serialize my test class and just save in bit format but i'm not sure whether 
when i chage sorse code would it be problem for my old saved test. If bit format saves only field probably not but if it works other way then i think i could be a problem with this

Also not sure how to do optimal code for hadle difren sub tests like when i have one choice question with 3 answer probably it hould looks a little bit difrent from Gui prospective... (Probably easyest way would be to just make only seted placec for answer and do not make optimal guis for difrent sub types of tests)

Clases

Test - class wich contain few field and mainly it contains vector of pointes on base questi
Question clase - difrent types of question clase i think ablout do something like base question extends Gui element like JPanel(probalbly for this projekt i should use JavaFX instead) and each question class difret type extends base class each test will be separate gui elemnt 

Profs of using javaFX:
Simple to bild difrent question with difrent batons 

Cons:
Had problem with pakajing all javaFX dependecies to jar file using gradle


And thats probably it i think in think projekt i need just configure projekt configer proper question difrent type hadler 
and solve this problem whith Serializele version controler test creator or function to abdates serialized old clase to new version (like i said if serializeble only sawing filed it could be easy solve also i can try to handle saving test with same sord of database but i think it would be harder then just save bit serisalize class) and create a lot of gui panels

## Plan

1. Configure JavaFX with gradle
2. Create base class for test
3. create contaner for test
4. Made it serializable
5. Write function for serialization
6. Create difrent types of test (One answer, multiple answers, text answer ...)
7. design Gui with scen builder



