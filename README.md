## Project: The Buzz

## Descrition:
- The Buzz is a social media app where users can post, delete, like, and edit messages. 

## Contributors
1. Yash Patel - yap224@lehigh.edu
2. Sean Noto - scn224@lehigh.edu
3. Tiana Aldroubi - tma224@lehigh.edu
4. Aryan Tawde - art223@lrhigh.edu
4. Wilson Zheng - wjz224@lehigh.edu

## Deployment
1. Go into the thebuzz folder and run "sh deploy.sh"
2. Go into backend folder and run "mvn package; mvn heroku:deploy"


## User Stories

U1: As an Admin I want to edit the database directly because I need to update and maintain it. (Manual test)

U2: As an Admin I want to add/drop tables for maintenance purposes. (Manual test)

U3: As an Admin I want to manage new columns because I want to maintain parts of the database data. (Manual Test)

U4: As an Admin  I want to manage new tables because I want to maintain the database as a whole. (Manual Test) 

U5: As an Authenticated User I want to like and unlike posts so I can show support for the posts I like. (Automatic test)

U6: As an Authenticated User I want to post text so I can share my ideas to others. (Automatic test)

U7: As an Authenticated user  I want to post things because I enjoy sharing ideas. (Automatic test)

U8: As an Authenticated user  I want to like posts because I want to interact with other idea creators. (Automatic test)

U9: As an Authenticated user  I want to be able to delete my own posts because I may not like my old posts. (Automatic test)

U10: As an Authenticated user  I want to edit my own posts because I may have typos on my posts. (Automatic test)

## Routes
- __Get__: returns one entity from the table post
   - \GET /messages 
   - JSON Route: 
         - "mId" = int
         - "mTitle" = String
         - "mContent" = String
         - "mLikes" = int
         - "mCreated" = timecreated
  
- __Get__: returns one entity from the table comment
    - \GET /comments
    - JSON Route:
        - "mId" = int
        - "mCommentId" = int
        - "mCommentLikes" = int
        - "mCommentDislikes" = int
        - "mComment" = String
    

- __Post__: adds a new message, title, messageId, and like counter to the current database table
    - \POST /messages
    - JSON Route: 
         - "mTitle" = String
         - "mContent" = String
  
- __Post__ : adds a new comment to the associated messageId
    - \POST /comments
    - JSON Route: 
        - "mId" = int
        - "mComment" = String
  
- __Delete__: removes an entity specified by an ID number from the table
    - \DELETE /messages/#
    - JSON Route: 
        - "mId" = int
  
- __Delete__: Removes a comment specified by the commentId
    - \DELETE /comments/#
    - JSON Route: 
         - "mCommentId" =  int
   
- __Put__: changes an existing entity in the table by specifying an ID number
    - \PUT /messages/#
    - JSON Route:
        - "mId" = int
        - "mContent" = String

- __Put__: Adds likes for message
    - \PUT /messages/#/likes
    - JSON Route:
        - "mId" = int
        -  "mLikes" = int
      
- __Put__: Adds dislikes for message
    - \PUT /messages/#/dislikes
    -  JSON Route:
        - "mId" = int
        - "mDislikes" = int
      
- __Put__: Update Comment
    - \PUT /comments/#
    - JSON Route: 
         - "mId" = int
         - "mComment" = String
      
- __Put__: Adds likes for comment
    - \PUT /comments/#/likes
    - JSON Route:
        - "mCommentId" = int
        - "mCommentLikes" = int
  
- __Put__: Adds dislikes for comment
    - \PUT /comments/#/dislikes
    - JSON Route:
         - "mCommentId" = int
         - "mCommentDislikes" = int
  
## Javadoc documentation
Read HTML file for App.java and Database.java [here](./backend\src\main\java\edu\lehigh\cse216\yap224\backend\JavadocHTMLFiles\index-all.html) 

  
## Test Description
- __Backend__: Use the AppTest.java file to create script that automatically runs the routes for get, post, put, and delete then makes sures then Make sure that posts added through the front end/mobile actually appear in database

Use Database.java to test the different methods in the Database.java class

- __Admin__: Check that tables can be edited and created/deleted

- __Web front-end__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly


- __Mobile__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly


## ERD

![ERD Diagram for backend](images/ERD Diagram.png)

## State Machine

![State Machine](images/StateMachine.png)

![State Machine](images/userStateMachine.png)

## System Diagram

![System Diagram](images/SystemDiagram.png)

## Desktop and Mobile Renders

![Desktop 1](images/desktop 1.png)

![Desktop 2](images/desktop 2.png)

![Android Interaction](images/Android Large.png)

## Invite Trello Link 
 https://trello.com/invite/b/ktZREaYO/6223f419fbe085536c4393c2cb3ff528/tutorial-board-1
 
## Link for folder with project resources
https://drive.google.com/drive/folders/1bG9Afm0etEM5-NCcldsnn2dRRtsRPe1x?usp=sharing

## Documentation for React branch:
[React Documentation](thebuzz/docs/index.html)
