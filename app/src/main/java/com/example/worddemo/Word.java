package com.example.worddemo;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "Word")
    private String mWord;

    public Word(int id, String word)
    {
        this.mWord = word;
        this.id = id;

    }

    public Word(String word) {

    }

    @NonNull
    public String getWord()
    {
        return this.mWord;
    }
    public int getId(){return id;}
    public void setId(int id)
    { this.id = id;}

}
