package com.devnet.vault.utility

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date
import java.util.Locale

class Devnet(appContext: Context) {
    private var currentContext :Context = appContext

    fun shareDownloadLink(downloadLink: String ) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Click on link to download Safer: $downloadLink")
            type = "text/plain"
        }
        try {
            currentContext.startActivity(Intent.createChooser(shareIntent, "Share link via").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } catch (e: Exception) {
            Toast.makeText(
                currentContext,
                "No application available to share the link.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun firebaseUserLogin(email: String, password: String, auth: FirebaseAuth, onSuccess: () -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(currentContext, "Login successful!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(currentContext, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun firebaseUserRegister(email: String, password: String, auth: FirebaseAuth, onSuccess: () -> Unit,onFailure: () -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userData = mapOf(
                        "Email" to email,
                        "Password" to password,
                        "Registration Date" to SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    )
                    firebaseStoreData(auth, FirebaseDatabase.getInstance().reference, "Profile", userData, onSuccess,onFailure)
                } else {
                    Toast.makeText(currentContext, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun <K, V> firebaseStoreData(auth: FirebaseAuth, dbRef: DatabaseReference, menu: String, userData: Map<K, V>,onSuccess: () -> Unit,onFailure: () -> Unit) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            val pathRef = dbRef.child(currentUID).child(menu)
            pathRef.setValue(userData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure()
                    }
                }
        } else {
            onFailure()
        }
    }

}
