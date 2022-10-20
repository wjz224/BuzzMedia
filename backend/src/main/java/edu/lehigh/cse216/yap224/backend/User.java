package edu.lehigh.cse216.yap224.backend;

/***
 *  User holds a row of information for a User.
 */
public class User {
    /***
     * The User's Id which is a primary key but a foreign key for Comments, Likes, Dislikes, and Post tables. 
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mUserId;
    /***
     * The User's Email, private since first names should not change
     */
    private String mEmail;
    /***
     * The User's username, public since the user should be able to change their usernames.
     */
    public String mUserName;
    /***
     *  The User's gender, public since this can change
     */
    public  String mGender;
    /**
     * The User's Sexual Oritentation, public since this can change
     */
    public  String mSexualOrientation;
    /***
     * The User's note, public since the note can change.
     */
    public String mNote;

    /***
     * Create a user from the parameters passed in
     * @param userId The user's id unique to the User
     * @param email The user's email
     * @param userName The user's userName
     * @param gender The user's gender
     * @param sexualOrientation The user's sexualOrientation
     * @param note The user's note
     */
    User(int userId, String email, String userName, String gender, String sexualOrientation, String note){
        this.mUserId = userId;
        this.mEmail = email;
        this.mUserName = userName;
        this.mGender = gender;
        this.mSexualOrientation= sexualOrientation;
        this.mNote = note;
    }
    /***
    * Copy constructor to create one User from another
    * @param user The user data we are copying from
    */
    User(User user){
        this.mUserId = user.mUserId;
        this.mEmail = user.mEmail;
        this.mUserName = user.mUserName;
        this.mGender = user.mGender;
        this.mSexualOrientation= user.mSexualOrientation;
        this.mNote = user.mNote;
    }
  
}
