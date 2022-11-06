package edu.lehigh.cse216.yap224.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

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
        System.out.println("  [T] Create all tables");
        System.out.println("  [D] Drop tblData");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [A] Query for a specific post");
        System.out.println("  [B] Query for a specific post");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [C] Query for all post");
        System.out.println("  [D] Query for all users");
        System.out.println("  [-] Delete a row");
        System.out.println("  [E] Delete a user");
        System.out.println("  [F] Delete a post");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [G] Insert a new post");
        System.out.println("  [H] Insert a new user");
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
        String actions = "TD1AB*CD-EF+GH~q?";

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
                Database.RowData res = db.mOnePost(post_id);
                if (res != null) {
                    System.out.println(res.title + " " + res.test);
                }
            }

            //Find Specific User
            else if (action == 'B') {
                int user_id = getInt(in, "Enter the user ID");
                if (user_id == -1)
                    continue;
                Database.RowData res = db.mOneUser(user_id);
                if (res != null) {
                    System.out.println(res.username + " " + res.first_name + " " + res.last_name + " " + res.email + " " + res.gender);
                    System.out.println("  --> " + res.note);
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
                int user_id = getInt(in, "Enter the User ID");
                String username = getString(in, "Enter the username");
                String first_name = getString(in, "Enter the first name");
                String last_name = getString(in, "Enter the last name");
                String email = getString(in, "Enter the email");
                String sex_orient = getString(in, "Enter the sexual orientation");
                int phone_num = getInt(in, "Enter the phone number");
                String gender = getString(in, "Enter the gender");
                String note = getString(in, "Enter the note");
                int likes = getInt(in, "Enter the number of likes");
                // if (phone_num.equals("") || message.equals(""))
                //     continue;
                int res = db.insertUser(user_id, username, first_name, last_name, email, sex_orient, phone_num, gender, note);
                System.out.println(res + " rows added");
            } 

            // Add a Post row to Database by reading user input for each column 
            else if (action == 'G') {
                int post_id = getInt(in, "Enter the post ID");
                int user_id = getInt(in, "Enter the user ID");
                String title = getString(in, "Enter the title");
                String text = getString(in, "Enter the text");
                // if (subject.equals("") || message.equals(""))
                //     continue;
                int res = db.insertPost(post_id, user_id, title, text);
                System.out.println(res + " rows added");
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
        }
        // Always remember to disconnect from the database when the program 
        // exits
        db.disconnect();
    }
}