package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.type.Date
import java.text.SimpleDateFormat

class query_adapter(private val nlist: ArrayList<query_data_class>): RecyclerView.Adapter<query_adapter.myViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): query_adapter.myViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.query_item, parent, false)
        return myViewholder(itemView)
    }

    override fun onBindViewHolder(holder: query_adapter.myViewholder, position: Int) {
        val ns: query_data_class = nlist[position]
        val sdf= ns.created?.toDate().toString()


        holder.uname.text = ns.uname
        holder.uemail.text=ns.uemail
        holder.uphone.text=ns.uphone
        holder.created.text=sdf
        holder.mssg.text=ns.mssg


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


    }

}