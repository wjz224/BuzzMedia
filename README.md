Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).
## User Stories

Admin
- As an Admin I want to edit the database directly because I need to update and maintain it. (Manual test)

- As a User I want to like and unlike posts so I can show support for the posts I like. (Automatic test)

- As a User I want to post text so I can share my ideas to others. (Automatic test)

## Routes / Purposes
- __Get__: returns one entity from the table
  GET /messages 

- __Post__: adds a new message, title, and like counter to the current database table
  POST /messages

- __Delete__: removes an entity specified by an ID number from the table
  DELETE /messages/#

- __Put__: changes an existing entity in the table by specifying an ID number
  PUT /messages/#

- __Put__: Adds like or dislikes if already liked
  PUT /messages/#/3
  
## Test Description
- __Backend__: Use the AppTest.java file to create script that automatically runs the routes for get, post, put, and delete then makes sures then Make sure that posts added through the front end/mobile actually appear in database

Use Database.java to test the different methods in the Database.java class

- __Admin__: Check that tables can be edited and created/deleted

- __Web front-end__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly


- __Mobile__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly


## ERD

![ERD Diagram for backend](images/ERD Diagram.png)

## State Machine

![State Machine](images/State Machine.png)

## System Diagram

![System Diagram](images/System Diagram.png)

## Desktop and Mobile Renders

![Desktop 1](images/desktop 1.png)

![Desktop 2](images/desktop 2.png)

![Android Interaction](images/Android Large.png)

## Contributors
1. Yash Patel
2. Sean Noto
3. Tiana Aldroubi
4. Aryan Tawde
4. Wilson Zheng

## Invite Trello Link 
 https://trello.com/invite/b/ktZREaYO/6223f419fbe085536c4393c2cb3ff528/tutorial-board-1
 
 ## Link for folder with project resources
 https://drive.google.com/drive/folders/1bG9Afm0etEM5-NCcldsnn2dRRtsRPe1x?usp=sharing