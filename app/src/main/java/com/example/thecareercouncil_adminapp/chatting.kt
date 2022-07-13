package com.example.thecareercouncil_adminapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONException
import org.json.JSONObject

class chatting : AppCompatActivity() {
    private lateinit var sendbtn: ImageView
    private lateinit var sendmessage: EditText
    private lateinit var msg: String
    private lateinit var rv: RecyclerView
    private lateinit var messagelist: ArrayList<Message>
    private lateinit var myAdapter: MessageAdapter
    private lateinit var db: FirebaseFirestore

    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "key=" + "AAAAZxzPKPM:APA91bHuYRUhELS_4utoa-lZeROXKU0BX1XVe4Y_pgd6Hsu-eYjDkhG0uSQISddSe5fWVWy2U4zC1BBmY1EVokWa7eW6WFsRLO-YcZ0iVnHCieGce8ALUqMVNuYE7GnrBo4PuwEu0it2"
    private val contentType = "application/json"

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        val id:String = intent.getStringExtra("id").toString()
        val docname:String = intent.getStringExtra("email").toString()
        msg=docname
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/admin")
        sendbtn = findViewById(R.id.send_btn)
        sendmessage = findViewById(R.id.message)
        var fstore: FirebaseFirestore = FirebaseFirestore.getInstance()

        val uid= FirebaseAuth.getInstance().currentUser?.uid ?: "null"

        rv = findViewById(R.id.mssg_rec)

        rv.layoutManager = GridLayoutManager(this,1)

        rv.setHasFixedSize(false)

        messagelist = arrayListOf()

        myAdapter = MessageAdapter(messagelist)
        rv.adapter = myAdapter


        sendbtn.setOnClickListener {


            val a = hashMapOf(
                "message" to sendmessage.text.toString(),
                "senderId" to uid,
                "time" to Timestamp.now()

            )

            fstore.collection(id).document()
                .set(a)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
            sendmessage.setText("")

            val pms = hashMapOf(
                "message" to "yes")

            fstore.collection(docname).document(docname)
                .set(pms)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
            sendmessage.setText("")

            if (msg.isNotEmpty()) {
                val topic = "/topics/"+id //topic has to match what the receiver subscribed to

                val notification = JSONObject()
                val notifcationBody = JSONObject()

                try {

                    notifcationBody.put("title", "Chat Room")
                    notifcationBody.put("message", "New Message Received!")   //Enter your notification message
                    notification.put("to", topic)
                    notification.put("data", notifcationBody)
                    Log.e("TAG", "try")
                } catch (e: JSONException) {
                    Log.e("TAG", "onCreate: " + e.message)
                }

                sendNotification(notification)

        }

        }
        EventchangeListener(id)
    }


    private fun EventchangeListener(id: String) {
        db = FirebaseFirestore.getInstance()
        db.collection(id).orderBy("time", Query.Direction.ASCENDING).
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
                        messagelist.add(dc.document.toObject(Message::class.java))
                    }
                }
                if(myAdapter.itemCount!=0) {
                    val pos = myAdapter.itemCount - 1
                    rv.smoothScrollToPosition(pos)
                }
                myAdapter.notifyDataSetChanged()
            }
        }
        )
    }

    private fun sendNotification(notification: JSONObject) {
        Log.e("TAG", "sendNotification")
        val jsonObjectRequest = object : JsonObjectRequest(
            FCM_API,
            notification,
            Response.Listener<JSONObject> { response ->
                Log.i("TAG", "onResponse: $response")

            },
            Response.ErrorListener {
                Toast.makeText(this, "Request error", Toast.LENGTH_LONG).show()
                Log.i("TAG", "onErrorResponse: Didn't work")
            }) {

            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }
}