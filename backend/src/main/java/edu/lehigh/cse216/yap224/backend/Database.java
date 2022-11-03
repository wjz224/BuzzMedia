package edu.lehigh.cse216.yap224.backend;


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
    private PreparedStatement mOneComment;
    private PreparedStatement mOneUser;
    
    /**
     * Prepared statements do delete particular row
     */
    private PreparedStatement mDeleteUser;
    private PreparedStatement mDeletePost;
    private PreparedStatement mDeleteComment;
    /**
     * Prepared statements to add additional row
     */
    private PreparedStatement mInsertUser;
    private PreparedStatement mInsertPost;
    private PreparedStatement mInsertComment;


    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAll;
    private PreparedStatement mSelectAllUser;
    private PreparedStatement mSelectAllPost;
    private PreparedStatement mSelectAllComment;
    private PreparedStatement mSelectAllCommentPost;

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
     * A prepared statement for the getting the user_id from the user table using the user email
     */
    private PreparedStatement mGetUserId;
    /**
     * A prepared statement edit functions
     */
    private PreparedStatement mEditUserUsername;
    private PreparedStatement mEditUserName;
    private PreparedStatement mEditUserEmail;
    private PreparedStatement mEditUserGender;
    private PreparedStatement mEditUserSexOrient;
    private PreparedStatement mEditUserNote;
    private PreparedStatement mUpdatePost;

    private PreparedStatement mEditPostUser;
    private PreparedStatement mEditPostTitle;
    private PreparedStatement mEditPostText;
    private PreparedStatement mEditCommentComment;
    


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

    public static class CommentRowData {
        int mComment_id;
        int mPost_id;
        int mUser_id;
        String mComment;

        public CommentRowData(int comment_id, int post_id, int user_id, String comment) {
            mComment_id = comment_id;
            mPost_id = post_id;
            mUser_id = user_id;
            mComment = comment;
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
                "CREATE TABLE userTable (user_id SERIAL, username VARCHAR(50) NOT NULL, email UNIQUE VARCHAR(50) NOT NULL, sex_orient VARCHAR(50) NOT NULL, gender VARCHAR(50), note VARCHAR(50), primary key (user_id))");

            //Create POST table
            db.mCreateTablePost = db.mConnection.prepareStatement(  
                "CREATE TABLE postTable (post_id SERIAL, user_id int NOT NULL, title VARCHAR(50) NOT NULL, text VARCHAR(500) NOT NULL, primary key (post_id), foreign key (user_id) references userTable)");

            //Create COMMENT table
            db.mCreateTableComment = db.mConnection.prepareStatement(
                "CREATE TABLE commentTable (comment_id SERIAL, user_id int NOT NULL, post_id int NOT NULL, comment_val VARCHAR(500) NOT NULL, primary key (comment_id), foreign key (user_id) references userTable, foreign key (post_id) references postTable)");

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

            db.mSelectAllUser = db.mConnection.prepareStatement("SELECT * FROM userTable");
            db.mSelectAllPost = db.mConnection.prepareStatement("SELECT * FROM postTable");
            db.mSelectAllComment = db.mConnection.prepareStatement("SELECT * FROM commentTable");
            db.mSelectAllCommentPost = db.mConnection.prepareStatement("SELECT * FROM commentTable WHERE post_id =?");
            // Get user_id from email
            db.mGetUserId = db.mConnection.prepareStatement("SELECT user_id from userTable WHERE email = ?");
            //Display one post
            db.mOnePost = db.mConnection.prepareStatement("SELECT * from postTable WHERE post_id=?");
            //Display one user
            db.mOneUser = db.mConnection.prepareStatement("SELECT * from userTable WHERE user_id=?");
            //Display one comment
            db.mOneComment = db.mConnection.prepareStatement("SELECT * from commentTable WHERE comment_id=?");
            //Delete User Row
            db.mDeleteUser = db.mConnection.prepareStatement("DELETE FROM userTable WHERE user_id = ?");
            //Delete Post Row
            db.mDeletePost = db.mConnection.prepareStatement("DELETE FROM postTable WHERE post_id=?");
             //Delete Comment Row
             db.mDeleteComment = db.mConnection.prepareStatement("DELETE FROM commentTable WHERE comment_id=?");
            //Insert User
            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO userTable VALUES (default, ?, ?, ?, ?, ?)");
            //Insert Post
            db.mInsertPost = db.mConnection.prepareStatement("INSERT INTO postTable VALUES (default, ?, ?, ?)");
            //Comment Post
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO commentTable VALUES (default, ?, ?, ?)");

            //Edit functions
            db.mEditUserUsername = db.mConnection.prepareStatement("UPDATE userTable SET username =? WHERE user_id =?");
            //db.mEditUserName = db.mConnection.prepareStatement("UPDATE userTable SET name =? WHERE user_id =?");
            db.mEditUserEmail = db.mConnection.prepareStatement("UPDATE userTable SET email =? WHERE user_id =?");
            db.mEditUserGender = db.mConnection.prepareStatement("UPDATE userTable SET gender =? WHERE user_id =?");
            db.mEditUserSexOrient = db.mConnection.prepareStatement("UPDATE userTable SET sex_orient =? WHERE user_id =?");
            db.mEditUserNote = db.mConnection.prepareStatement("UPDATE userTable SET note =? WHERE user_id =?");
            db.mUpdatePost = db.mConnection.prepareStatement("Update postTable SET user_id =? , title =?, text=? WHERE post_id =?");
            db.mEditPostUser = db.mConnection.prepareStatement("UPDATE postTable SET user_id =? WHERE post_id =?");
            db.mEditPostTitle = db.mConnection.prepareStatement("UPDATE postTable SET title =? WHERE post_id =?");
            db.mEditPostText = db.mConnection.prepareStatement("UPDATE postTable SET text =? WHERE post_id =?");
            db.mEditCommentComment = db.mConnection.prepareStatement("UPDATE commentTable SET comment_val =? WHERE comment_id =?");

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
    
    /** 
     * Get user-id from userTable based on given email
    */
    int getUserId(String email){
        int user_id = 0;
        try{
            mGetUserId.setString(1, email);
            // user_id = mGetUserId.executeUpdate();
            ResultSet rs = mGetUserId.executeQuery();
            while(rs.next()){
                user_id = rs.getInt("USER_ID");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user_id; 
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

    int insertComment(int user_id, int post_id, String comment_val) {
        int count = 0;
        try {
            mInsertComment.setInt(1, user_id);
            mInsertComment.setInt(2, post_id);
            mInsertComment.setString(3, comment_val);
            count += mInsertComment.executeUpdate();
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
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<UserRowData> selectAllUser() {
        ArrayList<UserRowData> res = new ArrayList<UserRowData>();
        try {
            ResultSet rs = mSelectAllUser.executeQuery();
            while (rs.next()) {
                res.add(new UserRowData(rs.getInt("user_id"), rs.getString("username"),rs.getString("email"),rs.getString("sex_orient"),rs.getString("gender"),rs.getString("note")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<PostRowData> selectAllPost() {
        ArrayList<PostRowData> res = new ArrayList<PostRowData>();
        try {
            ResultSet rs = mSelectAllPost.executeQuery();
            while (rs.next()) {
                res.add(new PostRowData(rs.getInt("post_id"), rs.getInt("user_id"), rs.getString("title"), rs.getString("text")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<CommentRowData> selectAllComment() {
        ArrayList<CommentRowData> res = new ArrayList<CommentRowData>();
        try {
            ResultSet rs = mSelectAllComment.executeQuery();
            while (rs.next()) {
                res.add(new CommentRowData(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("post_id"), rs.getString("comment_val")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    ArrayList<CommentRowData> selectAllCommentPost(int post_id) {
        ArrayList<CommentRowData> res = new ArrayList<CommentRowData>();
        try {
            mSelectAllCommentPost.setInt(1, post_id);
            ResultSet rs = mSelectAllCommentPost.executeQuery();
            while (rs.next()) {
                res.add(new CommentRowData(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("post_id"), rs.getString("comment_val")));
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
     * @param post_id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    CommentRowData selectmOneComment(int comment_id) { 
        CommentRowData res = null;
        try {
            mOneComment.setInt(1, comment_id);
            ResultSet rs = mOneComment.executeQuery();
            if (rs.next()) {
                res = new CommentRowData(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("post_id"), rs.getString("comment_val"));
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

    int deleteComment(int id) {
        int res = -1;
        try {
            mDeleteComment.setInt(1, id);
            res = mDeleteComment.executeUpdate();
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

    int editUserUsername(int user_id, String newUsername) { 
        int res = -1;
        try {
            mEditUserUsername.setString(1, newUsername);
            mEditUserUsername.setInt(2, user_id);
            res = mEditUserUsername.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    // int editUserName(int user_id, String newName) { 
    //     int res = -1;
    //     try {
    //         mEditUserName.setString(1, newName);
    //         mEditUserName.setInt(2, user_id);
    //         res = mEditUserName.executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    int editUserEmail(int user_id, String newEmail) { 
        int res = -1;
        try {
            mEditUserEmail.setString(1, newEmail);
            mEditUserEmail.setInt(2, user_id);
            res = mEditUserEmail.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editUserGender(int user_id, String newGender) { 
        int res = -1;
        try {
            mEditUserGender.setString(1, newGender);
            mEditUserGender.setInt(2, user_id);
            res = mEditUserGender.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editUserSexOrient(int user_id, String newSexOrient) { 
        int res = -1;
        try {
            mEditUserSexOrient.setString(1, newSexOrient);
            mEditUserSexOrient.setInt(2, user_id);
            res = mEditUserSexOrient.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editUserNote(int user_id, String newNote) { 
        int res = -1;
        try {
            mEditUserNote.setString(1, newNote);
            mEditUserNote.setInt(2, user_id);
            res = mEditUserNote.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editPostUser(int post_id, int newUser) { 
        int res = -1;
        try {
            mEditPostUser.setInt(1, newUser);
            mEditPostUser.setInt(2, post_id);
            res = mEditPostUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editPostTitle(int post_id, String newTitle) { 
        int res = -1;
        try {
            mEditPostTitle.setString(1, newTitle);
            mEditPostTitle.setInt(2, post_id);
            res = mEditPostTitle.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editPostText(int post_id, String newText) { 
        int res = -1;
        try {
            mEditPostText.setString(1, newText);
            mEditPostText.setInt(2, post_id);
            res = mEditPostText.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editCommentComment(int comment_id, String newComment) { 
        int res = -1;
        try {
            mEditCommentComment.setString(1, newComment);
            mEditCommentComment.setInt(2, comment_id);
            res = mEditCommentComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    int updatePost(int user_id, String newTitle, String newText, int post_id) { 
        int res = -1;
        try {
            mUpdatePost.setInt(1, user_id);
            mUpdatePost.setString(2, newTitle);
            mUpdatePost.setString(3, newText);
            mUpdatePost.setInt(4, post_id);
            res = mUpdatePost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}