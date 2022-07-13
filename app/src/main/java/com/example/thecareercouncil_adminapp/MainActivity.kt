package com.example.thecareercouncil_adminapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import me.relex.circleindicator.CircleIndicator3
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.tasks.OnCompleteListener




class MainActivity : AppCompatActivity() {
    private var cardlist = mutableListOf<Int>()
    private lateinit var myAdapter: ViewPagerAdapter
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newmessage()

        val dl : DrawerLayout = findViewById(R.id.drawer_layout)
        val nav: NavigationView = findViewById(R.id.navigation_view)

        val nbtn: ImageView = findViewById(R.id.navbtn)


        toggle = ActionBarDrawerToggle(this, dl, R.string.open, R.string.close )

        dl.addDrawerListener(toggle)
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)


        toggle.syncState()

        nbtn.setOnClickListener{
            dl.openDrawer(nav)
        }
        nav.setNavigationItemSelectedListener {

            if(it.itemId == R.id.contactnav)
            {
                val intent = Intent(this, userdetails::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.chatreqnav)
            {
                val intent = Intent(this, chat_req::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.chatnav)
            {
                val intent = Intent(this, chat::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.pendingnav)
            {
                val intent = Intent(this, view_pending::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.completednav)
            {
                val intent = Intent(this, view_completed::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.resquenav)
            {
                val intent = Intent(this, view_res_query::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.resfornav)
            {
                val intent = Intent(this, view_res_req_form::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.resdocnav)
            {
                val intent = Intent(this, view_res_req_doc::class.java)
                startActivity(intent)
                true
            }

            else if(it.itemId==R.id.jobappliqnav)
            {
                val intent = Intent(this, view_jobappli_que::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.jobapplirnav)
            {
                val intent = Intent(this, view_jobappli_form::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.opbquerynav)
            {
                val intent = Intent(this, opb_que::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.introcards)
            {
                val intent = Intent(this, uploadcards::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.opbreqnav)
            {
                val intent = Intent(this, opb_req::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.logoutnav)
            {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                finish()
                startActivity(intent)
                true
            }

            else if(it.itemId==R.id.cccreqnav)
            {
                val intent = Intent(this, view_ccc_req::class.java)
                startActivity(intent)
                true
            }

            else if(it.itemId==R.id.workshopreqnav)
            {
                val intent = Intent(this, view_workshop::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.wsuploadnav)
            {
                val intent = Intent(this, upload_workshop::class.java)
                startActivity(intent)
                true
            }
            else if(it.itemId==R.id.newsuploadnav)
            {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, upload_news::class.java)
                startActivity(intent)
                true
            }
            else
            {
                true

            }

        }

        postToList()

        myAdapter = ViewPagerAdapter(cardlist)

        val vp : ViewPager2 = findViewById(R.id.vp)
        vp.adapter = myAdapter
        vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        var page=0


        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(vp)


    }

    private fun addToList(card: Int){
        cardlist.add(card)
    }

    private fun postToList()
    {
        addToList(R.drawable.done)
        addToList(R.drawable.dtwo)
        addToList(R.drawable.dthree)
        addToList(R.drawable.dfour)
        addToList(R.drawable.dfive)
        addToList(R.drawable.dsix)
        addToList(R.drawable.dseven)
        addToList(R.drawable.deight)
        addToList(R.drawable.dnine)
        addToList(R.drawable.dten)

        addToList(R.drawable.deleven)
        addToList(R.drawable.dtwelve)
        addToList(R.drawable.dthirteen)
        addToList(R.drawable.dfourteen)
        addToList(R.drawable.dfifteen)
        addToList(R.drawable.dsixteen)
        addToList(R.drawable.dseventeen)

    }

    fun newmessage()
    { val firestore= FirebaseFirestore.getInstance()
        firestore.collection("pendingmessages").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    val list: MutableList<String> = ArrayList()
                    for (document in task.result) {
                        list.add(document.id)
                        list.add("\n")
                    }
                    if(!list.isEmpty())
                    {var names: StringBuffer = StringBuffer()
                        for(i in list)
                        {
                            names.append(i)
                        }
                        calldialog(names.toString())
                    Log.d(TAG, list.toString())}
                    else
                    {
                        calldia()
                    }
                } else {
                    calldia()
                }
            })
    }

    fun calldialog(a:String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Messages")
        builder.setMessage("You have new messages from:\n\n"+a)
        builder.setPositiveButton("DISMISS"){dialog, which ->

            val firestore= FirebaseFirestore.getInstance()
            firestore.collection("pendingmessages").get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {

                        for (document in task.result) {
                            firestore.collection("pendingmessages").document(document.id)
                                .delete()
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                        }
                        dialog.dismiss()

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                })

        }
        builder.show()
    }
    fun calldia()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No new Messages")
        builder.setMessage("You have no new chat messages.")
        builder.setPositiveButton("OK"){dialog, which ->
            dialog.dismiss()
        }
        builder.show()

    }
}