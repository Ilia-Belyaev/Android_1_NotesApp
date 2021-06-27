package com.example.notesapp;

import android.app.AlertDialog;
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

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
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
                        new AlertDialog.Builder(itemView.getContext())
                                .setTitle(R.string.alert)
                                .setIcon(R.drawable.ic_alert)
                                .setMessage(R.string.alert_message)
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes,(d,i)->{
                                    //todo удаляем заметку
                                })
                                .setNegativeButton(R.string.no,(d,i)->{
                                    //todo просто закрываем окно
                                })
                                .show();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
        cardView.setCardBackgroundColor(new Random().nextInt());
    }

    public void bind(NoteEntity noteEntity,String id) {
        this.noteEntity = noteEntity;
        subjectTextView.setText(noteEntity.title);
        descriptionTextView.setText(noteEntity.text);

    }

}
