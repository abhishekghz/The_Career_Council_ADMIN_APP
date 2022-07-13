package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class completed_adapter(private val nlist: ArrayList<completed_data_class>): RecyclerView.Adapter<completed_adapter.myViewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): completed_adapter.myViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.completed_item, parent, false)
        return completed_adapter.myViewholder(itemView)

    }

    override fun onBindViewHolder(holder: completed_adapter.myViewholder, position: Int) {
        val ns: completed_data_class = nlist[position]



        holder.uname.text = ns.uname
        holder.uemail.text = ns.uemail
        holder.uphone.text = ns.uphone
        holder.created.text = ns.created
        holder.mssg.text = ns.mssg
        holder.src.text = ns.source
        holder.id.text = ns.id


    }

    override fun getItemCount(): Int {
        return nlist.size

    }
    public class myViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uname: TextView = itemView.findViewById(R.id.uname)
        val uemail: TextView = itemView.findViewById(R.id.uemail)
        val uphone: TextView = itemView.findViewById(R.id.uphone)
        val created: TextView = itemView.findViewById(R.id.created)
        val mssg: TextView = itemView.findViewById(R.id.mssg)
        val src: TextView = itemView.findViewById(R.id.source)
        val id: TextView = itemView.findViewById(R.id.docidc)
    }

}