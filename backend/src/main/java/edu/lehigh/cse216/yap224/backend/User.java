package edu.lehigh.cse216.yap224.backend;

public class User {
    /***
     * The User's Email, private since first names should not change
     */
    private String mEmail;
    /***
     * The User's first name, private email's do not change.
     */
    private String mName;
    /***
     *  The User's gender, public since this can change
     */
    public  String mGender;
    /**
     * The User's Sexual Oritentation, public since this can change
     */
    public  String mSexualOrientation;
    /***
     * The User's, family name, private since this last names should not change
     */
    private String mFamilyName;
    /***
     * The User's note, public since the note can change.
     */
    public  String mNote;

     /**
     * Create a User by using the parameters passed in.
     */
    User(String email, String name, String gender, String sexualOrientation, String familyName, String note ){
        this.mEmail = email;
        this.mName = name;
        this.mGender = gender;
        this.mSexualOrientation= sexualOrientation;
        this.mFamilyName = familyName;
        this.mNote = note;
    }
    /**
     * Copy constructor to create one User from another
     */
    User(User user){
        this.mEmail = user.mEmail;
        this.mName = user.mName;
        this.mGender = user.mGender;
        this.mSexualOrientation= user.mSexualOrientation;
        this.mFamilyName = user.mFamilyName;
        this.mNote = user.mNote;
    }
  
}
