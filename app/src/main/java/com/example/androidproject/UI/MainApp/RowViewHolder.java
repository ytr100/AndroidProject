package com.example.androidproject.UI.MainApp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Model.Entity.Holdable;
import com.example.androidproject.Model.Listeners.OnItemClickListener;
import com.example.androidproject.R;

 class RowViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView title;
    TextView content;
    ImageView photo;
    ImageButton edit;
    ImageButton delete;

    public RowViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        username = itemView.findViewById(R.id.row_username);
        title = itemView.findViewById(R.id.row_title);
        content = itemView.findViewById(R.id.row_content);
        edit = itemView.findViewById(R.id.row_edit);
        delete = itemView.findViewById(R.id.row_delete);
        photo = itemView.findViewById(R.id.row_photo);
        itemView.setOnClickListener(v -> {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                    listener.onClick(position);
            }
        });
        edit.setOnClickListener(v->{});//TODO: navigate to edit/delete screen
        delete.setOnClickListener(v->{});
    }

    public void bind(Holdable b) {
        username.setText(b.getUsername());
        title.setText(b.getTitle());
        content.setText(b.getContent());
        photo.setVisibility(View.VISIBLE);//TODO: add photo support
        edit.setVisibility(View.VISIBLE);//TODO: check if current user
        delete.setVisibility(View.VISIBLE);
    }
}
