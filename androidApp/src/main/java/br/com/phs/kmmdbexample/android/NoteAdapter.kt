package br.com.phs.kmmdbexample.android

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.phs.shared.cache.Note

class NoteAdapter: ListAdapter<Note, NoteAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItem(position)) {
            holder.itemView.findViewById<TextView>(R.id.noteTV).text = this.name
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    }

    class ViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.note_list_layout, parent, false)
    )

}