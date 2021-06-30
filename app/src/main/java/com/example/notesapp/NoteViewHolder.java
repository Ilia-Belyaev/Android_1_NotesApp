package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView subjectTextView;
    private final TextView descriptionTextView;
    private final CardView cardView;
    private NoteEntity noteEntity;
//    private CardSource data;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
//        int position = NotesAdapter.getMenuPosition();
        subjectTextView = itemView.findViewById(R.id.theme_text_view);
        descriptionTextView = itemView.findViewById(R.id.description_text_view);
        itemView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
            popupMenu.inflate(R.menu.note_item_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.update_note:
                        assert clickListener != null;
                        clickListener.onItemClick(noteEntity);
                        break;
                    case R.id.delete_note:
//                        data.deleteCardData(position);
//                        NotesAdapter.notifyItemRemoved(position);
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        cardView.setCardBackgroundColor(new Random().nextInt());
    }

    public void bind(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
        subjectTextView.setText(noteEntity.title);
        descriptionTextView.setText(noteEntity.text);

    }

}
