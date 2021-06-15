package com.example.notesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Objects;


public class NotesListFragment extends Fragment {
    private final NoteEntity noteEntity1 = new NoteEntity("", "");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_fragment, null);
    }

    private void addNoteToList(NoteEntity noteEntity) {
        Button button = requireView().findViewById(R.id.create_note);
        button.setOnClickListener(v -> {
            ((Controller) getActivity()).createNewNote(noteEntity);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = view.findViewById(R.id.linear);
        addNoteToList(noteEntity1);
    }

    interface Controller {
        void createNewNote(NoteEntity noteEntity);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement ProfileListFragment.Controller");
        }
    }
}