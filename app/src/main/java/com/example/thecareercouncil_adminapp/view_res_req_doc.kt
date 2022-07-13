package com.example.thecareercouncil_adminapp

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.example.thecareercouncil_adminapp.databinding.ActivityViewResReqDocBinding
import com.example.thecareercouncil_adminapp.databinding.ActivityViewWorkshopBinding
import com.google.firebase.firestore.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.sql.Timestamp

class view_res_req_doc : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var user_search: SearchView
    private lateinit var userArrayList: ArrayList<commonadaptertwodata>
    private lateinit var myAdapter: commonadaptertwo
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityViewResReqDocBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_res_req_doc)

        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)

        binding = ActivityViewResReqDocBinding.inflate(layoutInflater)
        rv = findViewById(R.id.linkpaidrec)
        user_search = findViewById(R.id.user_search)
        rv.layoutManager = GridLayoutManager(this,1)
        rv.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = commonadaptertwo( userArrayList)
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

            myAdapter.setonitemclicklis(object : commonadaptertwo.onitemclicklis{
                override fun onitemclicklisf(id: String) {

                  val uri:Uri= Uri.parse(id)
                    disp(uri)

                }

            })


        EventchangeListener()
    }
    private fun EventchangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Resume Upload File- Paid").orderBy("uname", Query.Direction.ASCENDING).
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
                        userArrayList.add(dc.document.toObject(commonadaptertwodata::class.java))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }

    fun disp(uri:Uri)
    {
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        ContextCompat.startActivity(this, browserIntent, null)
    }


}