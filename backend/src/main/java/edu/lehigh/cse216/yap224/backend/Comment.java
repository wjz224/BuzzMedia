package edu.lehigh.cse216.yap224.backend;

public class Comment {
     /***
     * The Comment's Id which is a primary key for comment
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mCommentId;
    /***
     *  /***
     * The User's Id which is a foreign key for comment from the User Tabel
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mUserId;
    /***
     * The Post's Id which is a foreign key for comment from the Post Table
     * We never want to change a foreign key or a primary key because it is a unique identifier for their elements which is why this is kept final.
     */
    public final int mPostId;
    /***
     * The content for this comment
     */
    public final String mComment;
    /***
     * Create a comment by using the parameters passed in.
     * @param commentId the comment's id unique to the comment
     * @param userId the userid for the user who made the comment
     * @param postId the postid for the post where the comment was made
     * @param comment the content of the comment
     */
    Comment(int commentId, int userId, int postId, String comment){
        this.mCommentId = commentId;
        this.mUserId = userId;
        this.mPostId = postId;
        this.mComment = comment;
    }
     /***
      * Copy constructor to create one Comment from another
      * @param comment the comment that we are copying over.
      */
    Comment(Comment comment){
        this.mCommentId = comment.mCommentId;
        this.mUserId = comment.mUserId;
        this.mPostId = comment.mPostId;
        this.mComment = comment.mComment;
    }
}
