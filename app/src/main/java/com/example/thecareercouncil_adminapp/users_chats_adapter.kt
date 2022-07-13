package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class users_chats_adapter(private val userlist: ArrayList<chat_data_class>): RecyclerView.Adapter<users_chats_adapter.myViewholder>(),
    Filterable {

    private lateinit var mlis : onitemclicklis

    interface onitemclicklis{
        fun onitemclicklisf(id : String, Email:String)
    }

    fun setonitemclicklis(listener: onitemclicklis)
    {
        mlis= listener
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    fillist = userlist
                } else {
                    val resultList = ArrayList<chat_data_class>()
                    for (row in userlist) {
                        if (charSearch.lowercase() in row.Name.toString().lowercase()) {
                            resultList.add(row)
                        }
                    }
                    fillist = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = fillist
                return filterResults

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                fillist = results?.values as ArrayList<chat_data_class>
                notifyDataSetChanged()
            }

        }
    }

    var fillist = ArrayList<chat_data_class>()

    init {
        fillist = userlist
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): users_chats_adapter.myViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item,parent,false)

        return myViewholder(itemView, mlis)
    }

    override fun onBindViewHolder(holder: users_chats_adapter.myViewholder, position: Int) {


        val user : chat_data_class = fillist[position]

        holder.Name.text = user. Name
        holder.id.text = user.id
        holder.Email.text = user. Email


    }


    override fun getItemCount(): Int {
        return  fillist.size

    }

    public class myViewholder(itemView: View, listener: onitemclicklis) : RecyclerView.ViewHolder(itemView)
    {
        val Name: TextView = itemView.findViewById(R.id.unamechat)
        val Email: TextView = itemView.findViewById(R.id.uemailchat)
        val id: TextView = itemView.findViewById(R.id.id)
        val btn: Button = itemView.findViewById(R.id.chatbtn)

        init {

            btn.setOnClickListener {
                listener.onitemclicklisf(id.text.toString(),Email.text.toString())
            }
        }

    }


}