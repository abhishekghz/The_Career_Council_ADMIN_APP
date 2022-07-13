package com.example.thecareercouncil_adminapp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private var cards: List<Int>) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashcardpage,parent,false)
        return Pager2ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemImage.setImageResource(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }


    public class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val itemImage : ImageView = itemView.findViewById(R.id.the_card)

        init {


            itemImage.setOnClickListener{ v: View ->
                val position = adapterPosition
                val a = position+1

                intenting(a)

            }
        }

        fun intenting(a: Int)
        {
            if(a==1)
            {
                val intent = Intent(itemImage.context, view_pending::class.java)
                itemImage.context.startActivity(intent)
            }
            else if(a==2)
            {
                val intent = Intent(itemImage.context, view_completed::class.java)
                itemImage.context.startActivity(intent)
            }
            else if(a==3)
            {
                val intent = Intent(itemImage.context, userdetails::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==4)
            {
                val intent = Intent(itemImage.context, chat_req::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==5)
            {
                val intent = Intent(itemImage.context, chat::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==6)
            {
                val intent = Intent(itemImage.context, view_res_query::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==7)
            {
                val intent = Intent(itemImage.context, view_res_req_form::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==8)
            {
                val intent = Intent(itemImage.context, view_res_req_doc::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==9)
            {
                val intent = Intent(itemImage.context, view_jobappli_que::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==10)
            {
                val intent = Intent(itemImage.context, view_jobappli_form::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==11)
            {
                val intent = Intent(itemImage.context, opb_que::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==12)
            {
                val intent = Intent(itemImage.context, opb_req::class.java)
                itemImage.context.startActivity(intent)

            }

            else if(a==13)
            {
                val intent = Intent(itemImage.context, quiz_req::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==14)
            {
                val intent = Intent(itemImage.context, view_ccc_req::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==15)
            {
                val intent = Intent(itemImage.context, view_workshop::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==16)
            {
                val intent = Intent(itemImage.context, upload_workshop::class.java)
                itemImage.context.startActivity(intent)

            }
            else if(a==17)
            {
                val intent = Intent(itemImage.context, upload_news::class.java)
                itemImage.context.startActivity(intent)

            }

        }


    }

}