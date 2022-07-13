package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class commonadaptertwo(private val nlist: ArrayList<commonadaptertwodata>): RecyclerView.Adapter<commonadaptertwo.myViewholder>() ,
    Filterable {
    private lateinit var mlis : onitemclicklis

    interface onitemclicklis{
        fun onitemclicklisf(id : String)
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
                    fillist = nlist
                } else {
                    val resultList = ArrayList<commonadaptertwodata>()
                    for (row in nlist) {
                        if (charSearch.lowercase() in row.uname.toString().lowercase()) {
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
                fillist = results?.values as ArrayList<commonadaptertwodata>
                notifyDataSetChanged()
            }

        }
    }

    var fillist = ArrayList<commonadaptertwodata>()

    init {
        fillist = nlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commonadaptertwo.myViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.itemtwo, parent, false)
        return myViewholder(itemView,mlis)
    }

    override fun onBindViewHolder(holder: commonadaptertwo.myViewholder, position: Int) {
        val ns: commonadaptertwodata = fillist[position]
        val link =ns.img
        val sdf= ns.created?.toDate().toString()


        holder.title.text=ns.title
        holder.uname.text = ns.uname
        holder.uemail.text=ns.uemail
        holder.link.text=ns.link
        holder.uphone.text=ns.uphone
        holder.created.text=sdf
        holder.mssg.text=ns.mssg
        Picasso.get()
            .load(link)
            .into(holder.image);



    }

    override fun getItemCount(): Int {
        return fillist.size

    }
    public class myViewholder(itemView: View,listener: commonadaptertwo.onitemclicklis) : RecyclerView.ViewHolder(itemView) {
        val uname: TextView = itemView.findViewById(R.id.uname)
        val uemail: TextView = itemView.findViewById(R.id.uemail)
        val uphone: TextView = itemView.findViewById(R.id.uphone)
        val created: TextView = itemView.findViewById(R.id.created)
        val title: TextView = itemView.findViewById(R.id.By)
        val mssg: TextView = itemView.findViewById(R.id.mssg)
        val image: ImageView = itemView.findViewById(R.id.image)
        val link: TextView = itemView.findViewById(R.id.link)
        val btn: Button = itemView.findViewById(R.id.dw)

        init {

            btn.setOnClickListener {
                listener.onitemclicklisf(link.text.toString())
            }
        }
    }
}