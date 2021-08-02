package com.example.myapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter(var context: Context) : RecyclerView.Adapter<NoteAdapter.NoteHolder>(){

    var listener:OnItemClickListener?=null
    companion object{
        lateinit var list:List<Note>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.node_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item= list?.get(position)
        if (item != null) {
            holder.title.text=item.title
            holder.id.text= item.priority.toString()
            holder.description.text=item.description
        }

    }

    override fun getItemCount(): Int {


        return list.size
    }
    fun setNotes(note: List<Note>) {
        list = note
        notifyDataSetChanged()
    }
    fun getNoteAt(position: Int): Note {
        return list[position]
    }

    inner class NoteHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById(R.id.text_view_title)
        val description: TextView=itemView.findViewById(R.id.text_view_description)
        val id: TextView = itemView.findViewById(R.id.text_view_priority)

        init
        {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(list.get(position))
                }
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listenr: OnItemClickListener) {
        listener = listenr
    }

}