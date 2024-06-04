package com.pkmaether.aether.data.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthClient {
    val authInstance: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
}