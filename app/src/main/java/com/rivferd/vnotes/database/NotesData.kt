package com.rivferd.vnotes.database

import android.database.Cursor
import android.util.Log

object NotesData {

    fun getListNote(dataFromDB: Cursor): ArrayList<Note> {
        val listNote = arrayListOf<Note>()
        if (!dataFromDB.moveToFirst()) return listNote
        while (!dataFromDB.isAfterLast) {
            listNote.add(
                Note(
                    dataFromDB.getInt(0),
                    dataFromDB.getString(1),
                    dataFromDB.getString(2),
                    dataFromDB.getString(3)
                )
            )
            dataFromDB.moveToNext()
        }
        return listNote
    }

}

data class Note(
    val id: Int,
    val title: String,
    val dateCreated: String,
    val content: String
)
