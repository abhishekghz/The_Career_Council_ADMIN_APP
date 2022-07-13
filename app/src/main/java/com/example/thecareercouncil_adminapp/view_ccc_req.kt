package com.example.thecareercouncil_adminapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecareercouncil_adminapp.databinding.ActivityOpbReqBinding
import com.example.thecareercouncil_adminapp.databinding.ActivityViewCccReqBinding
import com.google.firebase.firestore.*

class view_ccc_req : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var user_search: SearchView
    private lateinit var userArrayList: ArrayList<commonadapteronedata>
    private lateinit var myAdapter: commonadapterone
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityViewCccReqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_ccc_req)
        binding = ActivityViewCccReqBinding.inflate(layoutInflater)
        rv = findViewById(R.id.linkpaidrec)
        user_search = findViewById(R.id.user_search)
        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = commonadapterone( userArrayList)
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

        EventchangeListener()
    }
    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Campus to Corporate - Paid").orderBy("uname", Query.Direction.ASCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                    if(dc.type== DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(commonadapteronedata::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }
}