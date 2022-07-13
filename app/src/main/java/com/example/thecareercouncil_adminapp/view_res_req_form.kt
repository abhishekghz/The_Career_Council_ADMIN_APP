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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecareercouncil_adminapp.databinding.ActivityViewResReqDocBinding
import com.example.thecareercouncil_adminapp.databinding.ActivityViewResReqFormBinding
import com.google.firebase.firestore.*

class view_res_req_form : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var user_search: SearchView
    private lateinit var userArrayList: ArrayList<commonadaptertwodata>
    private lateinit var myAdapter: commonadaptertwo
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityViewResReqFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_res_req_form)
        binding = ActivityViewResReqFormBinding.inflate(layoutInflater)
        rv = findViewById(R.id.linkpaidrec)
        user_search = findViewById(R.id.user_search)
        rv.layoutManager = GridLayoutManager(this, 1)
        rv.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = commonadaptertwo(userArrayList)
        rv.adapter = myAdapter


        val searchIcon =
            binding.userSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val textView =
            binding.userSearch.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        user_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter.filter(newText)
                return false
            }

        })

        myAdapter.setonitemclicklis(object : commonadaptertwo.onitemclicklis {
            override fun onitemclicklisf(id: String) {
                disp(id)

            }

        })


        EventchangeListener()
    }

    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Resume Upload Form- Paid").orderBy("uname", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            userArrayList.add(dc.document.toObject(commonadaptertwodata::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }
            )
    }

    fun disp(id: String) {
        val intent = Intent(this, Formdetails::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}


