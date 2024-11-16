package com.petpal.db

import android.util.Log
import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseHelper {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://petpal-beb4e-default-rtdb.europe-west1.firebasedatabase.app")
    private val petsRef: DatabaseReference = database.getReference("pets")

    // Save pet to Firebase Realtime Database
    suspend fun savePet(pet: Pet) = suspendCancellableCoroutine<Unit> { continuation ->
        petsRef.child(pet.id.toString()).setValue(pet)
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    // Get pet from Firebase by id
    suspend fun getPet(id: Int): Pet? = suspendCancellableCoroutine { continuation ->
        petsRef.child(id.toString()).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val pet = snapshot.getValue(Pet::class.java)
                    continuation.resume(pet)
                } else {
                    continuation.resume(null) // Pet not found
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    // Get all pets from Firebase
    suspend fun getPets(): List<Pet> = suspendCancellableCoroutine { continuation ->
        petsRef.get()
            .addOnSuccessListener { snapshot ->
                val pets = mutableListOf<Pet>()
                for (childSnapshot in snapshot.children) {
                    val pet = childSnapshot.getValue(Pet::class.java)
                    pet?.let { pets.add(it) }
                }
                continuation.resume(pets)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    // Delete pet from Firebase
    suspend fun deletePet(id: Int) = suspendCancellableCoroutine<Unit> { continuation ->
        petsRef.child(id.toString()).removeValue()
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}
