package edu.lehigh.cse216.yap224.admin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// ResultSetMetaData is a new import that I added to create an object that can return ColumnNames. Refer to getColNames to see implementation.
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.LinkPermission;
import java.util.ArrayList;

public class Database {
    /**
     * Prepared statements to find a particular row
     */
    private PreparedStatement mOnePost;
    private PreparedStatement mOneUser;
    /**
     * Prepared statements do delete particular row
     */
    private PreparedStatement mDeleteUser;
    private PreparedStatement mDeletePost;
    /**
     * Prepared statements to add additional row
     */
    private PreparedStatement mInsertUser;
    private PreparedStatement mInsertPost;


    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOne;

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statements for creating the tables in our database
     */
    private PreparedStatement mCreateTableUser;
    private PreparedStatement mCreateTablePost;
    private PreparedStatement mCreateTableComment;
    private PreparedStatement mCreateTableLike;
    private PreparedStatement mCreateTableDislike;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;
    private PreparedStatement mDropTableUser;
    private PreparedStatement mDropTablePost;
    private PreparedStatement mDropTableComment;
    private PreparedStatement mDropTableLike;
    private PreparedStatement mDropTableDislike;
    /**
     * A prepared statement for getting the name of the columns in our database
     */
    private PreparedStatement mGetColNames;


    /**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */
    public static class RowData {
        /**
         * The ID of this row of the database
         */
        int mId;
        /**
         * The subject stored in this row
         */
        String mSubject;
        /**
         * The message stored in this row
         */
        String mMessage;
        /**
         * The likes stored in this row
         */
        int mLikes;
        /**
         * Construct a RowData object by providing values for its fields
         * @param id represents the id 
         * @param subject represents the subject
         * @param message represents the message 
         * @param likes  represents the number of likes
         */
        public RowData(int id, String subject, String message, int likes) {
            mId = id;
            mSubject = subject;
            mMessage = message;
            mLikes = likes;
        }
    }

    public static class UserRowData {
        int mUser_id;
        String mUsername;
        String mEmail;
        String mSex_orient;
        String mGender;
        String mNote;
        
        public UserRowData(int user_id, String username, String email, String sex_orient, String gender, String note) {
            mUser_id = user_id;
            mUsername = username;
            mEmail = email;
            mSex_orient = sex_orient;
            mGender = gender;
            mNote = note;
        }
    }

    public static class PostRowData {
        int mPost_id;
        int mUser_id;
        String mTitle;
        String mText;

