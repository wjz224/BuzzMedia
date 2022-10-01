**Edit a file, create a new file, and clone from Bitbucket in under 2 minutes**

When you're done, you can delete the content in this README and update the file with details for others getting started with your repository.

*We recommend that you open this README in another tab as you perform the tasks below. You can [watch our video](https://youtu.be/0ocf7u76WSo) for a full demo of all the steps in this tutorial. Open the video in a new tab to avoid leaving Bitbucket.*

---

## Edit a file

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. Click **Source** on the left side.
2. Click the README.md link from the list of files.
3. Click the **Edit** button.
4. Delete the following text: *Delete this line to make a change to the README from Bitbucket.*
5. After making your change, click **Commit** and then **Commit** again in the dialog. The commit page will open and you’ll see the change you just made.
6. Go back to the **Source** page.

---

## Create a file

Next, you’ll add a new file to this repository.

1. Click the **New file** button at the top of the **Source** page.
2. Give the file a filename of **contributors.txt**.
3. Enter your name in the empty file space.
4. Click **Commit** and then **Commit** again in the dialog.
5. Go back to the **Source** page.

Before you move on, go ahead and explore the repository. You've already seen the **Source** page, but check out the **Commits**, **Branches**, and **Settings** pages.

---

## Clone a repository

Use these steps to clone from SourceTree, our client for using the repository command-line free. Cloning allows you to work on your files locally. If you don't yet have SourceTree, [download and install first](https://www.sourcetreeapp.com/). If you prefer to clone from the command line, see [Clone a repository](https://confluence.atlassian.com/x/4whODQ).

1. You’ll see the clone button under the **Source** heading. Click that button.
2. Now click **Check out in SourceTree**. You may need to create a SourceTree account or log in.
3. When you see the **Clone New** dialog in SourceTree, update the destination path and name if you’d like to and then click **Clone**.
4. Open the directory you just created to see your repository’s files.

Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. You can [push your change back to Bitbucket with SourceTree](https://confluence.atlassian.com/x/iqyBMg), or you can [add, commit,](https://confluence.atlassian.com/x/8QhODQ) and [push from the command line](https://confluence.atlassian.com/x/NQ0zDQ).
## User Stories

Admin
- As an Admin I want to edit the database directly because I need to update and maintain it. (Manual test)

- As an Admin I want to be able to ban users because I want to prevent users from breaking rules. (Manual test)

- As an Admin I want to get different users different tiers so I can control what these users can use. (Manual test)

- As an Admin I want to have the authority to authorize who can join the app so I can prevent users not affiliated with the company from joining the app. (Manual test)

- As an Admin I want to be able to time out users because I want to prevent users from spamming. (Manual test)

- As a User I want to make my posts private, public, or seen by only a select number of people so I can control who can see my post. (Manual test)

- As a User I want to like and unlike posts so I can show support for the posts I like. (Automatic test)

- As a User I want to post images, code files, text, and videos so I can share my ideas to others. (Automatic test)

- As a User I want to be able to write and see comments on posts because I want to be able to share and see other peoples opinions and ideas.  (Automatic test)

- As a User I want to be able to add tags so I can organize where my post go. (Automatic test)

- As a User I want to be able to set preferences so I can see posts that I want to see. (Automatic test)

- As a User I want to be able to block people so I can stop seeing posts that I don’t want to see. (Automatic test)

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
  PUT /messages/#3
  
## Test Description
- __Backend__: Use the AppTest.js file to create script that automatically runs the routes for get, post, put, and delete then makes sures then Make sure that posts added through the front end/mobile actually appear in database

- __Admin__: Check that tables can be edited and created/deleted

- __Web front-end__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly


- __Mobile__: Make sure buttons correctly display respective behavior, make sure user is able to add text to necessary fields, make sure messages are displayed correctly

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