//package com.example.androidproject.Model.Database;
//
//import androidx.lifecycle.LiveData;
//
//import com.example.androidproject.Model.Entity.Comment;
//import com.example.androidproject.Model.Entity.Message;
//import com.example.androidproject.Model.Entity.Post;
//import com.example.androidproject.Model.Entity.User;
//
//import java.util.List;
//
//public interface Database {//every method except LiveData must be called from a background thread
//    void insertPost(Message m, String username);
//
//    void editPost(Post p);
//
//    void deletePost(Post p);
//
//    void insertComment(Message m, String username, String postID, String parentCommentID);
//
//    void editComment(Comment c);
//
//    void deleteComment(Comment c);
//
//    Post getPostByID(String postID);
//
//    User getByUserName(String username);
//
//    Comment getCommentByID (String commentID);
//
//    LiveData<List<Comment>> getCommentsOfPost(String postID);
//
//    LiveData<List<Comment>> getCommentsOfComment(String parentCommentID);
//
//    LiveData<List<Comment>> getCommentsOfUser(String username);
//
//    LiveData<List<Post>> getAllPosts();
//
//    LiveData<List<User>> getAllUsers();
//
//    LiveData<List<Comment>> getAllComments();
//
//
//    LiveData<List<Post>> getPostsOfUser(String username);
//
//
//}
