package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NoteFragment.Controller {
    private static LinearLayout linearLayout;
    private HashMap<String, String> noteInfo = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_layout, new NotesListFragment())
                .commit();

        linearLayout = findViewById(R.id.linear_layout);
    }

    @Override
    public void saveResult(NoteEntity noteEntity) {
        String note = noteEntity.note;
        String themeNote = noteEntity.theme;
        noteInfo.put(note,themeNote);
        getSupportFragmentManager().popBackStack();
        TextView textView = new TextView(this);
        textView.setText(note);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(Color.parseColor("#ffffff"));
        linearLayout.addView(textView);

    }

    @Override
    public void createNewNote(NoteEntity noteEntity) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .add(isLandscape ? R.id.detail_container : R.id.fragment_layout, NoteFragment.newInstance(noteEntity))
                .addToBackStack(null)
                .commit();

    }
}

