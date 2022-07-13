package com.example.thecareercouncil_adminapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class Formdetails : AppCompatActivity() {
    private lateinit var deets: TextView
    private lateinit var abc: resformdataclass
    private lateinit var sb:StringBuffer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formdetails)
        deets=findViewById(R.id.resdata)
        sb= StringBuffer()
        val id:String = intent.getStringExtra("id").toString()
        calldb(id)


    }
    fun calldb(id:String)
    {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("ResForm").document(id)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            abc = documentSnapshot.toObject(resformdataclass::class.java)!!
            sb.append(abc.data.toString())
            disp(sb.toString())
        }.addOnFailureListener { exception ->
            sb.append("Error in getting documents.")
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            disp(sb.toString())
        }
    }
    fun disp(a:String)
    {
       deets.text=a
    }
}