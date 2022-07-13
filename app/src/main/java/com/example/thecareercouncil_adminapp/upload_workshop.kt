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
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.*

class upload_workshop : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var uploadImage: ImageView

    lateinit var dwuri: Uri

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var fstore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_workshop)
        fstore = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference
    val btn_choose_image: Button = findViewById(R.id.btn_choose_image)
        btn_choose_image.setOnClickListener { launchGallery() }
        val title : EditText = findViewById(R.id.ntitle)
        val desc : EditText = findViewById(R.id.ndesc)
        val price : EditText = findViewById(R.id.wprice)
        val btn : Button = findViewById(R.id.uploadw)

        uploadImage= findViewById(R.id.image_preview)

        btn.setOnClickListener {

            val ti = title.text.toString().trim()
            val d= desc.text.toString().trim()
            val p = price.text.toString().trim()

            if(filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading")
                progressDialog.setMessage("Please wait while the details are being uploaded")
                progressDialog.show()
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())

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
                        disp(downloadUri.toString(),ti,d,p)

                    } else {
                        // Handle failures
                        progressDialog.dismiss()
                        disp("Hello",ti,d,p)
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
   fun disp(a:String,ti:String,d:String,p:String){



       val anh = hashMapOf(
           "desc" to d,
           "price" to p,
           "img" to a,
           "title" to ti
       )

       fstore.collection("workshops").document()
           .set(anh)
           .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
               Toast.makeText(this, "New Workshop Uploaded in the Database", Toast.LENGTH_LONG).show()}
           .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

       val intent = Intent(this, MainActivity::class.java)
       finish()
       startActivity(intent)
   }

}

