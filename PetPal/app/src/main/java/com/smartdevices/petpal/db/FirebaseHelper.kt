package com.smartdevices.petpal.db

import com.google.firebase.database.*

class FirebaseHelper {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://petpal-beb4e-default-rtdb.europe-west1.firebasedatabase.app")
    private val petsRef: DatabaseReference = database.getReference("pets")


}
