package com.example.notesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteFragment extends Fragment {
    private static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";
    private Button saveButton;
    private EditText themeEditText;
    private EditText descriptionNote;

    @Nullable
    private NoteEntity note = null;

    public static NoteFragment newInstance(@Nullable NoteEntity note) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_EXTRA_KEY, note);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        saveButton = view.findViewById(R.id.save_button);
        themeEditText = view.findViewById(R.id.theme_edit_text);
        descriptionNote = view.findViewById(R.id.description_edit_text);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        note = getArguments().getParcelable(NOTE_EXTRA_KEY);
        getActivity().setTitle(note == null ? R.string.create_note_title : R.string.edit_note_title);
        fillNote(note);
        saveButton.setOnClickListener(v -> {
            getContract().saveNote(gatherNote());
        });
    }

    private void fillNote(NoteEntity note) {
        if (note == null) return;
        themeEditText.setText(note.title);
        descriptionNote.setText(note.text);
    }

    private NoteEntity gatherNote() {
        return new NoteEntity(
                note == null ? NoteEntity.generateId() : note.id,
                themeEditText.getText().toString(),
                note == null ? NoteEntity.getCurrentDate() : note.creatingDate,
                descriptionNote.getText().toString());

    }

    interface Contract {
        void saveNote(NoteEntity noteEntity);

    }

    private Contract getContract() {
        return (Contract) getActivity();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException("Activity must implement Contract");
        }
    }
}


