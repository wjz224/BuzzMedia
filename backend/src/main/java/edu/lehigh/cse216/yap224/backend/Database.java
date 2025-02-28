package edu.lehigh.cse216.yap224.backend;


import java.sql.Connection;
import java.util.Date;
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

    //Like and dislike prepared statements
    private PreparedStatement mInsertLike;
    private PreparedStatement mInsertDislike;
    private PreparedStatement mRemoveLike;
    private PreparedStatement mRemoveDislike;

    //Count Likes and Dislikes
    private PreparedStatement mFindLike;
    private PreparedStatement mFindDislike;

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
    private PreparedStatement mCheckLike;
    private PreparedStatement mCheckDislike;

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
    private PreparedStatement mGetUserId;
    private PreparedStatement mGetUserProfile;
    private PreparedStatement mGetUserIdComment;

    /**
     * A prepared statement edit functions
     */
    private PreparedStatement mEditUserUsername;
    private PreparedStatement mEditUserName;
    private PreparedStatement mEditUserEmail;
    private PreparedStatement mEditUserGender;
    private PreparedStatement mEditUserSexOrient;
    private PreparedStatement mEditUserNote;

    private PreparedStatement mEditPostUser;
    private PreparedStatement mEditPostTitle;
    private PreparedStatement mEditPostText;
    private PreparedStatement mEditPostFilename;
    private PreparedStatement mEditPostFile;

    private PreparedStatement mEditCommentComment;
    private PreparedStatement mEditAccessTime;
    private PreparedStatement mInvalidateUser;
    private PreparedStatement mValidateUser;
    private PreparedStatement mUpdateComment;
    private PreparedStatement mEditCommentFilename;
    private PreparedStatement mEditCommentFile;


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
        String mProfile;
        boolean mValid;
        
        public UserRowData(int user_id, String username, String email, String sex_orient, String gender, String note, String profile, boolean valid) {
            mUser_id = user_id;
            mUsername = username;
            mEmail = email;
            mSex_orient = sex_orient;
            mGender = gender;
            mNote = note;
            mProfile = profile;
            mValid = valid;
            
        }
    }

    public static class PostRowData {
        int mPost_id;
        int mUser_id;
        String mTitle;
        String mText;
        String mLastAccessed;
        String mFilename;
        String mFile;

        public PostRowData(int post_id, int user_id, String title, String text, String access_time, String Filename, String file) {
            mPost_id = post_id;
            mUser_id = user_id;
            mTitle = title;
            mText = text;
            mLastAccessed = access_time;
            mFilename = Filename;
            mFile = file;

            
        }
    }

    public static class CommentRowData {
        int mComment_id;
        int mPost_id;
        int mUser_id;
        String mComment;
        String mFilename;
        String mFile;

        public CommentRowData(int comment_id, int post_id, int user_id, String comment, String Filename, String file) {
            mComment_id = comment_id;
            mPost_id = post_id;
            mUser_id = user_id;
            mComment = comment;
            mFilename = Filename;
            mFile = file;
        }
    }

    public static class PostLikesData {
        int mPost_id;
        int mUser_id;
        String mTitle;
        String mText;
        String mFilename;
        String mFile;
        int mLikes;
        int mDislikes;

        public PostLikesData(int post_id, int user_id, String title, String text, String Filename, String file, int likes, int dislikes) {
            mPost_id = post_id;
            mUser_id = user_id;
            mTitle = title;
            mText = text;
            mFilename = Filename;
            mFile = file;
            mLikes = likes;
            mDislikes = dislikes;
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
                "CREATE TABLE userTable (user_id SERIAL, username VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, sex_orient VARCHAR(50) NOT NULL, gender VARCHAR(50), note VARCHAR(50), profile VARCHAR(10485760), valid BOOLEAN, primary key (user_id))");

            //Create POST table
            db.mCreateTablePost = db.mConnection.prepareStatement(
                "CREATE TABLE postTable (post_id SERIAL, user_id int NOT NULL, title VARCHAR(50) NOT NULL, text VARCHAR(500) NOT NULL, access_time VARCHAR(500) NOT NULL, Filename VARCHAR(500), file VARCHAR(10485760), primary key (post_id), foreign key (user_id) references userTable)");

            //Create COMMENT table
            db.mCreateTableComment = db.mConnection.prepareStatement(
                "CREATE TABLE commentTable (comment_id SERIAL, user_id int NOT NULL, post_id int NOT NULL, comment_val VARCHAR(500) NOT NULL, Filename VARCHAR(500), file VARCHAR(10485760), primary key (comment_id), foreign key (user_id) references userTable, foreign key (post_id) references postTable)");

            //Create LIKE table
            db.mCreateTableLike = db.mConnection.prepareStatement(
                "CREATE TABLE likeTable (user_id int NOT NULL, post_id int NOT NULL , foreign key (user_id) references userTable, foreign key (post_id) references postTable, CONSTRAINT user_like UNIQUE(user_id,post_id))");

            //Create DISLIKE table
            db.mCreateTableDislike = db.mConnection.prepareStatement(
                "CREATE TABLE dislikeTable (user_id int NOT NULL, post_id int NOT NULL, foreign key (user_id) references userTable, foreign key (post_id) references postTable, CONSTRAINT user_dislike UNIQUE(user_id,post_id))");
            
            db.mDropTableUser = db.mConnection.prepareStatement("DROP TABLE userTable");
            db.mDropTablePost = db.mConnection.prepareStatement("DROP TABLE postTable");
            db.mDropTableComment = db.mConnection.prepareStatement("DROP TABLE commentTable");
            db.mDropTableLike = db.mConnection.prepareStatement("DROP TABLE likeTable");
            db.mDropTableDislike = db.mConnection.prepareStatement("DROP TABLE dislikeTable");

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT * FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET subject = ?, message = ?,  likes = ? WHERE id = ?");
            db.mGetColNames = db.mConnection.prepareStatement("SELECT * FROM tblData");

            db.mSelectAllUser = db.mConnection.prepareStatement("SELECT * FROM userTable");
            db.mSelectAllPost = db.mConnection.prepareStatement("SELECT * FROM postTable");
            db.mSelectAllComment = db.mConnection.prepareStatement("SELECT * FROM commentTable");
            db.mSelectAllCommentPost = db.mConnection.prepareStatement("SELECT * FROM commentTable WHERE post_id =?");

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
            db.mInsertUser = db.mConnection.prepareStatement("INSERT INTO userTable VALUES (default, ?, ?, ?, ?, ?, ?, TRUE)");
            //Insert Post
            db.mInsertPost = db.mConnection.prepareStatement("INSERT INTO postTable VALUES (default, ?, ?, ?, ?, ?, ?)");
            //Comment Post
            db.mInsertComment = db.mConnection.prepareStatement("INSERT INTO commentTable VALUES (default, ?, ?, ?, ?, ?)");
            db.mGetUserId = db.mConnection.prepareStatement("SELECT user_id from userTable WHERE email = ?");
            db.mGetUserProfile= db.mConnection.prepareStatement("SELECT profile from userTable WHERE email = ?");
            db.mGetUserIdComment = db.mConnection.prepareStatement("SELECT user_id from commentTable where comment_id = ?");
            //Edit functions
            db.mEditUserUsername = db.mConnection.prepareStatement("UPDATE userTable SET username =? WHERE user_id =?");
            //db.mEditUserName = db.mConnection.prepareStatement("UPDATE userTable SET name =? WHERE user_id =?");
            db.mEditUserEmail = db.mConnection.prepareStatement("UPDATE userTable SET email =? WHERE user_id =?");
            db.mEditUserGender = db.mConnection.prepareStatement("UPDATE userTable SET gender =? WHERE user_id =?");
            db.mEditUserSexOrient = db.mConnection.prepareStatement("UPDATE userTable SET sex_orient =? WHERE user_id =?");
            db.mEditUserNote = db.mConnection.prepareStatement("UPDATE userTable SET note =? WHERE user_id =?");
            db.mInvalidateUser = db.mConnection.prepareStatement("UPDATE userTable SET valid =FALSE WHERE user_id =?");
            db.mValidateUser = db.mConnection.prepareStatement("UPDATE userTable SET valid =TRUE WHERE user_id =?");
            db.mUpdateComment = db.mConnection.prepareStatement("Update commentTable SET comment_val=? WHERE comment_id =?");

            db.mEditPostUser = db.mConnection.prepareStatement("UPDATE postTable SET user_id =? WHERE post_id =?");
            db.mEditPostTitle = db.mConnection.prepareStatement("UPDATE postTable SET title =? WHERE post_id =?");
            db.mEditPostText = db.mConnection.prepareStatement("UPDATE postTable SET text =? WHERE post_id =?");
            db.mEditPostFile = db.mConnection.prepareStatement("UPDATE postTable SET file =? WHERE post_id =?");
            db.mEditPostFilename = db.mConnection.prepareStatement("UPDATE postTable SET Filename = ?, File = ? WHERE post_id =?");
            db.mEditAccessTime = db.mConnection.prepareStatement("UPDATE postTable SET access_time =? WHERE post_id =? ");
            db.mEditCommentComment = db.mConnection.prepareStatement("UPDATE commentTable SET comment_val =? WHERE comment_id =?");
            db.mEditCommentFilename = db.mConnection.prepareStatement("UPDATE commentTable SET Filename =?, File = ? WHERE comment_id =?");
            db.mEditCommentFile = db.mConnection.prepareStatement("UPDATE commentTable SET file =? WHERE comment_id =?");

            // check if user already liked
            db.mCheckLike = db.mConnection.prepareStatement("SELECT COUNT(*) as checkLikes FROM likeTable WHERE post_id = ? AND user_id = ?");
            // check if user already disliked
            db.mCheckDislike = db.mConnection.prepareStatement("SELECT COUNT(*) as checkDislikes FROM dislikeTable WHERE post_Id = ? AND user_id = ?");
            //Insert Like
            db.mInsertLike = db.mConnection.prepareStatement("INSERT INTO likeTable VALUES ( ?, ? )");
            //Insert Dislike
            db.mInsertDislike = db.mConnection.prepareStatement("INSERT INTO dislikeTable VALUES ( ?, ? )");
            //Remove Like
            db.mRemoveLike = db.mConnection.prepareStatement("DELETE FROM likeTable WHERE user_id = ? AND post_id = ?");
            //Remove Dislike
            db.mRemoveDislike = db.mConnection.prepareStatement("DELETE FROM dislikeTable WHERE user_id = ? AND post_id = ?");
            //Find Like
            db.mFindLike = db.mConnection.prepareStatement("SELECT COUNT(user_id) as Likes FROM likeTable WHERE post_id = ?");
            //Find Dislike
            db.mFindDislike = db.mConnection.prepareStatement("SELECT COUNT(user_id) as Dislikes FROM dislikeTable WHERE post_id = ?");            

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

    int insertUser(String username, String email, String sex_orient, String gender, String note, String profile) {
        int count = 0;
        ArrayList <UserRowData> listOfUsers = selectAllUser();
        if (listOfUsers.size() == 0){
            try {
                mInsertUser.setString(1, username);
                mInsertUser.setString(2, email);
                mInsertUser.setString(3, sex_orient);
                mInsertUser.setString(4, gender);
                mInsertUser.setString(5, note);
                mInsertUser.setString(6, profile);
                count += mInsertUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        }else{
            for (UserRowData users:listOfUsers){
                if (email.equals(users.mEmail) & (users.mValid == false)){
                    System.out.println("Email is invalid. Cannot sign up for The Buzz");
                    return count+1;
                }
            }
            try {
                mInsertUser.setString(1, username);
                mInsertUser.setString(2, email);
                mInsertUser.setString(3, sex_orient);
                mInsertUser.setString(4, gender);
                mInsertUser.setString(5, note);
                mInsertUser.setString(6, profile);
                count += mInsertUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        }
        
    }

    int insertPost(int user_id, String title, String text, String Filename, String file) {
        int count = 0;
        try {
            mInsertPost.setInt(1, user_id);
            mInsertPost.setString(2, title);
            mInsertPost.setString(3, text);
            mInsertPost.setString(4, new Date().toString());
            mInsertPost.setString(5, Filename);
            mInsertPost.setString(6 , file);
            count += mInsertPost.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertComment(int user_id, int post_id, String comment_val, String Filename, String file) {
        int count = 0;
        try {
            mInsertComment.setInt(1, user_id);
            mInsertComment.setInt(2, post_id);
            mInsertComment.setString(3, comment_val);
            mInsertComment.setString(4, Filename);
            mInsertComment.setString(5, file);
            updateAccessTime(post_id);
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
                res.add(new UserRowData(rs.getInt("user_id"), rs.getString("username"),rs.getString("email"),rs.getString("sex_orient"),rs.getString("gender"),rs.getString("note"), rs.getString("profile"), rs.getBoolean("valid")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<PostLikesData> selectAllPostLikes(){
        ArrayList<PostRowData> res = selectAllPost();
        ArrayList<PostLikesData> PostLikes = new ArrayList<>();
        try{
            for(int i = 0; i < res.size(); i++){
                PostRowData cur = res.get(i);
                ResultSet likes = findLike(cur.mPost_id);
                ResultSet dislikes = findDislike(cur.mPost_id);
                int numLikes = 0;
                int numDislikes = 0;
                while(likes.next()){
                    numLikes = likes.getInt("Likes");
                }
                while(dislikes.next()){
                    numDislikes = dislikes.getInt("Dislikes");
                }
                PostLikesData newData = new PostLikesData(cur.mPost_id,cur.mUser_id,cur.mTitle,cur.mText, cur.mFilename, cur.mFile, numLikes, numDislikes);
                PostLikes.add(newData);
            }
         }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return PostLikes;
    }

    ArrayList<PostRowData> selectAllPost() {
        ArrayList<PostRowData> res = new ArrayList<PostRowData>();
        try {
            ResultSet rs = mSelectAllPost.executeQuery();
            while (rs.next()) {
                res.add(new PostRowData(rs.getInt("post_id"), rs.getInt("user_id"), rs.getString("title"), rs.getString("text"), rs.getString("access_time"), rs.getString("Filename"), rs.getString("file")));
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
                res.add(new CommentRowData(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("post_id"), rs.getString("comment_val"), rs.getString("Filename"), rs.getString("file")));
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
                res.add(new CommentRowData(rs.getInt("comment_id"), rs.getInt("post_id"), rs.getInt("user_id"), rs.getString("comment_val"), rs.getString("Filename"), rs.getString("file")));
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
                res = new UserRowData(rs.getInt("user_id"), rs.getString("username"),rs.getString("email"),rs.getString("sex_orient"),rs.getString("gender"),rs.getString("note"), rs.getString("profile"), rs.getBoolean("valid"));
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
                res = new PostRowData(rs.getInt("post_id"), rs.getInt("user_id"), rs.getString("title"), rs.getString("text"), rs.getString("access_time"), rs.getString("Filename"), rs.getString("file"));
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
                res = new CommentRowData(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("post_id"), rs.getString("comment_val"), rs.getString("Filename"), rs.getString("file"));
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
    int getUserIdComment(int comment_id){
        int user_id = 0;
        try{
            mGetUserIdComment.setInt(1, comment_id);
            // user_id = mGetUserId.executeUpdate();
            ResultSet rs = mGetUserIdComment.executeQuery();
            while(rs.next()){
                user_id = rs.getInt("USER_ID");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user_id; 
    }

    String getUserProfile(String email){
        String profile = "";
        try{
            mGetUserProfile.setString(1, email);
            // user_id = mGetUserId.executeUpdate();
            ResultSet rs = mGetUserId.executeQuery();
            while(rs.next()){
                profile = rs.getString("profile");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return profile;
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

    /**
     * Update the time for a row in the database
     * 
     * @param id The id of the Post to update
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateAccessTime(int id) { 
        int res = -1;
        
        try {
            Date newTime = new Date();
            System.out.println(newTime.toString());
            mEditAccessTime.setString(1, newTime.toString());
            mEditAccessTime.setInt(2, id);
            res = mEditAccessTime.executeUpdate();
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
            mDropTableDislike.execute();
            mDropTableLike.execute();
            mDropTableComment.execute();
            mDropTablePost.execute();
            mDropTableUser.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Invalidate the User
     */


    int invalidateUser(int user_id) { 
        int res = -1;
        try {
            mInvalidateUser.setInt(1, user_id);
            res = mInvalidateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int validateUser(int user_id) { 
        int res = -1;
        try {
            mValidateUser.setInt(1, user_id);
            res = mValidateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
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
            updateAccessTime(post_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editPostFilename(int post_id, String newFilename, String File) { 
        int res = -1;
        try {
            mEditPostFilename.setString(1, newFilename);
            mEditPostFile.setString(2, File);
            mEditPostFilename.setInt(3, post_id);
            res = mEditPostFilename.executeUpdate();
            updateAccessTime(post_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editPostFile(int post_id, String newFile) { 
        int res = -1;
        try {
            mEditPostFile.setString(1, newFile);
            mEditPostFile.setInt(2, post_id);
            res = mEditPostFile.executeUpdate();
            updateAccessTime(post_id);

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

    int editCommentFilename(int comment_id, String newFilename, String File) { 
        int res = -1;
        try {
            mEditCommentFilename.setString(1, newFilename);
            mEditCommentFilename.setString(2, File);
            mEditCommentFilename.setInt(3, comment_id);
            res = mEditCommentFilename.executeUpdate();
            updateAccessTime(comment_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int editCommentFile(int comment_id, String newFile) { 
        int res = -1;
        try {
            mEditCommentFile.setString(1, newFile);
            mEditCommentFile.setInt(2, comment_id);
            res = mEditCommentFile.executeUpdate();
            updateAccessTime(comment_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int updateComment(int comment_id, String comment_val) { 
        int res = -1;
        try {
            mUpdateComment.setString(1, comment_val);
            mUpdateComment.setInt(2, comment_id);
            res = mUpdateComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int insertLike(int user_id, int post_id) {
        int count = 0;
        try {
            mInsertLike.setInt(1, user_id);
            mInsertLike.setInt(2, post_id);
            count += mInsertLike.executeUpdate();
            updateAccessTime(post_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertDislike(int user_id, int post_id) {
        int count = 0;
        try {
            mInsertDislike.setInt(1, user_id);
            mInsertDislike.setInt(2, post_id);
            count += mInsertDislike.executeUpdate();
            updateAccessTime(post_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int removeLike(int user_id, int post_id) {
        int res = -1;
        try {
            mRemoveLike.setInt(1, user_id);
            mRemoveLike.setInt(2, post_id);
            res = mRemoveLike.executeUpdate();
            updateAccessTime(post_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int removeDislike(int user_id, int post_id) {
        int res = -1;
        try {
            mRemoveDislike.setInt(1, user_id);
            mRemoveDislike.setInt(2, post_id);
            res = mRemoveDislike.executeUpdate();
            updateAccessTime(post_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int checkLike(int user_id, int post_id){
        int res = -1;
        try{
            mCheckLike.setInt(1, post_id);
            mCheckLike.setInt(2, user_id);
            ResultSet result = mCheckLike.executeQuery();
            while(result.next()){
                res = result.getInt("checkLikes");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    int checkDislike(int user_id, int post_id){
        int res = -1;
        try{
            mCheckDislike.setInt(1, post_id);
            mCheckDislike.setInt(2, user_id);
            ResultSet result = mCheckDislike.executeQuery();
            while(result.next()){
                res = result.getInt("checkDislikes");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    ResultSet findLike(int post_id) {
        ResultSet res = null;
        try {
            mFindLike.setInt(1, post_id);
            res = mFindLike.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    ResultSet findDislike(int post_id) {
        ResultSet res = null;
        try {
            mFindDislike.setInt(1, post_id);
            res = mFindDislike.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}