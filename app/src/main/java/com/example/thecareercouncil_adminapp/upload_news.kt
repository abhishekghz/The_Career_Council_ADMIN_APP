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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*


class upload_news : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var uploadImage: ImageView

    lateinit var dwuri: Uri

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var fstore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_news)
        fstore = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference
        val btn_choose_image: Button = findViewById(R.id.btn_choose_image)
        btn_choose_image.setOnClickListener { launchGallery() }

        val title: EditText = findViewById(R.id.ntitle)
        val desc: EditText = findViewById(R.id.ndesc)
        uploadImage = findViewById(R.id.image_preview)
        val btn: Button = findViewById(R.id.uploadw)

        btn.setOnClickListener {
            val t = title.text.toString().trim()
            val d = desc.text.toString().trim()
            val g = Timestamp.now()
            if(filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading")
                progressDialog.setMessage("Please wait while the details are being uploaded")
                progressDialog.show()
                val ref = storageReference?.child("news/" + UUID.randomUUID().toString())

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
                        disp(downloadUri.toString(),t,d,g)

                    } else {
                        // Handle failures
                        progressDialog.dismiss()
                        disp("Hello",t,d,g)
                        // ...
                    }
                }


            }
            else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }

        }
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
            if (data == null || data.data == null) {
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

    fun disp(a: String, ti: String, d: String, g: Timestamp) {
        val ah = hashMapOf(
            "desc" to d,
            "img" to a,
            "created" to g,
            "title" to ti
        )

        fstore.collection("news").document()
            .set(ah)
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }


        Toast.makeText(this, "New Update added to Newsfeed", Toast.LENGTH_LONG).show()

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}