package com.pkmaether.aether.data.firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreClient {
    val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}