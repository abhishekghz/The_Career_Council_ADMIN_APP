package com.example.thecareercouncil_adminapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecareercouncil_adminapp.databinding.ActivityUserdetailsBinding


class userdetails : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var user_search: SearchView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityUserdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetails)

        binding = ActivityUserdetailsBinding.inflate(layoutInflater)
        rv = findViewById(R.id.userdeetsrecv)
        user_search = findViewById(R.id.user_search)
        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter( userArrayList)
        rv.adapter = myAdapter


        val searchIcon = binding.userSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val textView = binding.userSearch.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        user_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter.filter(newText)
                return false
            }

        })
        myAdapter.setonitemclicklis(object : MyAdapter.onitemclicklis{
            override fun onitemclicklisf(num: String) {
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse("tel:$num")
                startActivity(i)
            }

        })
        EventchangeListener()
    }

    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("users").orderBy("Name",Query.Direction.ASCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error!= null){
                            Log.e("Firestore Error",error.message.toString())
                            return
                        }

                        for (dc: DocumentChange in value?.documentChanges!!)
                        {
                            if(dc.type==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.document.toObject(User::class.java))
                            }
                        }
                        myAdapter.notifyDataSetChanged()
                    }
                }
                )
    }
}