        public PostRowData(int post_id, int user_id, String title, String text) {
            mPost_id = post_id;
            mUser_id = user_id;
            mTitle = title;
            mText = text;
        }
    }

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String db_url) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find postgresql driver");
            return null;
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception

            //For primary key: primary key (pkey)
            //For foreign key (fkey1) references table1
            //OR  foreign key (fkey1, fkey2) references table1, table2

            /*db.mCreateTable = db.mConnection.prepareStatement(
                    "CREATE TABLE tblData (id SERIAL PRIMARY KEY, subject VARCHAR(50) "
                    + "NOT NULL, message VARCHAR(1024) NOT NULL, likes int NOT NULL)");*/
            
            //Create USER table
            db.mCreateTableUser = db.mConnection.prepareStatement(
                "CREATE TABLE userTable (user_id SERIAL, username VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, sex_orient VARCHAR(50) NOT NULL, gender VARCHAR(50), note VARCHAR(50), primary key (user_id))");

            //Create POST table
            db.mCreateTablePost = db.mConnection.prepareStatement(
                "CREATE TABLE postTable (post_id SERIAL, user_id int NOT NULL, title VARCHAR(50) NOT NULL, text VARCHAR(500) NOT NULL, primary key (post_id), foreign key (user_id) references userTable)");

            //Create COMMENT table
            db.mCreateTableComment = db.mConnection.prepareStatement(
                "CREATE TABLE commentTable (comment_id SERIAL, user_id int NOT NULL, post_id int NOT NULL, coment_val VARCHAR(500) NOT NULL, primary key (comment_id), foreign key (user_id) references userTable, foreign key (post_id) references postTable)");

            //Create LIKE table
            db.mCreateTableLike = db.mConnection.prepareStatement(
                "CREATE TABLE likeTable (user_id SERIAL, post_id int NOT NULL, primary key (user_id, post_id), foreign key (user_id) references userTable, foreign key (post_id) references postTable)");

            //Create DISLIKE table
            db.mCreateTableDislike = db.mConnection.prepareStatement(
                "CREATE TABLE dislikeTable (user_id SERIAL, post_id int NOT NULL, primary key (user_id, post_id), foreign key (user_id) references userTable, foreign key (post_id) references postTable)");
            
            db.mDropTableUser = db.mConnection.prepareStatement("DROP TABLE userTable");
            db.mDropTablePost = db.mConnection.prepareStatement("DROP TABLE postTable");
            db.mDropTableComment = db.mConnection.prepareStatement("DROP TABLE commentTable");
            db.mDropTableLike = db.mConnection.prepareStatement("DROP TABLE likeTable");
            db.mDropTableDislike = db.mConnection.prepareStatement("DROP TABLE dislikeTable");

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT * FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET subject = ?, message = ?,  likes = ? WHERE id = ?");
            db.mGetColNames = db.mConnection.prepareStatement("SELECT * FROM tblData");

            //Display one post
            db.mOnePost = db.mConnection.prepareStatement("SELECT * from postTable WHERE post_id=?");
            //Display one user
            db.mOneUser = db.mConnection.prepareStatement("SELECT * from userTable WHERE user_id=?");
            //Delete User Row
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM userTable WHERE user_id = ?");
            //Delete Post Row
            db.mDeletePost = db.mConnection.prepareStatement("DELETE FROM postTable WHERE post_id=?");
            //Insert User
            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO userTable VALUES (default, ?, ?, ?, ?, ?)");
            //Insert Post
            db.mInsertPost = db.mConnection.prepareStatement("INSERT INTO postTable VALUES (default, ?, ?, ?)");


        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will alw  ays be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * @param likes The number of likes for this new row
     * @return The number of rows that were inserted
     */
    int insertRow(String subject, String message, int likes) {
        int count = 0;
        try {
            mInsertOne.setString(1, subject);
            mInsertOne.setString(2, message);
            mInsertOne.setInt(3, likes);
            count += mInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertUser(String username, String email, String sex_orient, String gender, String note) {
        int count = 0;
        try {
            mInsertUser.setString(1, username);
            mInsertUser.setString(2, email);
            mInsertUser.setString(3, sex_orient);
            mInsertUser.setString(4, gender);
            mInsertUser.setString(5, note);
            count += mInsertUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertPost(int user_id, String title, String text) {
        int count = 0;
        try {
            mInsertPost.setInt(1, user_id);
            mInsertPost.setString(2, title);
            mInsertPost.setString(3, text);
            count += mInsertPost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAll() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowData(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),rs.getInt("likes")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific row, by User_ID
     * 
     * @param user_id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    UserRowData selectmOneUser(int user_id) { 
        UserRowData res = null;
        try {
            mOneUser.setInt(1, user_id);
            ResultSet rs = mOneUser.executeQuery();
            if (rs.next()) {
                res = new UserRowData(rs.getInt("user_id"), rs.getString("username"),rs.getString("email"),rs.getString("sex_orient"),rs.getString("gender"),rs.getString("note"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param post_id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    PostRowData selectmOnePost(int post_id) { 
        PostRowData res = null;
        try {
            mOnePost.setInt(1, post_id);
            ResultSet rs = mOnePost.executeQuery();
            if (rs.next()) {
                res = new PostRowData(rs.getInt("post_id"), rs.getInt("user_id"), rs.getString("title"), rs.getString("text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowData selectOne(int id) { 
        RowData res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowData(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),rs.getInt("likes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    /***
     * Get all column names from the table.
     * 
     * @param none
     * 
     * @return the column names as strings from the table.
     */
    ArrayList<String> getColNames(){
        ArrayList<String> names = new ArrayList<String>();
        try {
            
            ResultSet rs = mGetColNames.executeQuery();
            // Create ResultSetMetaData object (rsmd) by calling getMetaData() function from the ResultSet object (rs).
            ResultSetMetaData rsmd = rs.getMetaData();
            // Get columnCount by using getColumnCount() method from rsmd.
            int columnCount = rsmd.getColumnCount();
            // Get column name by using getColumnName(index) method from rsmd. The index represents the ascending order in which columns appear (starting from 1 which is id)
            // For loop adds all column names into the Arraylist<String> and after returns it.
            for (int i = 1; i <= columnCount; i++ ) {
                names.add(rsmd.getColumnName(i));
              }
            rs.close();
            return names;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRow(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a User row by User ID
     * 
     * @param user_id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteUser(int id) {
        int res = -1;
        try {
            mDeleteUser.setInt(1, id);
            res = mDeleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a Post row by Post ID
     * 
     * @param post_id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deletePost(int id) {
        int res = -1;
        try {
            mDeletePost.setInt(1, id);
            res = mDeletePost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * @param subject The new subject contents
     * @param likes The new number of likes
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOne(int id, String subject, String message, int likes) { 
        int res = -1;
        try {
            mUpdateOne.setString(1, subject);
            mUpdateOne.setString(2, message);
            mUpdateOne.setInt(3, likes);
            mUpdateOne.setInt(4, id);
            res = mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    // /**
    //  * Create tblData.  If it already exists, this will print an error
    //  */
    // void createTable() {
    //     try {
    //         mCreateTable.execute();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * Create all tables.  If it already exists, this will print an error
     */
    void createTable() {
        try {
            mCreateTableUser.execute();
            mCreateTablePost.execute();
            mCreateTableComment.execute();
            mCreateTableLike.execute();
            mCreateTableDislike.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTable() {
        try {
            mDropTableComment.execute();
            mDropTableLike.execute();
            mDropTableDislike.execute();
            mDropTablePost.execute();
            mDropTableUser.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}