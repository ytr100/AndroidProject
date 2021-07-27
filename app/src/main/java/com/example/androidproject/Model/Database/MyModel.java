package com.example.androidproject.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyModel {


    public static final MyModel instance = new MyModel();
    private static final String NEXT_COMMENT_ID = "8";
    public static String CURRENT_USER = "C";
    private static String NEXT_POST_ID = "4";
    private final Map<String, MutableLiveData<List<Comment>>> commentsOfPost;
    private final Map<String, MutableLiveData<List<Post>>> postsOfUsers;
    private final Map<String, MutableLiveData<List<Comment>>> commentsOfUsers;
    private final Map<String, MutableLiveData<List<Comment>>> commentsOfComments;
    private Map<Comment, List<Comment>> nestedComments;
    private Map<Post, List<Comment>> directComments;
    private Map<String, Comment> translationComments;
    private Map<String, Post> translationPosts;
    private Map<String, User> users;
    private MutableLiveData<List<Post>> allPosts;

    private MyModel() {
        initUsers();
        initPosts();
        initComments();
        initDirect();
        initNested();
        postsOfUsers = new HashMap<>();
        commentsOfPost = new HashMap<>();
        commentsOfUsers = new HashMap<>();
        commentsOfComments = new HashMap<>();
    }

    public void insertPost(Post p, String username) {
        p.setPostUsername(username);
        p.setPostID(NEXT_POST_ID);
        incrementPostID();
        translationPosts.put(p.getPostID(), p);
        List<Post> newlist = allPosts.getValue();
        newlist.add(p);
        allPosts.setValue(newlist);
        directComments.put(p, new ArrayList<>());
        getPostsOfUser(username);
        newlist = postsOfUsers.get(username).getValue();
        newlist.add(p);
        postsOfUsers.get(username).setValue(newlist);
    }

    public void editPost(Post p) {
        translationPosts.put(p.getPostID(), p);
        List<Post> newlist = allPosts.getValue();
        newlist.remove(p);
        newlist.add(p);
        allPosts.setValue(newlist);
        getPostsOfUser(p.getPostUsername());
        newlist = postsOfUsers.get(p.getPostUsername()).getValue();
        newlist.remove(p);
        newlist.add(p);
        postsOfUsers.get(p.getPostUsername()).setValue(newlist);

    }
    public void deletePost(Post p) {
        translationPosts.remove(p.getPostUsername());
        List<Post> newlist = allPosts.getValue();
        newlist.remove(p);
        allPosts.setValue(newlist);
        getPostsOfUser(p.getPostUsername());
        newlist = postsOfUsers.get(p.getPostUsername()).getValue();
        newlist.remove(p);
        postsOfUsers.get(p.getPostUsername()).setValue(newlist);

    }

    public void insertComment(Comment c, String username) {

    }

    public Post getByID(String postID) {
        return translationPosts.get(postID);
    }

    private void incrementPostID() {
        int x = Integer.parseInt(NEXT_POST_ID) + 1;
        NEXT_POST_ID = Integer.toString(x);
    }

    private void incrementCommentID() {
        int x = Integer.parseInt(NEXT_COMMENT_ID) + 1;
        NEXT_POST_ID = Integer.toString(x);
    }

    private void initUsers() {
        users = new HashMap<>();
        users.put("A", new User("A", "A@mail.com"));
        users.put("B", new User("B", "B@mail.com"));
        users.put("C", new User("C", "C@mail.com"));
    }

    private void initPosts() {
        translationPosts = new HashMap<>();
        Post p1, p2, p3;
        p1 = new Post("Hello From A", "I am A", "");
        p1.setPostID("1");
        p1.setPostUsername("A");
        translationPosts.put("1", p1);
        p2 = new Post("Hello From B", "I am B", "");
        p2.setPostID("2");
        p2.setPostUsername("B");
        translationPosts.put("2", p2);
        p3 = new Post("Hello From C", "I am C", "");
        p3.setPostID("3");
        p3.setPostUsername("C");
        translationPosts.put("3", p3);
    }

    private void initComments() {
        translationComments = new HashMap<>();
        Comment c1, c2, c3, c4, c5, c6, c7,c8;
        c1 = new Comment("Comment from B", "I am B (comment)", "");
        c1.setParentCommentID(null);
        c1.setPostID("1");
        c1.setCommentUsername("B");
        c1.setCommentID("1");
        translationComments.put("1", c1);
        c2 = new Comment("Comment from A", "I am A (comment)", "");
        c2.setParentCommentID(null);
        c2.setPostID("1");
        c2.setCommentUsername("A");
        c2.setCommentID("2");
        translationComments.put("2", c2);
        c3 = new Comment("Comment from A", "I am A (nested comment)", "");
        c3.setParentCommentID("1");
        c3.setPostID("1");
        c3.setCommentUsername("A");
        c3.setCommentID("3");
        translationComments.put("3", c3);
        c4 = new Comment("Comment from C", "I am C (nested comment)", "");
        c4.setParentCommentID("1");
        c4.setPostID("1");
        c4.setCommentUsername("C");
        c4.setCommentID("4");
        translationComments.put("4", c4);
        c5 = new Comment("Comment from A", "I am A (comment)", "");
        c5.setParentCommentID(null);
        c5.setPostID("3");
        c5.setCommentUsername("A");
        c5.setCommentID("5");
        translationComments.put("5", c5);
        c6 = new Comment("Comment from B", "I am B (nested comment)", "");
        c6.setParentCommentID("5");
        c6.setPostID("3");
        c6.setCommentUsername("B");
        c6.setCommentID("6");
        translationComments.put("6", c6);
        c7 = new Comment("Comment from C", "I am C (nested comment)", "");
        c7.setParentCommentID("5");
        c7.setPostID("3");
        c7.setCommentUsername("C");
        c7.setCommentID("7");
        translationComments.put("7", c7);
        c8  = new Comment("Comment from C", "I am C (comment)", "");
        c8.setParentCommentID(null);
        c8.setPostID("2");
        c8.setCommentUsername("C");
        c8.setCommentID("8");
        translationComments.put("8", c8);
    }

    private void initDirect() {
        directComments = new HashMap<>();
        for (Post p : translationPosts.values()) {
            List<Comment> direct = new ArrayList<>();
            for (Comment c : translationComments.values())
                if (c.getParentCommentID() == null)
                    if (c.getPostID().equals(p.getPostID()))
                        direct.add(c);
            Collections.sort(direct, (o1, o2) -> o1.getCommentID().compareTo(o2.getCommentID()));
            directComments.put(p, direct);
        }
    }

    private void initNested() {
        nestedComments = new HashMap<>();

        for (List<Comment> direct : directComments.values()) {
            for (Comment parent : direct) {
                List<Comment> nestList = new ArrayList<>();
                for (Comment nested : translationComments.values())
                    if (nested.getParentCommentID() != null)
                        if (nested.getParentCommentID().equals(parent.getCommentID()))
                            nestList.add(nested);
                Collections.sort(nestList, (o1, o2) -> o1.getCommentID().compareTo(o2.getCommentID()));
                nestedComments.put(parent, nestList);
            }
        }
    }

    public LiveData<List<Comment>> getCommentsOfPost(String postID) {
        if (!commentsOfPost.containsKey(postID))
            commentsOfPost.put(postID, new MutableLiveData<>(directComments.get(translationPosts.get(postID))));
        return commentsOfPost.get(postID);
    }

    public LiveData<List<Comment>> getCommentsOfComment(String parentCommentID) {
        if (!commentsOfComments.containsKey(parentCommentID))
            commentsOfComments.put(parentCommentID, new MutableLiveData<>(nestedComments.get(translationComments.get(parentCommentID))));
        return commentsOfComments.get(parentCommentID);
    }

    public LiveData<List<Comment>> getCommentsOfUser(String username) {
        if (!commentsOfUsers.containsKey(username)) {
            List<Comment> commentsOfUser = new ArrayList<>();
            for (Comment c : translationComments.values()) {
                if (c.getUsername().equals(username))
                    commentsOfUser.add(c);
            }
            commentsOfUsers.put(username, new MutableLiveData<>(commentsOfUser));
        }
        return commentsOfUsers.get(username);
    }

    public LiveData<List<Post>> getAllPosts() {
        if (allPosts == null) {
            List<Post> posts = new ArrayList<>(translationPosts.values());
            Collections.sort(posts, (o1, o2) -> o1.getPostID().compareTo(o2.getPostID()));
            allPosts = new MutableLiveData<>(posts);
        }
        return allPosts;
    }

    public LiveData<List<Post>> getPostsOfUser(String username) {
        if (!postsOfUsers.containsKey(username)) {
            List<Post> posts = new ArrayList<>();
            for (Post p : translationPosts.values())
                if (p.getPostUsername().equals(username))
                    posts.add(p);
            Collections.sort(posts, (o1, o2) -> o1.getPostID().compareTo(o2.getPostID()));
            postsOfUsers.put(username, new MutableLiveData<>(posts));
        }

        return postsOfUsers.get(username);
    }

    public User getByUserName(String username) {
        return users.get(username);
    }
}
