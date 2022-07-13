package com.example.thecareercouncil_adminapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class uploadcards : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var uploadImage: ImageView

    lateinit var dwuri: Uri

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var fstore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadcards)
        fstore = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference
        val btn_choose_image: Button = findViewById(R.id.btn_choose_image)
        val a: Button=findViewById(R.id.uploadchat)
        val b: Button=findViewById(R.id.uploadres)
        val c: Button=findViewById(R.id.uploadjob)
        val d: Button=findViewById(R.id.uploadlinkedin)
        val e: Button=findViewById(R.id.uploadcar)
        val f: Button=findViewById(R.id.uploadcam)
        val g: Button=findViewById(R.id.uploadwshop)
        val h: Button=findViewById(R.id.uploadqr)

        uploadImage= findViewById(R.id.imgpreview)
        btn_choose_image.setOnClickListener { launchGallery() }

        h.setOnClickListener {
            if(filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading")
                progressDialog.setMessage("Please wait while the QR code is being uploaded")
                progressDialog.show()
                val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

                ref!!.putFile(filePath!!).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    ref.downloadUrl
                }.addOnCompleteListener {task->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        progressDialog.dismiss()
                        disp(downloadUri.toString(),"qr")

                    } else {
                        // Handle failures
                        progressDialog.dismiss()
                        disp("Hello","qr")
                        // ...
                    }
                }


            }
            else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

        a.setOnClickListener {

            if(filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading")
                progressDialog.setMessage("Please wait while the details are being uploaded")
                progressDialog.show()
                val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

                ref!!.putFile(filePath!!).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    ref.downloadUrl
                }.addOnCompleteListener {task->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        progressDialog.dismiss()
                        disp(downloadUri.toString(),"chat")

                    } else {
                        // Handle failures
                        progressDialog.dismiss()
                        disp("Hello","chat")
                        // ...
                    }
                }


            }
            else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }
        b.setOnClickListener {if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"resume")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","resume")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }  }
        c.setOnClickListener { if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"jobsearch")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","jobsearch")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        } }
        d.setOnClickListener { if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"linkedin")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","linkedin")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        } }
        e.setOnClickListener { if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"career")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","career")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        } }
        f.setOnClickListener { if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"campus")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","campus")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        } }
        g.setOnClickListener { if(filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.setMessage("Please wait while the details are being uploaded")
            progressDialog.show()
            val ref = storageReference?.child("cards/" + UUID.randomUUID().toString())

            ref!!.putFile(filePath!!).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    progressDialog.dismiss()
                    disp(downloadUri.toString(),"workshop")

                } else {
                    // Handle failures
                    progressDialog.dismiss()
                    disp("Hello","workshop")
                    // ...
                }
            }


        }
        else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        } }
    }
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                uploadImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    fun disp(a:String,b: String){

        val anh = hashMapOf("img" to a)


        fstore.collection("cards").document(b)
            .set(anh)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                Toast.makeText(this, "New Details Uploaded in the Database", Toast.LENGTH_LONG).show()}
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}