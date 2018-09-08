package br.com.e_aluno.firebase.firestone

import br.com.e_aluno.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioFirestone {

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val currentUserDocRef: DocumentReference
        get() = instance.document("usuarios/${auth.uid}")
                ?: throw  NullPointerException("UID está nulo")


    fun initUsuarioCorrente(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {
                onComplete()
            } else {

                val novoUsuario = Usuario().apply {
                    auth.currentUser?.let { currentUser ->
                        this.email = currentUser.email ?: ""
                        this.nome = currentUser.displayName ?: ""

                    }
                }

                currentUserDocRef.set(novoUsuario).addOnCompleteListener { onComplete() }
            }
        }

    }


}