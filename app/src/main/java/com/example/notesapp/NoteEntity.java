package com.example.notesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteEntity implements Parcelable {

    public String theme;
    public String note;

    public NoteEntity(String theme, String note) {
        this.theme = theme;
        this.note = note;
    }

    protected NoteEntity(Parcel in) {
        theme = in.readString();
        note = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(theme);
        dest.writeString(note);

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

}
