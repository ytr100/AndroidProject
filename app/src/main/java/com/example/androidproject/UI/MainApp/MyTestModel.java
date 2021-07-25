package com.example.androidproject.UI.MainApp;

import android.util.Log;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTestModel {
    public static final MyTestModel instance = new MyTestModel();
    public Map<Post, List<Comment>> db;
     private MyTestModel(){
         this.db = new HashMap<>();
         for(int i = 0; i<10;i++){
             Post p = new Post(""+i,""+i,""+i);
             p.setPostUsername(""+i);
             List<Comment> data = new ArrayList<>();
             for(int j = i; j<10;j++){
                 Comment c = new Comment(""+j,""+j,"");
                 c.setCommentUsername(""+j);
                 data.add(c);
             }
             db.put(p,data);
         }
     }
}
