package edu.lehigh.cse216.yap224.backend;

public class Dislike {
     /***
     * The User's Id which is a foreign key for dislike from the User table
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mUserId;
    /***
     * The Post's Id which is a foreign key for dislike from the Post table.
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mPostId;
    
    /***
     * Create a like by using the parameters passed in
     * @param userId The unique UserId that disliked the comment
     * @param postId The unique PostId that is disliked.
     */
    Dislike(int userId, int postId){
        this.mUserId = userId;
        this.mPostId = postId;
    }
    /***
     * Copy a constructor to create one dislike from another
     * @param dislike the like that we are copying from
     */
    Dislike(Like dislike){
        this.mUserId = dislike.mUserId;
        this.mPostId = dislike.mPostId;
    }

}
