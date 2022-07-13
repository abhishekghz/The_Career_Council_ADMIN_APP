package com.example.thecareercouncil_adminapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.squareup.picasso.Picasso

class commonadapterone(private val nlist: ArrayList<commonadapteronedata>): RecyclerView.Adapter<commonadapterone.myViewholder>() , Filterable{

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    fillist = nlist
                } else {
                    val resultList = ArrayList<commonadapteronedata>()
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
                fillist = results?.values as ArrayList<commonadapteronedata>
                notifyDataSetChanged()
            }

        }
    }

    var fillist = ArrayList<commonadapteronedata>()

    init {
        fillist = nlist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commonadapterone.myViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.itemone, parent, false)
        return myViewholder(itemView)
    }

    override fun onBindViewHolder(holder: commonadapterone.myViewholder, position: Int) {
        val ns: commonadapteronedata = fillist[position]
        val link =ns.img
        val sdf= ns.created?.toDate().toString()


        holder.title.text=ns.title
        holder.uname.text = ns.uname
        holder.uemail.text=ns.uemail
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
    public class myViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val uname: TextView = itemView.findViewById(R.id.uname)
        val uemail: TextView = itemView.findViewById(R.id.uemail)
        val uphone: TextView = itemView.findViewById(R.id.uphone)
        val created: TextView = itemView.findViewById(R.id.created)
        val title: TextView = itemView.findViewById(R.id.By)
        val mssg: TextView = itemView.findViewById(R.id.mssg)
        val image: ImageView = itemView.findViewById(R.id.image)


    }

}