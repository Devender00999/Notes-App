package com.aryafacilities.notes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Notes {
    private String noteTitle;
    private String noteText;
    private String noteDate;

    public Notes(String noteTitle, String noteText, String noteDate) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.noteDate = noteDate;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
