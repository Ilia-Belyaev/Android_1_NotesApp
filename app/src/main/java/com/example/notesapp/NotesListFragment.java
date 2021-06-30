package com.example.notesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NotesListFragment extends Fragment{
    private final ArrayList<NoteEntity> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
//    private Button buttonCreateNote;
    private final String uid = UUID.randomUUID().toString();
    private FirebaseRepository repo;
    private static final String PREF_NAME = "notes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }
    private final Runnable subscriber = () -> {
        updateAllNotes(noteList);
    };
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        adapter = new NotesAdapter();
        adapter.setOnItemClickListener(getContract()::editNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        repo = new FirebaseRepository();

        updateAllNotes(noteList);

//        buttonCreateNote.setOnClickListener(v -> {
//            getContract().createNewNote();
//        });

        repo.subscribe(subscriber);

    }

    interface Contract {
        void createNewNote();

        void editNote(NoteEntity note);
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
    private void updateAllNotes(List<NoteEntity> notes) {
        adapter.setData(notes, uid);
        List<NoteEntity> sortedNotes = repo.getNotes();
        Collections.sort(sortedNotes, (o1, o2) -> o1.getCreatingDate() > o2.getCreatingDate() ? 1 : -1);
        adapter.setData(sortedNotes, uid);
        adapter.notifyDataSetChanged();
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
        FirebaseRepository.sendNote(noteEntity);
        updateAllNotes(noteList);
    }

}
