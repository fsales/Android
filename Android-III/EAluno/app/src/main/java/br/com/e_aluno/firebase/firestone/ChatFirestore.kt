package br.com.e_aluno.firebase.firestone

import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.IMensagem
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ChatFirestore {

    private val CHAT = "chatIesb"
    private val CANAIS_CHAT = "canaisChat"

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${Auth.instance.uid()
                ?: throw NullPointerException("UID is null.")}")

    private val chatChannelsCollectionRef = firestoreInstance.collection(CHAT)


    fun sendMessage(mensagem: IMensagem, channelId: String, onErro: (Exception?) -> Unit?) {
        chatChannelsCollectionRef.document(channelId)
                .collection(CHAT)
                .add(mensagem).addOnFailureListener {
                    onErro(it)
                }
    }

    fun chat(otherUserUid: String,
             onComplete: (chatId: String) -> Unit) {

        currentUserDocRef.collection(CANAIS_CHAT).document(otherUserUid).get().addOnSuccessListener {
            if (it.exists()) {
                onComplete(it["channelId"] as String)
                return@addOnSuccessListener
            }
        }
    }

}