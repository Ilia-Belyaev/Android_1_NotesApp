package com.example.notesapp;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository {
    private static final String NOTES_TABLE_TITLE = "notes";
    private static List<NoteEntity> cache = new ArrayList<>();
    private final FirebaseFirestore db;
    private final List<Runnable> subscribers = new ArrayList<>();


    public FirebaseRepository() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db.setFirestoreSettings(settings);

        db.collection(NOTES_TABLE_TITLE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                refillCache(queryDocumentSnapshots);
            }
        });
        db.collection(NOTES_TABLE_TITLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                refillCache(value);
            }
        });
    }

    public static void sendNote(NoteEntity note) {
        (FirebaseFirestore.getInstance()).collection(NOTES_TABLE_TITLE).add(note);
    }

    List<NoteEntity> getNotes() {
        return cache;
    }

    private void refillCache(@Nullable QuerySnapshot snapshot) {
        if (snapshot == null) return;
        cache = new ArrayList<>();
        for (DocumentSnapshot document : snapshot.getDocuments()) {
            cache.add(document.toObject(NoteEntity.class));
        }
        notifySubscribers();
    }

    private void notifySubscribers() {
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }
    void subscribe(Runnable subscriber) {
        subscribers.add(subscriber);
    }

    void unsubscribe(Runnable subscriber) {
        subscribers.remove(subscriber);
    }

}
