package br.com.e_aluno.firebase.firestone

import android.util.Log
import br.com.e_aluno.model.Banner
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class BannerFirestone {

    companion object {
        val instance: BannerFirestone by lazy {
            BannerFirestone()
        }
    }

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentDocRef: DocumentReference
        get() = instance.document("banners")


    fun getBanners(onListen: (ArrayList<Banner>) -> Unit) {
        val banners = instance.collection("banners").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                return@addSnapshotListener
            }

            val items = arrayListOf<Banner>()
            querySnapshot!!.documents.forEach {
                items.add(it.toObject(Banner::class.java)!!)
            }

            onListen(items)
        }


    }

}