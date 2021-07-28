package com.example.androidproject.UI.MainApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.MainappNavigationDirections;
import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Holdable;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.OnDeleteClickListener;
import com.example.androidproject.Model.Listeners.OnEditClickListener;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
import com.example.androidproject.R;
import com.squareup.picasso.Picasso;

class RowViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView title;
    TextView content;
    ImageView photo;
    ImageButton edit;
    ImageButton delete;
    OnDeleteClickListener deleteClickListener;
    OnEditClickListener editClickListener;

    public RowViewHolder(@NonNull View itemView, OnItemClickListener itemListener, OnEditClickListener editListener, OnDeleteClickListener deleteListener) {
        super(itemView);
        username = itemView.findViewById(R.id.row_username);
        title = itemView.findViewById(R.id.row_title);
        content = itemView.findViewById(R.id.row_content);
        edit = itemView.findViewById(R.id.row_edit);
        delete = itemView.findViewById(R.id.row_delete);
        photo = itemView.findViewById(R.id.row_photo);
        itemView.setOnClickListener(v -> {
            if (itemListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    itemListener.onClick(position);
            }
        });
        deleteClickListener = deleteListener;
        editClickListener = editListener;
    }

    public void bind(Holdable b) {
        username.setText(b.getUsername());
        title.setText(b.getTitle());
        content.setText(b.getContent());
        photo.setImageResource(R.drawable.ic_launcher_background);
        if(b.getPhoto() != null && !b.getPhoto().equals("")){
            Picasso.get().load(b.getPhoto()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(photo);
        }else{
            photo.setVisibility(View.GONE);
        }

        edit.setVisibility(MyModel.CURRENT_USER.equals(b.getUsername())  ? View.VISIBLE : View.GONE);//TODO: add current user to model
        delete.setVisibility(MyModel.CURRENT_USER.equals(b.getUsername())  ? View.VISIBLE : View.GONE);
        edit.setOnClickListener(v -> editClickListener.onClick(b));
        delete.setOnClickListener(v -> deleteClickListener.onClick(b));
        username.setOnClickListener(v -> {
            MainappNavigationDirections.ActionGlobalProfileFragment action = MainappNavigationDirections.actionGlobalProfileFragment(username.getText().toString());
            Navigation.findNavController(v).navigate(action);
        });
    }
}
