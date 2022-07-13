package com.example.thecareercouncil_adminapp

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecareercouncil_adminapp.databinding.ActivityUserdetailsBinding
import com.example.thecareercouncil_adminapp.databinding.ActivityViewPendingBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*

class view_pending : AppCompatActivity() {
    private lateinit var rv: RecyclerView

    private lateinit var nslist: ArrayList<pending_data_class>
    private lateinit var myAdapter: pending_adapter
    private lateinit var abc: String
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityViewPendingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pending)
        binding = ActivityViewPendingBinding.inflate(layoutInflater)
        rv = findViewById(R.id.pendrecv)

        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(false)

        nslist = arrayListOf()

        myAdapter = pending_adapter( nslist)
        rv.adapter = myAdapter

        myAdapter.setonitemclicklis(object : pending_adapter.onitemclicklis{
            override fun onitemclicklisf(id: String, uname:String, uemail:String, uphone: String, created:String, mssg: String, src: String) {

                callDialog(id,uname,uemail,uphone,created,mssg,src)


        }})

        EventchangeListener()

    }

    private fun callDialog(id: String, uname:String, uemail:String, uphone: String, created:String, mssg: String, src: String) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Move to Completed?")
            builder.setMessage("Are you sure you want to mark this task as completed?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton("Yes") { dialog, which ->
                var fstore: FirebaseFirestore = FirebaseFirestore.getInstance()
                val b = hashMapOf(
                    "uname" to uname,
                    "uemail" to uemail,
                    "uphone" to uphone,
                    "created" to created,
                    "mssg" to mssg,
                    "source" to src,
                    "id" to id

                )

                fstore.collection("completed").document(id)
                    .set(b)
                    .addOnSuccessListener {

                        Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")

                    }
                    .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }


                db.collection("pending").document(id)
                    .delete()
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                Toast.makeText(applicationContext,
                    "Moved to Completed", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

                val i = Intent(this,MainActivity::class.java)
                finish()
                startActivity(i)

            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }


            builder.show()

        }


    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("pending").orderBy("created", Query.Direction.DESCENDING).
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
                        nslist.add(dc.document.toObject(pending_data_class::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }
}