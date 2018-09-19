package br.com.e_aluno.firebase.firestone

import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FCMFirebase {
    companion object {
        val instance: FCMFirebase by lazy {
            FCMFirebase()
        }
    }

    private val USUARIOS = "usuarios"

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("${USUARIOS}/${Auth.instance.uid()
                ?: throw NullPointerException("UID is null.")}")

    //region FCM
    fun getFCMRegistrationTokens(onComplete: (tokens: MutableList<String>) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(Usuario::class.java)!!
            onComplete(user.registrationTokens!!)
        }
    }

    fun setFCMRegistrationTokens(registrationTokens: MutableList<String>) {
        currentUserDocRef.update(mapOf("registrationTokens" to registrationTokens))
    }
    //endregion FCM

}