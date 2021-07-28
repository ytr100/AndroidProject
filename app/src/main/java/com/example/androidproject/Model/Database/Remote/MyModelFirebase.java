package com.example.androidproject.Model.Database.Remote;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Message;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.Model.Listeners.OnCommentsCompleteListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;
import com.example.androidproject.Model.Listeners.OnPostsCompleteListener;
import com.example.androidproject.Model.Listeners.OnUsersCompleteListener;
import com.example.androidproject.Model.Listeners.UploadImageListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyModelFirebase {
    final static String postCollection = "posts";
    final static String commentCollection = "comments";
    final static String userCollection = "users";

    public static void insertPost(Message m, String username, OnDBActionComplete actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Post p = new Post(m, username);
// Add a new document with a generated ID
        db.collection(postCollection)
                .add(p.toJsonWithoutID())
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    p.setPostID(documentReference.getId());
                    actionComplete.onComplete();
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }

    public static void editPost(Post p, OnDBActionComplete actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Updates the given document
        db.collection(postCollection)
                .document(p.getPostID()).update(p.toJsonWithoutID())
                .addOnSuccessListener(voidValue -> actionComplete.onComplete())
                .addOnFailureListener(e -> Log.w("TAG", "Error updating document", e));
    }
    public static void deletePost(Post p , OnDBActionComplete actionComplete){
        p.setDeleted(true);
        editPost(p,actionComplete);
    }

    public static void insertComment(Message m, String username, String postID, String parentCommentID, OnDBActionComplete actionComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Comment c = new Comment(m, username,postID,parentCommentID);
// Add a new document with a generated ID
        db.collection(commentCollection)
                .add(c.toJsonWithoutID())
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    c.setCommentID(documentReference.getId());
                    actionComplete.onComplete();
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }
    public static void editComment(Comment c, OnDBActionComplete actionComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Updates the given document
        db.collection(commentCollection)
                .document(c.getPostID()).update(c.toJsonWithoutID())
                .addOnSuccessListener(voidValue -> actionComplete.onComplete())
                .addOnFailureListener(e -> Log.w("TAG", "Error updating document", e));
    }
    public static void deleteComment(Comment c, OnDBActionComplete actionComplete){
        c.setDeleted(true);
        editComment(c,actionComplete);
    }

    public static void getAllPosts(OnPostsCompleteListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(postCollection)
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> postList = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            postList.add(Post.fromJson(document.getData()));
                        }
                    } else {
                        Log.w("TAG", "Error getting documents. (getAllPosts)", task.getException());
                    }
                    actionComplete.onComplete(postList);
                });
    }
    public static void getAllComments(OnCommentsCompleteListener actionComplete){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .get()
                .addOnCompleteListener(task -> {
                    List<Comment> commentList = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            commentList.add(Comment.fromJson(document.getData()));
                        }
                    } else {
                        Log.w("TAG", "Error getting documents. (getAllPosts)", task.getException());
                    }
                    actionComplete.onComplete(commentList);
                });
    }

    public static void insertUser(String username, String email, String password,OnDBActionComplete actionComplete){
        //TODO: authentication
    }
    public static void editUser(User user, String newPhoto, OnDBActionComplete actionComplete){
        //TODO: photo
    }
    public static void getAllUsers(OnUsersCompleteListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(userCollection)
                .get()
                .addOnCompleteListener(task -> {
                    List<User> userList = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            userList.add(User.fromJson(document.getData()));
                        }
                    } else {
                        Log.w("TAG", "Error getting documents. (getAllPosts)", task.getException());
                    }
                    actionComplete.onComplete(userList);
                });
    }
    public static void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnSuccessListener(uri ->
                        listener.onComplete(uri.toString())));
    }



}
