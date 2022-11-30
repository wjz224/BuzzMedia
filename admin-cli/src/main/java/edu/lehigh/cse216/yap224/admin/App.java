package edu.lehigh.cse216.yap224.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import edu.lehigh.cse216.yap224.admin.Database.PostRowData;
import edu.lehigh.cse216.yap224.admin.Database.UserRowData;

import java.util.Date;

/**
 * App is our basic admin app.  For now, it is a demonstration of the six key 
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create all tables");                  //Done
        System.out.println("  [D] Drop All Tables");                    //Done
        //System.out.println("  [1] Query for a specific row");
        System.out.println("  [A] Query for a specific post");          //Done
        System.out.println("  [B] Query for a specific user");          //Done
        System.out.println("  [I] Query for a specific commment");      //Done
        //System.out.println("  [*] Query for all rows");
        System.out.println("  [C] Query for all post");
        System.out.println("  [O] Query for all users");
        System.out.println("  [L] Query for all comments");
        //System.out.println("  [-] Delete a row");
        System.out.println("  [E] Delete a user");                      //Done
        System.out.println("  [F] Delete a post");                      //Done
        System.out.println("  [K] Delete a comment");                   //Done
        //System.out.println("  [+] Insert a new row");
        System.out.println("  [G] Insert a new post");                  //Done
        System.out.println("  [H] Insert a new user");                  //Done
        System.out.println("  [J] Insert a new comment");               //Done
        System.out.println("  [M] Like a post");                        //Done
        System.out.println("  [N] Un-like a post");                     //Done
        System.out.println("  [P] Dislike a post");                     //Done
        System.out.println("  [Q] Un-dislike a post");                  //Done
        System.out.println("  [R] Number of likes on post");               //
        System.out.println("  [S] Number of dislikes on post");               //
        System.out.println("  [Y] Delete posts older than a day");
        System.out.println("  [a] Edit a user's Username");
        //System.out.println("  [b] Edit a user's Name");
        System.out.println("  [b] Edit a user's Email");
        System.out.println("  [c] Edit a user's Sexual Orientation");
        System.out.println("  [d] Edit a user's Gender");
        System.out.println("  [e] Edit a user's Note");
        System.out.println("  [f] Edit a post's User");
        System.out.println("  [g] Edit a user's Title");
        System.out.println("  [h] Edit a user's Text");
        System.out.println("  [<] Invalidate a User");
        System.out.println("  [>] Validate a User");
        System.out.println("  [i] Edit a comment");
        System.out.println("  [k] Alter a Table");
        System.out.println("  [~] Update a row");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        /**
         * The valid characters that the user can enter 
         */
        String actions = "TDABICDLEFKGHJOMNPQRSTYabcdefghi<>jk~q?";

        /**
         *  While loop that continues until the user enters the q character.
        */ 
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {
        /**
         *  get the Postgres configuration from the environment
         */
        Map<String, String> env = System.getenv();
        String db_url = env.get("DATABASE_URL");
        
        /**
         * Get a fully-configured connection to the database, or exit  immediately
         */
        Database db = Database.getDatabase(db_url);
        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            //     function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                db.createTable();
            } else if (action == 'D') {
                db.dropTable();
            } else if (action == '1') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                Database.RowData res = db.selectOne(id);
                if (res != null) {
                    System.out.println("  [" + res.mId + "] " + res.mSubject);
                    System.out.println("  --> " + res.mMessage);
                }
            }
            
            //Find Specific Post
            else if (action == 'A') {
                int post_id = getInt(in, "Enter the post ID");
                if (post_id == -1)
                    continue;
                Database.PostRowData res = db.selectmOnePost(post_id);
                if (res != null) {
                    System.out.println(res.mPost_id + " " + res.mUser_id + " " + res.mTitle + " " + res.mText);
                }
            }

            //Find Specific User
            else if (action == 'B') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;
                Database.UserRowData res = db.selectmOneUser(user_id);
                if (res != null) {
                    System.out.println(res.mUser_id +  " " + res.mUsername +  " " + res.mEmail + " " + res.mGender);
                    System.out.println("  --> " + res.mNote);
                }
            }

            //Find Specific Comment
            else if (action == 'I') {
                int comment_id = getInt(in, "Enter the Comment ID");
                if (comment_id == -1)
                    continue;
                Database.CommentRowData res = db.selectmOneComment(comment_id);
                if (res != null) {
                    System.out.println(res.mComment_id +  " " + res.mUser_id +  " " + res.mPost_id + " " + res.mComment);
                }
            }

            // Print all rows from Database table
            else if (action == '*') {
                ArrayList<Database.RowData> res = db.selectAll();
                ArrayList<String> colNames = db.getColNames();
                if (res == null)
                    continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                // print all column names
                for(String names : colNames){
                    if(names.equals("id")){
                        System.out.print(" [" + names + "] ");
                    }
                    else{
                        System.out.print(names + " ");
                    }
                }
                System.out.println();
                for (Database.RowData rd : res) {
                    System.out.println("  [" + rd.mId + "] " + rd.mSubject + " " + rd.mMessage + " " + rd.mLikes);
                }
            }

            // Print all users from usertable
            else if (action == 'O') {
                ArrayList<Database.UserRowData> res = db.selectAllUser();
                ArrayList<String> colNames = db.getColNames();
                if (res == null)
                    continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                // print all column names
                for(String names : colNames){
                    if(names.equals("id")){
                        System.out.print(" [" + names + "] ");
                    }
                    else{
                        System.out.print(names + " ");
                    }
                }
                System.out.println();
                for (Database.UserRowData rd : res) {
                    System.out.println(rd.mUser_id +  " " + rd.mUsername +  " " + rd.mEmail + " " + rd.mGender);
                    System.out.println("  --> " + rd.mNote);
                }
            }

            // Print all post from posttable
            else if (action == 'C') {
                ArrayList<Database.PostRowData> res = db.selectAllPost();
                ArrayList<String> colNames = db.getColNames();
                if (res == null)
                    continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                // print all column names
                for(String names : colNames){
                    if(names.equals("id")){
                        System.out.print(" [" + names + "] ");
                    }
                    else{
                        System.out.print(names + " ");
                    }
                }
                System.out.println();
                for (Database.PostRowData rd : res) {
                    UserRowData temp = db.selectmOneUser(rd.mUser_id);
                    if (temp.mValid){
                        System.out.println(rd.mPost_id +  " " + rd.mUser_id +  " " + rd.mTitle + " " + rd.mText + " " + rd.mLastAccessed + " " + rd.mFilename + " " + rd.mFile);
                    }
                }
            }

            // Print all post from posttable help
            else if (action == 'L') {
                ArrayList<Database.CommentRowData> res = db.selectAllComment();
                ArrayList<String> colNames = db.getColNames();

                if (res == null){
                    continue;}
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                // print all column names
                for(String names : colNames){
                    if(names.equals("id")){
                        System.out.print(" [" + names + "] ");
                    }
                    else{
                        System.out.print(names + " ");
                    }
                }

                System.out.println();
                for (Database.CommentRowData rd : res) {
                    UserRowData temp = db.selectmOneUser(rd.mUser_id);
                    if (temp.mValid){
                        System.out.println(rd.mComment_id + " " + rd.mPost_id +  " " + rd.mUser_id +  " " + rd.mComment + " " + rd.mFilename + " " + rd.mFile);
                    }
                }
            }


            // Delete a row from Database  table
            else if (action == '-') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                int res = db.deleteRow(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            }

            else if (action == 'Y'){
                ArrayList<Database.PostRowData> res = db.selectAllPost();
                int numberDeleted;
                if (res == null)
                    continue;
                //System.out.println("  Current Times For Each Post");
                //System.out.println("  -------------------------");
                // print all column names
                
                System.out.println();
                for (Database.PostRowData rd : res) {
                    Date postDate = new Date(Date.parse(rd.mLastAccessed));
                    Date currentTime = new Date();
                    if ((currentTime.getTime() - postDate.getTime()) > (30000)){
                        System.out.println(rd.mPost_id +  " " + rd.mLastAccessed + " Deleted: " + postDate);
                        numberDeleted = db.deletePost(rd.mPost_id);
                    }
                    /*else{
                        System.out.println(rd.mPost_id +  " " + rd.mLastAccessed + " Not Going To be Deleted: " + postDate);
                    }*/

                }
            }

            // Delete a row from User table
            else if (action == 'E') {
                int user_id = getInt(in, "Enter the User ID");
                if (user_id == -1)
                    continue;
                int res = db.deleteUser(user_id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            }

            // Delete a row from Post table
            else if (action == 'F') {
                int post_id = getInt(in, "Enter the Post ID");
                if (post_id == -1)
                    continue;
                int res = db.deletePost(post_id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            }

            // Delete a comment from comment table
            else if (action == 'K') {
                int comment_id = getInt(in, "Enter the Comment ID");
                if (comment_id == -1)
                    continue;
                int res = db.deleteComment(comment_id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
            }


            // Add a row to Database by reading user input for each column 
            else if (action == '+') {
                String subject = getString(in, "Enter the subject");
                String message = getString(in, "Enter the message");
                int likes = getInt(in, "Enter the number of likes");
                if (subject.equals("") || message.equals(""))
                    continue;
                int res = db.insertRow(subject, message, likes);
                System.out.println(res + " rows added");
            } 

            // Add a User row to Database by reading user input for each column 
            else if (action == 'H') {
                String username = getString(in, "Enter the username");
                String email = getString(in, "Enter the email");
                String sex_orient = getString(in, "Enter the sexual orientation");
                String gender = getString(in, "Enter the gender");
                String note = getString(in, "Enter the note");
                String profile = getString(in, "Enter the ProfileURL");
                // if (phone_num.equals("") || message.equals(""))
                //     continue;
                int res = db.insertUser(username, email, sex_orient, gender, note, profile);
                System.out.println(res + " rows added");
            } 

            // Add a Post row to Database by reading user input for each column 
            else if (action == 'G') {
                int user_id = getInt(in, "Enter the user ID");
                String title = getString(in, "Enter the title");
                String text = getString(in, "Enter the text");
                String Filename = getString(in, "Enter the Filename");
                String file = getString(in, "Enter the file");
                // if (subject.equals("") || message.equals(""))
                //     continue;
                int res = db.insertPost(user_id, title, text, Filename, file);
                System.out.println(res + " rows added");
            } 

            // Add a Comment row to Database by reading user input for each column 
            else if (action == 'J') {
                int user_id = getInt(in, "Enter the user ID: ");
                int post_id = getInt(in, "Enter post ID: ");
                String comment = getString(in, "Enter the commment: ");
                String Filename = getString(in, "Enter the Filename");
                String file = getString(in, "Enter the file");
                // if (subject.equals("") || message.equals(""))
                //     continue;
                int res = db.insertComment(user_id, post_id, comment, Filename, file);
                System.out.println(res + " rows added");
            } 

//Find Specific User
            else if (action == 'B') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;
                Database.UserRowData res = db.selectmOneUser(user_id);
                if (res != null) {
                    System.out.println(res.mUser_id +  " " + res.mUsername +  " " + res.mEmail + " " + res.mGender);
                    System.out.println("  --> " + res.mNote);
                }
            }

            //Edit  User
            else if (action == 'a') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new username to be: ");
                String usernameNew = scn.nextLine();

                int res = db.editUserUsername(user_id, usernameNew);
            }
            // else if (action == 'b') {
            //     int user_id = getInt(in, "Enter the user ID");
            //     if (user_id == -1)
            //         continue;

            //     Scanner scn = new Scanner(System.in);
            //     System.out.print("What would you like the new name to be: ");
            //     String nameNew = scn.nextLine();

            //     int res = db.editUserName(user_id, nameNew);
            // }
            else if (action == 'b') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new email to be: ");
                String emailNew = scn.nextLine();

                int res = db.editUserEmail(user_id, emailNew);
            }
            else if (action == 'c') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new sexual orientation to be: ");
                String soNew = scn.nextLine();

                int res = db.editUserSexOrient(user_id, soNew);
            }
            else if (action == 'd') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new gender to be: ");
                String nameGender = scn.nextLine();

                int res = db.editUserGender(user_id, nameGender);
            }
            else if (action == 'e') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new note to be: ");
                String nameNote = scn.nextLine();

                int res = db.editUserNote(user_id, nameNote);
            }
            else if (action == 'f') {
                int post_id = getInt(in, "Enter the post ID");
                if (post_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new user (user id) to be: ");
                int newUser = scn.nextInt();

                int res = db.editPostUser(post_id, newUser);
            }
            else if (action == 'g') {
                int post_id = getInt(in, "Enter the post ID");
                if (post_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new title to be: ");
                String newTitle = scn.nextLine();

                int res = db.editPostTitle(post_id, newTitle);
            }
            else if (action == 'h') {
                int post_id = getInt(in, "Enter the post ID");
                if (post_id == -1)
                    continue;

                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new text to be: ");
                String newText = scn.nextLine();

                int res = db.editPostText(post_id, newText);
            }
            else if (action == 'i') {
                int comment_id = getInt(in, "Enter the comment ID");
                if (comment_id == -1)
                    continue;
                
                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new comment to be: ");
                String newComment = scn.nextLine();

                int res = db.editCommentComment(comment_id, newComment);
            }

            else if (action == 'k') {
                String tableName = getString(in, "Enter the table that needs to be edited");
                String columnName = getString(in, "Enter the column that is to be added");

            

                int res = db.alterDataSet(tableName, columnName);
            }

            else if (action == 'l') {
                int comment_id = getInt(in, "Enter the comment ID");
                if (comment_id == -1)
                    continue;
                
                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new comment Filename to be: ");
                String newFilename = scn.nextLine();

                int res = db.editCommentFilename(comment_id, newFilename);
            }

            else if (action == 'm') {
                int comment_id = getInt(in, "Enter the comment ID");
                if (comment_id == -1)
                    continue;
                
                Scanner scn = new Scanner(System.in);
                System.out.print("What would you like the new comment file to be: ");
                String newFile = scn.nextLine();

                int res = db.editCommentFile(comment_id, newFile);
            }


            //Like a post
            else if (action == 'M') {
                int user_id = getInt(in, "Enter the user ID");
                int post_id = getInt(in, "Enter the post ID");
                
                int res = db.insertLike(user_id, post_id);
                System.out.println(res + " rows added");
            } 
            //Dislike a post
            else if (action == 'P') {
                int user_id = getInt(in, "Enter the user ID");
                int post_id = getInt(in, "Enter the post ID");
                
                int res = db.insertDislike(user_id, post_id);
                System.out.println(res + " rows added");
            } 
            //unLike post
            else if (action == 'N') {
                int user_id = getInt(in, "Enter the user ID");
                int post_id = getInt(in, "Enter the post ID");
                
                int res = db.removeLike(user_id, post_id);
                System.out.println(res + " row removed");
            } 
            //Un-Dislike a post
            else if (action == 'Q') {
                int user_id = getInt(in, "Enter the user ID");
                int post_id = getInt(in, "Enter the post ID");
                
                int res = db.removeDislike(user_id, post_id);
                System.out.println(res + " row removed");
            } 
            //Number likes
            else if (action == 'R') {
                int post_id = getInt(in, "Enter the post ID");
                
                ResultSet numLike = db.findLike(post_id);
                try{
                    if(numLike.next())
                        System.out.println(numLike.getInt("Likes"));
                } catch (SQLException e) {
                    System.err.println("Error: Connection.close() threw a SQLException");
                    e.printStackTrace();
                }
            } 
            //Number dislikes
            else if (action == 'S') {
                int post_id = getInt(in, "Enter the post ID");
                
                ResultSet numDislike = db.findDislike(post_id);
                try{
                    if(numDislike.next())
                        System.out.println(numDislike.getInt("Dislikes"));
                } catch (SQLException e) {
                    System.err.println("Error: Connection.close() threw a SQLException");
                    e.printStackTrace();
                }
            } 


            // Update a row by reading in user input
            else if (action == '~') {
                int id = getInt(in, "Enter the row ID :> ");
                if (id == -1)
                    continue;
                // Neet to implement subject and likes update
                String newSubject = getString(in, "Enter the new subject");
                String newMessage = getString(in, "Enter the new message");
                int newLikes = getInt(in, "Enter the new number of likes");
                int res = db.updateOne(id,newSubject,newMessage, newLikes);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            }

            //Invalidate Or Validate User
            else if (action == '<') {
                int id = getInt(in, "Enter the User_ID to invalidate :> ");
                if (id == -1)
                    continue;
                // Neet to implement subject and likes updat
                int res = db.invalidateUser(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " User Invalidated updated");
            }

            else if (action == '>') {
                int id = getInt(in, "Enter the User_ID to Validate :> ");
                if (id == -1)
                    continue;
                // Neet to implement subject and likes updat
                int res = db.validateUser(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " User Validated");
            }
        }
        // Always remember to disconnect from the database when the program 
        // exits
        db.disconnect();
    }
}