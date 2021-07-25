package com.example.androidproject.UI.MainApp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
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
        adapter.setOnListItemClickListener(position ->{
                    PostsFragmentDirections.ActionPostsFragmentToCommentsFragment action = PostsFragmentDirections.actionPostsFragmentToCommentsFragment("" + position);
                    Snackbar.make(root, "post " + position + " was clicked!", 2000).show();
                    Navigation.findNavController(root).navigate(action);
                }
                );
        postsList.setAdapter(adapter);
        setHasOptionsMenu(true);
        return root;
    }




    class PostsListAdapter extends RecyclerView.Adapter<RowViewHolder> {
        private OnItemClickListener listener;

        @NonNull
        @Override
        public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row, parent, false);
            return new RowViewHolder(view, listener);
        }

        public void setOnListItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
            Post p = postsViewModel.getData().get(position);
            Log.d("TAG", "onBindViewHolder " + position);
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            return postsViewModel.getData().size();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_add:
                //Snackbar.make(requireView(),"PostsFragment",2000).show();
                item.setVisible(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}