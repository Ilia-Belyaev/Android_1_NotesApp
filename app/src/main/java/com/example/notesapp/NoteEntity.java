package com.example.notesapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

public class NoteEntity implements Parcelable {
    public final String id;
    public final String title;
    public final long creatingDate;
    public final String text;

    public NoteEntity(String id, String title, long date, String text) {
        this.id = id;
        this.title = title;
        this.creatingDate = date;
        this.text = text;
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        creatingDate = in.readLong();
        text = in.readString();
    }

    public static long getCurrentDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeLong(creatingDate);
        dest.writeString(text);
    }
}
