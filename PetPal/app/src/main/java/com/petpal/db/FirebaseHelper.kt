package com.petpal.db

import android.util.Log
import com.google.firebase.database.*

class FirebaseHelper {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://petpal-beb4e-default-rtdb.europe-west1.firebasedatabase.app")
    private val petsRef: DatabaseReference = database.getReference("pets")

    // Add new Pet to Firebase
    fun addNewPet(pet: Pet, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        val petId = petsRef.push().key
        Log.d("FirebaseHelper", "Generated pet ID: $petId")
        petId?.let {
            petsRef.child(it).setValue(pet).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(it) // Return the petId on success
                } else {
                    onFailure("Failed to add pet: ${task.exception?.message}")
                }
            }
        } ?: onFailure("Failed to generate pet ID")
    }

    // Fetch all Pets from Firebase
    fun fetchAllPets(onSuccess: (List<Pet>) -> Unit, onFailure: (String) -> Unit) {
        petsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val petsList = mutableListOf<Pet>()
                for (petSnapshot in snapshot.children) {
                    val pet = petSnapshot.getValue(Pet::class.java)
                    pet?.let { petsList.add(it) }
                }
                onSuccess(petsList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure("Failed to fetch pets: ${error.message}")
            }
        })
    }

    // Fetch a specific pet by its ID
    fun fetchPetById(petId: String, onSuccess: (Pet) -> Unit, onFailure: (String) -> Unit) {
        petsRef.child(petId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pet = snapshot.getValue(Pet::class.java)
                if (pet != null) {
                    onSuccess(pet)
                } else {
                    onFailure("Pet not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure("Failed to fetch pet: ${error.message}")
            }
        })
    }

    // Update Pet's medical record
    fun addMedicalRecord(petId: String, medicalRecord: MedicalRecord, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val medicalRecordsRef = petsRef.child(petId).child("medicalRecords")
        val recordId = medicalRecordsRef.push().key
        recordId?.let {
            medicalRecordsRef.child(it).setValue(medicalRecord).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure("Failed to add medical record: ${task.exception?.message}")
                }
            }
        } ?: onFailure("Failed to generate medical record ID")
    }

    // Delete Pet from Firebase
    fun deletePet(petId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        petsRef.child(petId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onFailure("Failed to delete pet: ${task.exception?.message}")
            }
        }
    }
}
