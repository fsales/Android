package br.com.e_aluno.firebase.firestone

import android.util.Log
import br.com.e_aluno.model.Noticia
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class NoticiasFirestone {

    companion object {
        val instance: NoticiasFirestone by lazy {
            NoticiasFirestone()
        }
    }

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun noticias(onListen: (ArrayList<Noticia>) -> Unit): ListenerRegistration =
            instance.collection("noticias").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val items = arrayListOf<Noticia>()
                querySnapshot!!.documents.forEach {
                    items.add(it.toObject(Noticia::class.java)!!)
                }

                onListen(items)
            }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

}
