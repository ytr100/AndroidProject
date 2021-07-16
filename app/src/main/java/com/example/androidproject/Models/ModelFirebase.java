package com.example.androidproject.Models;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {

    final private static String userCollection = "users";
    final private static String postCollection = "posts";
    final private static String commentCollection = "comments";

    private ModelFirebase() {
    }

    public interface GetAllListener<T> {
        public void onComplete(List<T> entities);
    }

    public static void getAllUsers(Long since, GetAllListener<User> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(userCollection)
                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<User> list = new LinkedList<User>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(User.fromJson(document.getData()));
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }


    public static void saveUser(User user, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(userCollection).document(user.getUsername())
                .set(user.toJson())
                .addOnSuccessListener(e -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }


    public static void getAllPosts(Long since, GetAllListener<Post> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(postCollection)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<Post>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(Post.fromJson(document.getData()));
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }


    public static void savePost(Post post, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(postCollection).document(post.getPostID())
                .set(post.toJson())
                .addOnSuccessListener(e -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public static void getAllComments(Long since, GetAllListener<Comment> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .whereGreaterThanOrEqualTo(Comment.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Comment> list = new LinkedList<Comment>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(Comment.fromJson(document.getData()));
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }


    public static void saveComment(Comment comment, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection).document(comment.getCommentID())
                .set(comment.toJson())
                .addOnSuccessListener(e -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

}
