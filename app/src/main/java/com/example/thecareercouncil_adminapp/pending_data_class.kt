package com.example.thecareercouncil_adminapp

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class pending_data_class(var id : String?="12345",var source : String?=null,var uname : String?=null, var uemail : String?=null, var uphone : String?=null, var created : Timestamp?=null, var mssg: String?=null)
