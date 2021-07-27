package com.example.androidproject.UI.MainApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.MainappNavigationDirections;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Listeners.OnDeleteClickListener;
import com.example.androidproject.Model.Listeners.OnEditClickListener;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
import com.example.androidproject.R;

import java.util.List;

public class CommentsFragment extends Fragment {

    private CommentsViewModel commentsViewModel;
    private String postID;
    private String parentCommentID;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        setHasOptionsMenu(true);

        RecyclerView commentsList = root.findViewById(R.id.main_comments_list);

        commentsList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        CommentsListAdapter adapter = new CommentsListAdapter();

        CommentsFragmentArgs args = CommentsFragmentArgs.fromBundle(getArguments());
        postID = args.getPostID();
        parentCommentID = args.getParentCommentID();
        username = args.getUsername();


        commentsList.setAdapter(adapter);
        initViewModel(adapter);
        setListeners(adapter,root);

        return root;
    }

    private void setListeners(CommentsListAdapter adapter, View root) {
        adapter.setOnListItemClickListener(position -> {
            Comment c = commentsViewModel.data.getValue().get(position);
            CommentsFragmentDirections.ActionCommentsFragmentSelf action = CommentsFragmentDirections.actionCommentsFragmentSelf(c.getPostID(), c.getCommentID());//c is the parent comment now
            Navigation.findNavController(root).navigate(action);
        });
        adapter.setEditListener(holdable -> {
                    Comment c = (Comment) holdable;
                    NavDirections action = MainappNavigationDirections.actionGlobalEditFragment(c.getPostID(), c.getCommentID(), c.getParentCommentID(), false);
                    Navigation.findNavController(root).navigate(action);
                }
        );
        adapter.setDeleteListener(holdable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    DialogInterface.OnClickListener listener = (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            Comment c = (Comment) holdable;
                            commentsViewModel.deleteComment(c);
                        }
                    };
                    builder.setMessage("Are you sure?")
                            .setPositiveButton("Yes", listener)
                            .setNegativeButton("No", listener)
                            .show();
                }
        );

    }

    private void initViewModel(CommentsListAdapter adapter) {
        if (username != null) {//view all comments of user
            commentsViewModel.getCommentsOfUser(username).observe(getViewLifecycleOwner(), comments ->
                    adapter.notifyDataSetChanged());
        } else if (parentCommentID != null) {//nested comment
            commentsViewModel.getCommentsOfComment(parentCommentID).observe(getViewLifecycleOwner(), comments ->
                    adapter.notifyDataSetChanged());
        } else {//regular comment
            commentsViewModel.getCommentsOfPost(postID).observe(getViewLifecycleOwner(), comments ->
                    adapter.notifyDataSetChanged());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                MainappNavigationDirections.ActionGlobalEditFragment action = MainappNavigationDirections.actionGlobalEditFragment(postID, null, parentCommentID, true);
                Navigation.findNavController(requireView()).navigate(action);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CommentsListAdapter extends RecyclerView.Adapter<RowViewHolder> {
        private OnItemClickListener listener;
        private OnEditClickListener editListener;
        private OnDeleteClickListener deleteListener;

        public void setEditListener(OnEditClickListener editListener) {
            this.editListener = editListener;
        }

        public void setDeleteListener(OnDeleteClickListener deleteListener) {
            this.deleteListener = deleteListener;
        }

        @NonNull
        @Override
        public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.row, parent, false);
            return new RowViewHolder(view, listener, editListener, deleteListener);
        }

        public void setOnListItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

            Comment c = commentsViewModel.data.getValue().get(position);
            Log.d("TAG", "onBindViewHolder " + position);
            holder.bind(c);
        }

        @Override
        public int getItemCount() {
            List<Comment> comments = commentsViewModel.data.getValue();
            if (comments == null) return 0;
            return comments.size();
        }
    }
}