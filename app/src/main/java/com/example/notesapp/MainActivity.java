package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Contract, NoteFragment.Contract {
    private static final String NOTES_LIST_FRAGMENT_TAG = "NOTES_LIST_FRAGMENT_TAG";
    private boolean isTwoPanelMode = false;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTwoPanelMode = findViewById(R.id.optional_fragment_container) != null;
        showNoteList();
        navView = findViewById(R.id.navigation_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.note_list_fragment:
                        showNoteList();
                        break;
                    case R.id.note_fragment:
                        showThisNote();
                        break;
                    case R.id.settings:
                        showSettings();
                        break;

                }
                return true;
            }
        });
    }

    private void showSettings() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!isTwoPanelMode) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.add(isTwoPanelMode ? R.id.optional_fragment_container : R.id.main_fragment_container, new Settings());
        fragmentTransaction.commit();
    }

    private void showNoteList() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, new NotesListFragment(), NOTES_LIST_FRAGMENT_TAG)
                .commit();
    }

    private void showThisNote() {
        showThisNote(null);
    }

    private void showThisNote(@Nullable NoteEntity note) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!isTwoPanelMode) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.add(isTwoPanelMode ? R.id.optional_fragment_container : R.id.main_fragment_container, NoteFragment.newInstance(note));
        fragmentTransaction.commit();
    }

    @Override
    public void createNewNote() {
        showThisNote();
    }

    @Override
    public void editNote(NoteEntity note) {
        showThisNote(note);
    }

    @Override
    public void saveNote(NoteEntity note) {
        setTitle(R.string.app_name);
        getSupportFragmentManager().popBackStackImmediate();
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        notesListFragment.addNote(note);
    }
}