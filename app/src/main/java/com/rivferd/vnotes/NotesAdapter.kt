package com.rivferd.vnotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rivferd.vnotes.database.Note

class NotesAdapter(private val listNote: ArrayList<Note>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        val context: Context
        fun onItemClick(intent: Intent)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        var tvDateCreated: TextView = itemView.findViewById(R.id.tv_date_created)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.tvTitle.text = listNote[position].title
        holder.tvDateCreated.text = listNote[position].dateCreated

        // Set listener for each item view
        holder.itemView.setOnClickListener {
            val intent = Intent(onItemClickListener.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.ID_KEY, listNote[position].id.toString())
            onItemClickListener.onItemClick(intent)
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }
}