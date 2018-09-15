package br.com.e_aluno.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Storage {

    companion object {
        val INSTANCE: Storage by lazy {
            Storage()
        }
    }

    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }


    private val currentUserRef: StorageReference
        get() = storageInstance.reference
                .child(FirebaseAuth.getInstance().currentUser?.uid
                        ?: throw NullPointerException("UID null."))

    fun uploadFoto(imagemBytes: ByteArray,
                   nome: String,
                   onComplete: (imagePath: String) -> Unit,
                   onError: (exception: Exception?) -> Unit) {

        val ref = currentUserRef.child("profilePictures/${nome + Auth.instance.uid()}")
        ref.putBytes(imagemBytes)
                .addOnSuccessListener {
                    onComplete(ref.path)
                }.addOnFailureListener {
                    onError(it)
                }
    }

    fun pathToReference(path: String) = storageInstance.getReference(path)
}