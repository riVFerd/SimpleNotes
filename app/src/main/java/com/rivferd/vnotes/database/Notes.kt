package com.rivferd.vnotes.database

import android.content.Context
import android.database.Cursor
import com.rivferd.vnotes.DetailActivity

class Notes(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insert(titleValue: String, content: String) {
        dbHelper.writableDatabase.execSQL(
            """
            INSERT INTO notes (title, date_created, content)
            VALUES ("$titleValue", datetime("now", "localtime"), "$content");
        """.trimIndent()
        )
    }

    fun update(titleValue: String, content: String, idValue: Int) {
        dbHelper.writableDatabase.execSQL(
            """
                UPDATE notes
                SET title = "$titleValue", content = "$content"
                WHERE id = $idValue
            """.trimIndent()
        )
    }

    fun deleteByName(titleValue: String) {
        dbHelper.writableDatabase.execSQL(
            """
            DELETE FROM notes WHERE title = "$titleValue"
        """.trimIndent()
        )
    }

    fun selectAll(): Cursor {
        return dbHelper.readableDatabase.rawQuery("SELECT * FROM notes;", null)
    }

    fun selectOneById(idValue: String): Cursor {
        return dbHelper.readableDatabase.rawQuery(
            """
            SELECT * FROM notes WHERE id = "$idValue";
        """.trimIndent(), null
        )
    }

    fun selectOneByTitle(titleValue: String): Cursor {
        return dbHelper.readableDatabase.rawQuery(
            """
            SELECT * FROM notes WHERE title = "$titleValue";
        """.trimIndent(), null
        )
    }

    /**
     * Technically return id of the file in the SQLite database
     */
    fun getId(titleValue: String): String {
        val cursor = selectOneByTitle(titleValue)
        return if (cursor.moveToFirst()) cursor.getInt(0).toString()
        else DetailActivity.NO_FILES_FOUND
    }

    /**
     * DELETE ALL DATA FROM DATABASE
     * Only for resetting SQLite
     */
    fun deleteAll() {
        dbHelper.writableDatabase.execSQL(
            """
            DELETE FROM notes;
        """.trimIndent()
        )
    }

}