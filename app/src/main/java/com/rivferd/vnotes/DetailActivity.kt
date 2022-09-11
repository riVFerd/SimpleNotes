package com.rivferd.vnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.rivferd.vnotes.database.Notes

class DetailActivity : AppCompatActivity() {

    companion object {
        const val NO_FILES_FOUND = "no_files_found"
        const val ID_KEY = "file_name"
    }

    private lateinit var edtTitle: EditText
    private lateinit var edtContent: EditText
    private lateinit var tvDelete: TextView
    private lateinit var tvSave: TextView
    private lateinit var notesModel: Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Views binding
        findViews()

        // function to make differences between creating new file and opening existing file
        if (!isNew()) openExisting()

        // Button restriction
        setRestriction()

        // Set up all event listener
        setListeners()
    }

    private fun openExisting() {
        // if opening existing file
        Log.d("Restriction", "setPageType: Save button clickable")
        val rawFileName = intent.getStringExtra(ID_KEY)
        val cursor = notesModel.selectOneById(rawFileName!!)
        cursor.moveToFirst()
        edtTitle.setText(cursor.getString(1))
        edtContent.setText(cursor.getString((3)))
    }

    // return true if creating new notes
    private fun isNew(): Boolean {
        if (!intent.hasExtra(ID_KEY)) {
            tvDelete.isEnabled = false
            return true
        }
        return false
    }

    private fun setRestriction() {
        tvSave.isEnabled =
            !(edtTitle.text.isEmpty() || edtContent.text.toString().isEmpty())
    }

    private fun setListeners() {
        tvSave.setOnClickListener {
            // Add data to database
            Log.d("DATABASE CREATION", "Inserting data")
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()

            if (isNew()) {
                notesModel.insert(title, content)
            } else {
                notesModel.update(title, content, Integer.parseInt(intent.getStringExtra(ID_KEY)!!))
            }

            Log.d("DATABASE CREATION", "Insert data success")


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        tvDelete.setOnClickListener {
            // Delete from database
            val currentTitle = edtTitle.text.toString()
            notesModel.deleteByName(currentTitle)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        edtTitle.addTextChangedListener{
            setRestriction()
        }

        edtContent.addTextChangedListener {
            setRestriction()
        }
    }

    private fun findViews() {
        edtTitle = findViewById(R.id.edt_title)
        edtContent = findViewById(R.id.edt_content)
        tvDelete = findViewById(R.id.tv_delete)
        tvSave = findViewById(R.id.tv_save)
        notesModel = Notes(this)
    }
}