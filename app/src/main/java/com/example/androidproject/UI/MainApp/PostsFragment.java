package com.example.androidproject.UI.MainApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidproject.MainappNavigationDirections;
import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.OnDeleteClickListener;
import com.example.androidproject.Model.Listeners.OnEditClickListener;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
import com.example.androidproject.R;

import java.util.List;

//TODO: livedata
public class PostsFragment extends Fragment {
    PostsViewModel postsViewModel;
    private String username;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_posts, container, false);

        RecyclerView postsList = root.findViewById(R.id.main_posts_list);
        ProgressBar progressBar = root.findViewById(R.id.main_posts_progressBar);
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout = root.findViewById(R.id.main_comments_refresh);
        postsList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        PostsListAdapter adapter = new PostsListAdapter();

        setListeners(adapter, root, progressBar);
        postsList.setAdapter(adapter);
        username = PostsFragmentArgs.fromBundle(getArguments()).getUsername();//only posts from user or all if null
        initViewModel(adapter,progressBar);
        setHasOptionsMenu(true);

        swipeRefreshLayout.setOnRefreshListener(() -> postsViewModel.refresh(() -> {
        }));
        MyModel.instance.postsLoadingState.observe(getViewLifecycleOwner(), postLoadingState -> {
            if (postLoadingState == MyModel.PostLoadingState.loading) {
                progressBar.setVisibility(View.VISIBLE);
            }else{
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }

    private void setListeners(PostsListAdapter adapter, View root, ProgressBar progressBar) {
        adapter.setOnListItemClickListener(position -> {
                    Post p = postsViewModel.getCurrent().get(position);
                    NavDirections action = PostsFragmentDirections.actionPostsFragmentToCommentsFragment(p.getPostID(), null);
                    Navigation.findNavController(root).navigate(action);
                }
        );
        adapter.setEditListener(holdable -> {
                    Post p = (Post) holdable;
                    NavDirections action =
                            PostsFragmentDirections
                                    .actionGlobalEditFragment(p.getPostID(), null, null, false);
                    Navigation.findNavController(root).navigate(action);
                }
        );
        adapter.setDeleteListener(holdable -> {

                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    DialogInterface.OnClickListener listener = (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            Post p = (Post) holdable;
                            postsViewModel.deletePost(p, () ->{
                                progressBar.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                            });
                        }
                    };
                    builder.setMessage("Are you sure?")
                            .setPositiveButton("Yes", listener)
                            .setNegativeButton("No", listener)
                            .show();
                }
        );

    }

    private void initViewModel(PostsListAdapter adapter, ProgressBar progressBar) {
        if (username != null) {//view all posts of user
            postsViewModel.getAllPosts().observe(getViewLifecycleOwner(), posts ->
                    postsViewModel.getPostsOfUser(username, posts1 -> {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    })
            );
        }//view all posts
        else postsViewModel.getAllPosts().observe(getViewLifecycleOwner(), posts ->
                postsViewModel.getPosts(posts1 -> {
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                MainappNavigationDirections.ActionGlobalEditFragment action = MainappNavigationDirections.actionGlobalEditFragment(null, null, null, true);
                Navigation.findNavController(requireView()).navigate(action);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class PostsListAdapter extends RecyclerView.Adapter<RowViewHolder> {
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
            Post p = postsViewModel.getCurrent().get(position);
            Log.d("TAG", "onBindViewHolder " + position);
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            List<Post> posts = postsViewModel.getCurrent();
            if (posts == null) return 0;
            return posts.size();
        }
    }


}