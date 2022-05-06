package com.example.notebook.bean;

import java.io.Serializable;

public class NotepadBean implements Serializable {
    private int id;
    private String notepadContent;
    private String notepadTime;

    public NotepadBean() {
    }

    public NotepadBean(int id, String notepadContent, String notepadTime) {
        this.id = id;
        this.notepadContent = notepadContent;
        this.notepadTime = notepadTime;
    }

    public NotepadBean(String notepadContent, String notepadTime) {
        this.notepadContent = notepadContent;
        this.notepadTime = notepadTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotepadContent() {
        return notepadContent;
    }

    public void setNotepadContent(String notepadContent) {
        this.notepadContent = notepadContent;
    }

    public String getNotepadTime() {
        return notepadTime;
    }

    public void setNotepadTime(String notepadTime) {
        this.notepadTime = notepadTime;
    }
}
