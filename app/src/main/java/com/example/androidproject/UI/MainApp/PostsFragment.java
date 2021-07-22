package com.example.androidproject.UI.MainApp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidproject.Models.Post;
import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;
//TODO: livedata
public class PostsFragment extends Fragment {
    PostsViewModel postsViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_posts, container, false);

        RecyclerView postsList = root.findViewById(R.id.main_posts_list);
        postsList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        PostsListAdapter adapter = new PostsListAdapter();
        adapter.setOnListItemClickListener(position ->
                Snackbar.make(root,"row " + position + " was clicked!",2000).show());
        postsList.setAdapter(adapter);
        return root;
    }

    interface OnItemClickListener{
        void onClick (int position);
    }
    //TODO: change location and maybe template it
    class PostsListAdapter extends RecyclerView.Adapter<PostViewHolder>{
        private OnItemClickListener listener;
        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row,parent,false);
            return new PostViewHolder(view,listener);
        }
        public void setOnListItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
            Post p = postsViewModel.getData().get(position);
            Log.d("TAG","onBindViewHolder "+position);
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            return postsViewModel.getData().size();
        }
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView title;
        TextView content;
        TextView likes;
        TextView comments;
        public PostViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            username = itemView.findViewById(R.id.row_username);
            title = itemView.findViewById(R.id.row_title);
            content = itemView.findViewById(R.id.row_content);
            likes = itemView.findViewById(R.id.row_likes);
            comments = itemView.findViewById(R.id.row_comments);
            itemView.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        listener.onClick(position);
                }
            });
        }
        public void bind (Post post){
            username.setText(post.getPostUsername());
            title.setText(post.getPostTitle());
            content.setText(post.getPostContent());
            int numLikes = post.getPostLikes();
            String l =  numLikes + (numLikes == 1 ? " Like" : " Likes");
            likes.setText(l);
            String tmp = "placeholder";
            comments.setText(tmp);
        }
    }

}