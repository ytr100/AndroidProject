package com.example.androidproject.Model.Database.Remote;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Message;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.Model.Listeners.GetCommentListener;
import com.example.androidproject.Model.Listeners.GetPostListener;
import com.example.androidproject.Model.Listeners.GetUserListener;
import com.example.androidproject.Model.Listeners.OnAuthenticationResult;
import com.example.androidproject.Model.Listeners.OnCommentsCompleteListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;
import com.example.androidproject.Model.Listeners.OnPostsCompleteListener;
import com.example.androidproject.Model.Listeners.OnUsersCompleteListener;
import com.example.androidproject.Model.Listeners.UploadImageListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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
    final private static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static void insertPost(Message m, String username, GetPostListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Post p = new Post(m, username);
// Add a new document with a generated ID
        db.collection(postCollection)
                .add(p.toJsonWithoutID())
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    p.setPostID(documentReference.getId());
                    actionComplete.onComplete(p);
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

    public static void deletePost(Post p, OnDBActionComplete actionComplete) {
        p.setDeleted(true);
        editPost(p, actionComplete);
    }

    public static void deleteUser(User u, OnDBActionComplete actionComplete) {
        u.setDeleted(true);
        editUser(u, null, actionComplete);
    }

    public static void signOutUser() {
        auth.signOut();
    }

    public static void signUpUser(String email, String password, OnAuthenticationResult onComplete, OnAuthenticationResult onError) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> {
                    onError.execute(email);
                    Log.d("TAG", e.getMessage());
                })
                .addOnSuccessListener(authResult -> onComplete.execute(email));
    }

    public static void signInUser(String email, String password, OnAuthenticationResult onComplete, OnAuthenticationResult onError) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> onError.execute(email))
                .addOnSuccessListener(authResult -> onComplete.execute(email));
    }

    public static void insertComment(Message m, String username, String postID, String parentCommentID, GetCommentListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Comment c = new Comment(m, username, postID, parentCommentID);
// Add a new document with a generated ID
        db.collection(commentCollection)
                .add(c.toJsonWithoutID())
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    c.setCommentID(documentReference.getId());
                    actionComplete.onComplete(c);
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }

    public static void getUserByEmail(String email, GetUserListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(userCollection)
                .whereEqualTo(User.EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        actionComplete.onComplete(User.fromJson(task.getResult().getDocuments().get(0).getData()));
                    else
                        Log.d("ERROR", "Task is unsuccessful : get email");
                });
    }

    public static void editComment(Comment c, OnDBActionComplete actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Updates the given document
        db.collection(commentCollection)
                .document(c.getPostID()).update(c.toJsonWithoutID())
                .addOnSuccessListener(voidValue -> actionComplete.onComplete())
                .addOnFailureListener(e -> Log.w("TAG", "Error updating document", e));
    }

    public static void deleteComment(Comment c, OnDBActionComplete actionComplete) {
        c.setDeleted(true);
        editComment(c, actionComplete);
    }

    public static void getAllPosts(Long since, OnPostsCompleteListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(postCollection)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
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

    public static void getAllComments(Long since, OnCommentsCompleteListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .whereGreaterThanOrEqualTo(Comment.LAST_UPDATED, new Timestamp(since, 0))
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

    public static void insertUser(String username, String email, GetUserListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = new User(username,email);
// Add a new document with a generated ID
        db.collection(userCollection)
                .document(user.getUsername())
                .set(user.toJson())
                .addOnSuccessListener(documentReference -> actionComplete.onComplete(user))
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }

    public static void editUser(User user, String newPhoto, OnDBActionComplete actionComplete) {
        if (newPhoto == null) {//delete
            user.setDeleted(true);
        } else {//edit
            user.setPhoto(newPhoto);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Updates the given document
        db.collection(userCollection)
                .document(user.getUsername()).update(user.toJson())
                .addOnSuccessListener(voidValue -> actionComplete.onComplete())
                .addOnFailureListener(e -> Log.w("TAG", "Error updating document", e));

    }

    public static void getAllUsers(Long since, OnUsersCompleteListener actionComplete) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(userCollection)
                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
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

    public static void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
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
