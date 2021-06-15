package com.example.notesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;


public class NoteFragment extends Fragment {
    public static final String DOSSIER_ARGS_KEY = "DOSSIER_ARGS_KEY";
    private Button saveButton;
    private NoteEntity noteEntity = null;
    private EditText theme;
    private EditText note;

    public static NoteFragment newInstance(NoteEntity noteEntity) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(DOSSIER_ARGS_KEY, noteEntity);
        fragment.setArguments(args);
        return fragment;
    }
    public NoteFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, null);
        theme = view.findViewById(R.id.subject_of_the_note);
        note = view.findViewById(R.id.description_of_the_note);
        saveButton = view.findViewById(R.id.save_note);
        saveButton.setOnClickListener(v -> {
            Controller controller = (Controller) getActivity();
            controller.saveResult(new NoteEntity(
                    theme.getText().toString(),
                    note.getText().toString()
            ));
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        theme.setText(noteEntity.theme);
        note.setText(noteEntity.note);
    }

    public interface Controller {
        void saveResult(NoteEntity noteEntity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement ProfileFragment.Controller");
        }
        if (getArguments() != null) {
            noteEntity = getArguments().getParcelable(DOSSIER_ARGS_KEY);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}