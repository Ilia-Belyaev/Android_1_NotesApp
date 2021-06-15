package com.example.notesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class NotesListFragment extends Fragment {
    private final ArrayList<NoteEntity> noteList = new ArrayList<>();
    private LinearLayout listLinearLayout;
    private static final String PREF_NAME = "notes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_list_fragment, container, false);
        listLinearLayout = view.findViewById(R.id.list_layout);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        renderList(noteList);

    }

    interface Contract {
        void createNewNote();

        void editNote(NoteEntity note);
    }

    private Contract getContract() {
        return (Contract) getActivity();
    }

    private void renderList(List<NoteEntity> notes) {
        listLinearLayout.removeAllViews();
        for (NoteEntity note : notes) {
            Button button = new Button(getContext());
            button.setText(note.title);
            button.setOnClickListener(v -> {
                getContract().editNote(note);
            });

            listLinearLayout.addView(button);

        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException("Activity must implement Contract");
        }
    }


    @Nullable
    private NoteEntity findNoteWithId(String id) {
        for (NoteEntity note : noteList) {
            if (note.id.equals(id)) {
                return note;
            }
        }
        return null;
    }

    public void addNote(NoteEntity noteEntity) {
        NoteEntity sameNote = findNoteWithId(noteEntity.id);
        if (sameNote != null) {
            noteList.remove(sameNote);
        }
        noteList.add(noteEntity);
        renderList(noteList);
    }

}
