package edu.lehigh.cse216.yap224.backend;

public class Like {
     /***
     * The User's Id which is a foreign key for like from the User table
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mUserId;
    /***
     * The Post's Id which is a foreign key for like from the Post table.
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mPostId;
    
    /***
     * Create a like by using the parameters passed in
     * @param userId The unique UserId that liked the comment
     * @param postId The unique PostId that is liked.
     */
    Like(int userId, int postId){
        this.mUserId = userId;
        this.mPostId = postId;
    }
    /***
     * Copy a constructor to create one like from another
     * @param like the like that we are copying from
     */
    Like(Like like){
        this.mUserId = like.mUserId;
        this.mPostId = like.mPostId;
    }

}
