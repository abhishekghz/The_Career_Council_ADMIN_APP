package com.example.thecareercouncil_adminapp

import android.content.Intent
import android.icu.text.Transliterator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(private val userlist: ArrayList<User>): RecyclerView.Adapter<MyAdapter.myViewholder>(), Filterable {

    private lateinit var mlis : onitemclicklis

    interface onitemclicklis{
        fun onitemclicklisf(num : String)
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
                    val resultList = ArrayList<User>()
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
                fillist = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }

        }
    }

    var fillist = ArrayList<User>()

    init {
        fillist = userlist
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.myViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_cell,parent,false)

        return myViewholder(itemView, mlis)
    }

    override fun onBindViewHolder(holder: MyAdapter.myViewholder, position: Int) {


        val user : User = fillist[position]

        holder.Name.text = user. Name
        holder.Email.text = user. Email
        holder.MobNo.text = user. Mobno

    }


    override fun getItemCount(): Int {
       return  fillist.size

    }

    public class myViewholder(itemView: View, listener: onitemclicklis) : RecyclerView.ViewHolder(itemView)
    {
        val Name: TextView = itemView.findViewById(R.id.name)
        val Email: TextView = itemView.findViewById(R.id.email)
        val MobNo: TextView = itemView.findViewById(R.id.mobno)
        val call: Button = itemView.findViewById(R.id.call_btn)

init {

    call.setOnClickListener {
        listener.onitemclicklisf(MobNo.text.toString())
    }
}

    }


}