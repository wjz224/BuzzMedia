package edu.lehigh.cse216.yap224.backend;

public class Post {
    /***
     * The Post's Id which is a primary key for post
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mPostId;
    /***
     * The User's Id which is a foreign key for post from the User table
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mUserId;
    /***
     * The title for this Post
     */
    public String mTitle;
    /***
     * The content for this post
     */
    public String mText;
    /***
     * Create a post by using the parameters passed in.
     * @param postId The postId unique to the post
     * @param userId The userId unique to the user making the post
     * @param title  The title of the post
     * @param text  the content of the post
     */
    Post(int postId, int userId, String title, String text){
        this.mPostId = postId;
        this.mUserId = userId;
        this.mTitle = title;
        this.mText = text;
    }
    /***
     * Copy constructor to create one Post from another
     */
    Post(Post post){
        this.mPostId = post.mPostId;
        this.mUserId = post.mUserId;
        this.mTitle = post.mTitle;
        this.mText = post.mText;
    }



}
