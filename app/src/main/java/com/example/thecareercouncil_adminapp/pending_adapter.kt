package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp

class pending_adapter(private val nlist: ArrayList<pending_data_class>): RecyclerView.Adapter<pending_adapter.myViewholder>() {

    private lateinit var mlis: onitemclicklis

    interface onitemclicklis {
        fun onitemclicklisf(id: String, uname:String, uemail:String, uphone: String, created:String, mssg: String, src: String)
    }

    fun setonitemclicklis(listener: onitemclicklis) {
        mlis = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pending_adapter.myViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.pending_item, parent, false)
        return myViewholder(itemView, mlis)
    }

    override fun onBindViewHolder(holder: pending_adapter.myViewholder, position: Int) {
        val ns: pending_data_class = nlist[position]
        val sdf = ns.created?.toDate().toString()


        holder.uname.text = ns.uname
        holder.uemail.text = ns.uemail
        holder.uphone.text = ns.uphone
        holder.created.text = sdf
        holder.mssg.text = ns.mssg
        holder.src.text = ns.source
        holder.id.text = ns.id

    }

    override fun getItemCount(): Int {
        return nlist.size

    }

    public class myViewholder(itemView: View, listener: pending_adapter.onitemclicklis) : RecyclerView.ViewHolder(itemView) {
        val uname: TextView = itemView.findViewById(R.id.uname)
        val uemail: TextView = itemView.findViewById(R.id.uemail)
        val uphone: TextView = itemView.findViewById(R.id.uphone)
        val created: TextView = itemView.findViewById(R.id.created)
        val mssg: TextView = itemView.findViewById(R.id.mssg)
        val src: TextView = itemView.findViewById(R.id.source)
        val id: TextView = itemView.findViewById(R.id.docid)

        val btn: Button = itemView.findViewById(R.id.compbutton)

        init {

            btn.setOnClickListener {
                listener.onitemclicklisf(id.text.toString(),uname.text.toString(),uemail.text.toString(),uphone.text.toString(),Timestamp.now().toDate().toString(),mssg.text.toString(),src.text.toString())
            }


        }

    }
}