package com.rivferd.vnotes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rivferd.vnotes.database.Notes
import com.rivferd.vnotes.database.NotesData

class MainActivity : AppCompatActivity() {

    private lateinit var fabNewNote: FloatingActionButton
    private lateinit var rvNoteList: RecyclerView
    private lateinit var tvMessage: TextView
    private lateinit var notesModel: Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up action bar
        setAppBar()

        // Views binding
        findViews()

        // Set up all event listener
        setListeners()

        // Set up recycler view
        setRecyclerView()
    }

    private fun setRecyclerView() {
//        notesModel.deleteAll()
        val listNote = NotesData.getListNote(notesModel.selectAll())
        rvNoteList.layoutManager = LinearLayoutManager(this)

        val adapter = NotesAdapter(listNote)
        val mainActivityContext: Context = this
        adapter.setOnItemClickListener(
            object : NotesAdapter.OnItemClickListener {
                override val context = mainActivityContext

                override fun onItemClick(intent: Intent) {
                    startActivity(intent)
                }
            }
        )
        rvNoteList.adapter = adapter


        // Set list visibility based on how much data on it
        if (listNote.size > 0) {
            tvMessage.visibility = View.GONE
            rvNoteList.visibility = View.VISIBLE
        }
    }

    private fun setAppBar() {
        supportActionBar?.title = "Simple Notes"
    }

    private fun setListeners() {
        fabNewNote.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findViews() {
        fabNewNote = findViewById(R.id.fab_new_note)
        rvNoteList = findViewById(R.id.rv_note_list)
        tvMessage = findViewById(R.id.tv_message)
        notesModel = Notes(this)
    }
}