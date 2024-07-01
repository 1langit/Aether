package com.pkmaether.aether.data.repositories

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.pkmaether.aether.data.PrefManager
import com.pkmaether.aether.data.firebase.FirebaseAuthClient
import com.pkmaether.aether.data.firebase.FirestoreClient
import com.pkmaether.aether.data.models.User

class UserRepository(context: Context) {

    private val usersCollection: CollectionReference
        get() = FirestoreClient.firestoreInstance.collection("users")

    private val firebaseAuth: FirebaseAuth
        get() = FirebaseAuthClient.authInstance

    private val prefManager = PrefManager.getInstance(context)

    fun registerUser(
        user: User,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                user.uid = it.user!!.uid
                usersCollection.document(it.user!!.uid).set(user)
                    .addOnSuccessListener {
                        saveUserPreference(user)
                        onSuccess()
                    }
                    .addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                usersCollection.document(it.user!!.uid).get()
                    .addOnSuccessListener { user ->
                        saveUserPreference(user.toObject(User::class.java)!!)
                        onSuccess()
                    }.addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }
    }

    fun logoutUser() {
        firebaseAuth.signOut()
        prefManager.clear()
    }

    private fun saveUserPreference(user: User) {
        prefManager.saveUid(user.uid)
        prefManager.saveEmail(user.email)
        prefManager.saveCompanyName(user.companyName)
        prefManager.saveIndustryType(user.industryType)
        prefManager.saveAddress(user.address)
        prefManager.savePhone(user.phone)
    }
}