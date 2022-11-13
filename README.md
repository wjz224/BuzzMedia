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

## Current Backlog
Web: Cannot log out

Admin: None

Backend: Improve unit tests

Mobile: Adding unit tests, fixing verify route

## Unit Test Descriptions for Phase 3
Web: 
1. Make sure user can be authenticated
2. Test that user can make a comment/edit a comment
3. Make sure like and dislike buttons work

Admin: 
1. Make sure user can add rows in the post and comment table
2. Make sure user is able to update tables using the like/dislike buttons
3. Make sure entries in tables can be changed such as likes
4. Be able to remove rows from the post table

Backend: 
1. Test that the memcache system works by storing and retriving some information 

Mobile:  
1. Make sure like and dislike changes the appearance of the buttons
2. Use MobileTest to see if pages change with the appropriate click
3. Make sure that the scroll feature for the main page and comment page work
4. Make sure that buttons are connected to the right routes and the data changes on the  
                webpage

## User Stories

1. U1: As an Admin I want to edit the database directly because I need to update and maintain it. (Manual test)

2. U2: As an Admin I want to add/drop tables for maintenance purposes. (Manual test)

3. U3: As an Admin I want to manage new columns because I want to maintain parts of the database data. (Manual Test)

4. U4: As an Admin  I want to manage new tables because I want to maintain the database as a whole. (Manual Test) 

5. U5: As an Authenticated User I want to like and unlike posts so I can show support for the posts I like. (Automatic test)

6. U6: As an Authenticated User I want to post text so I can share my ideas to others. (Automatic test)

7. U7: As an Authenticated user  I want to like posts because I want to interact with other idea creators. (Automatic test)

8. U8: As an Authenticated user  I want to edit my own comments because I may have typos on my comments. (Automatic test)

9. U9: As an Authenticated user  I want to create a user profile with personal information because I want people to know who is posting the text. (Automatic test)




## Routes
- __Get__: returns all entity from the table post
   - \GET #sessionKey/posts
   - JSON Route: 
     - "mPost_id" = int
     - "mUser_id" = int
     - "mTitle" = String
     - "mText" = String
     - "mLikes" = int
     - "mDislikes" = int
     
- __Get__: returns one entity from the table post
    - \GET #sessionKey/posts/#post_id
    - JSON Route:
        - "mPost_id" = int
        - "mUser_id" = String
        - "mTitle" = String
        - "mText" = String
        - "mLikes" = int
        - "mDislikes" = int
- __Get__: returns one entity from the table user
    - \GET #sessionKey/users/#email
    - JSON Route:
        - "mUser_id" = int
        - "mUsername" = String
        - "mName" = String
        - "mEmail" = String
        - "mSex_orient" = String
        - "mGender" = String
        - "mNote" = String  
        
- __Get__: returns all entities from the table comment associated to a post_id
    - \GET #sessionKey/comments/#post_id
    - JSON Route:
        - "mComment_id" = int
        - "mPost_id" = int
        - "mUser_Id" = int
        - "mComment" = String  
        
- __Post__: Verifies the access token and adds a new user to the associated user id
   - \POST /verify/#google_idtoken
   - JSON Route:
     -  "sessionKey" = int
     
- __Post__ : Adds a new post associated with the user.
    - \POST #sessionKey/posts
    - JSON Route: 
        - "mPost_id" = int
        
- __Post__ : Adds a new comment associated with the user
    - \POST #sessionKey/comments/#post_id
    - JSON Route: 
        - "mComment_id" = int
  
        
- __Put__: Updates an existing post in the table by specifying an post id and the user id
    - \PUT #sessionKey/posts/#email/#post_id
    - JSON Route:
        - "mPost_id" = int
        - "mUser_id" = int
        - "mTitle" = String
        - "mText" = String
- __Put__: Updates an existing comment in the table by specifying an comment id, the post id, and the user id
    - \PUT #sessionKey/comments/#email/#comment_id
    - JSON Route:
        - "mComment_id" = int
        - "mPost_id" = int
        - "mUser_id" = int
        - "mComment" = String
        
- __Put__: Like for post
    - \PUT #sessionKey/posts/#post_id/like
    - JSON Route:
        - "mPost_id" = int
        - "mLikes" = int
      
- __Put__: Dislike for post
    - \PUT #sessionKey/posts/#post_id/dislike
    -  JSON Route:
        - "mPost_id" = int
        - "mDislikes" = int

  
## Javadoc documentation
Read HTML file for App.java and Database.java [here](./backend\src\main\java\edu\lehigh\cse216\yap224\backend\JavadocHTMLFiles\index-all.html) 

  
## Test Description
- __Backend__: Use the AppTest.java file to create script that automatically runs the routes for get, post, put, and delete then makes sures then Make sure that posts added through the front end/mobile actually appear in database

Use Database.java to test the different methods in the Database.java class

- __Admin__: Check that tables can be edited and created/deleted

- __Web front-end__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly

- __Mobile__: 

  1. Make sure like and dislike changes the appearance of the buttons 
  2. Use MobileTest to see if pages change with the appropriate click
  3. Make sure that the scroll feature for the main page and comment page work
  4. Make sure that buttons are connected to the right routes and the data changes on the webpage



## ERD

![ERD Diagram for backend](images/ED_Diagram_phase2.jpeg)

## State Machines (idea, user)

![State Machine](images/StateMachine.jpg)

![State Machine](images/userStateMachineUpdate.png)

## System Diagram

![System Diagram](images/SystemDiagram.png)

## Desktop and Mobile Renders

![Desktop 1](images/loginWeb.png)

![Desktop 2](images/profileWeb.png)

![Desktop 3](images/editProfileWeb.png)

![Desktop 4](images/homeWeb.png)

![Desktop 5](images/otherProfileWeb.png)

![Desktop 6](images/postWeb.png)


## Mobile Page

![Login Page](images/Login.PNG)

![Main Page](images/PostPage.jpg)

![User Profile Page](images/ProfilePage.jpg)

![Comment Page](images/Comments.jpg)

![Edit Comment Page](images/EditComment.jpg)

![Add Comment Page](images/AddComment.jpg)

![Add Post Page](images/AddingPost.jpg)



## Invite Trello Link 
 https://trello.com/invite/b/ktZREaYO/6223f419fbe085536c4393c2cb3ff528/tutorial-board-1
 
## Link for folder with project resources
https://drive.google.com/drive/folders/1bG9Afm0etEM5-NCcldsnn2dRRtsRPe1x?usp=sharing

## Documentation for React branch:
[React Documentation](thebuzz/docs/index.html)
