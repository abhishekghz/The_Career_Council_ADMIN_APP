package com.example.thecareercouncil_adminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class view_res_query : AppCompatActivity() {


    private lateinit var rv: RecyclerView

    private lateinit var nslist: ArrayList<query_data_class>
    private lateinit var myAdapter: query_adapter
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_res_query)

        rv = findViewById(R.id.resrecv)

        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(false)

        nslist = arrayListOf()

        myAdapter = query_adapter( nslist)
        rv.adapter = myAdapter

        EventchangeListener()
    }
    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("resque").orderBy("created", Query.Direction.DESCENDING).
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
                        nslist.add(dc.document.toObject(query_data_class::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }
}