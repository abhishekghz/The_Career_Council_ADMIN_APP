package com.example.thecareercouncil_adminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class view_completed : AppCompatActivity() {
    private lateinit var rv: RecyclerView

    private lateinit var nslist: ArrayList<completed_data_class>
    private lateinit var myAdapter: completed_adapter
    
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_completed)
        rv = findViewById(R.id.comprecv)

        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(false)

        nslist = arrayListOf()

        myAdapter = completed_adapter( nslist)
        rv.adapter = myAdapter
        EventchangeListener()
    }
    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("completed")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        nslist.add(dc.document.toObject(completed_data_class::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }
}