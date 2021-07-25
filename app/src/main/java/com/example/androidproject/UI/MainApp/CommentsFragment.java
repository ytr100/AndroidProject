package com.example.androidproject.UI.MainApp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

public class CommentsFragment extends Fragment {

    private CommentsViewModel commentsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        RecyclerView commentsList = root.findViewById(R.id.main_comments_list);
        commentsList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        CommentsListAdapter adapter = new CommentsListAdapter();
        adapter.setOnListItemClickListener(position ->
                Snackbar.make(root, "comment " + position + " was clicked!", 2000).show());
        commentsList.setAdapter(adapter);
        String postID = CommentsFragmentArgs.fromBundle(getArguments()).getPostID();
        //hashcode of ""+x is different from the hashcode of x
        commentsViewModel.getData(new Post(postID,postID,postID));//TODO: transfer to viewmodel
        setHasOptionsMenu(true);
        return root;
    }
    class CommentsListAdapter extends RecyclerView.Adapter<RowViewHolder> {
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

            Comment c = commentsViewModel.getCurrent().get(position);
            Log.d("TAG", "onBindViewHolder " + position);
            holder.bind(c);
        }

        @Override
        public int getItemCount() {
            int x = commentsViewModel.getCurrent().size();
            Log.d("TAG","debug");
            return x;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Snackbar.make(requireView(), "CommentsFragment", 2000).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}