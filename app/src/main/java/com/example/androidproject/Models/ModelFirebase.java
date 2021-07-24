package com.example.androidproject.Models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.Display;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {

    final private static String userCollection = "users";
    final private static String postCollection = "posts";
    final private static String commentCollection = "comments";
    final private static FirebaseAuth auth = FirebaseAuth.getInstance();

    private ModelFirebase() {
    }

    public interface GetAllListener<T> {
        public void onComplete(List<T> entities);
    }


    public static void signOutUser() {
        auth.signOut();
    }

    public static void signUpUser(String email, String password, Model.onAuthenticationResult onComplete, Model.onAuthenticationResult onError) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> {
                    onError.execute(email);
                    Log.d("TAG", e.getMessage());
                })
                .addOnSuccessListener(authResult -> {
                    onComplete.execute(email);
                });
    }

    public static void signInUser(String email, String password, Model.onAuthenticationResult onComplete, Model.onAuthenticationResult onError) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> onError.execute(email))
                .addOnSuccessListener(authResult -> onComplete.execute(email));
    }


    public static void getUserByEmail(String email, Model.GetUserWithEmailListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(userCollection)
                .whereEqualTo(User.EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        listener.onComplete(User.fromJson(task.getResult().getDocuments().get(0).getData()));
                    else
                        Log.d("ERROR", "Task is unsuccessful : get email");
                });
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

    public static void deleteUser(User user, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user.setDeleted(true);
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
                        Log.d("TAG2", "in if");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(Post.fromJson(document.getData()));
                            Log.d("TAG2", "post: " + list.get(list.size() - 1).getPostTitle());
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }

    public static void getAllPostFromUser(User user, Long since, GetAllListener<Post> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(postCollection)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<Post>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get(Post.USERNAME).equals(user.getUsername()))
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

    public static void deletePost(Post post, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        post.setDeleted(true);
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

    public static void getCommentsFromUser(User user, Long since, GetAllListener<Comment> listener){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .whereGreaterThanOrEqualTo(Comment.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Comment> list = new LinkedList<Comment>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get(Comment.COMMENT_USERNAME).equals(user.getUsername()))
                                list.add(Comment.fromJson(document.getData()));
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }

    public static void getCommentsFromPost(Post post, Long since, GetAllListener<Comment> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .whereGreaterThanOrEqualTo(Comment.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Comment> list = new LinkedList<Comment>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get(Comment.POST_ID).equals(post.getPostID()))
                                list.add(Comment.fromJson(document.getData()));
                        }
                    } else
                        Log.d("ERROR", "Task is unsuccessful");
                    listener.onComplete(list);
                });
    }

    public static void getCommentsFromParentComment(Comment parentComment, Long since, GetAllListener<Comment> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(commentCollection)
                .whereGreaterThanOrEqualTo(Comment.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Comment> list = new LinkedList<Comment>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getData().get(Comment.POST_ID).equals(parentComment.getPostID()))
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

    public static void deleteComment(Comment comment, Model.OnCompleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        comment.setDeleted(true);
        db.collection(userCollection).document(comment.getCommentID())
                .set(comment.toJson())
                .addOnSuccessListener(e -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public static void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("photos").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(e -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> listener.onComplete(uri.toString())));
    }
}
