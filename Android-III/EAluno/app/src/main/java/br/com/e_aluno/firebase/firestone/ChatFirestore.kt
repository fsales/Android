package br.com.e_aluno.firebase.firestone

import android.content.Context
import android.util.Log
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Canal
import br.com.e_aluno.model.IMensagem
import br.com.e_aluno.model.Mensagem
import br.com.e_aluno.model.MensagemTexto
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ChatFirestore {

    companion object {
        val instance: ChatFirestore by lazy {
            ChatFirestore()
        }
    }

    private val CHAT = "chatIesb"
    private val MENSAGENS = "mensagens"
    private val CANAIS_CHAT = "canaisChat"

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("usuarios/${Auth.instance.uid()
                ?: throw NullPointerException("UID is null.")}")

    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")


    fun sendMessage(mensagem: IMensagem,
                    channelId: String,
                    onComplete: () -> Unit,
                    onErro: (Exception?) -> Unit?) {
        chatChannelsCollectionRef.document(channelId)
                .collection(MENSAGENS)
                .add(mensagem).addOnCompleteListener {
                    onComplete()
                }
                .addOnFailureListener {
                    onErro(it)
                }
    }

    fun chat(otherUserUid: String,
             onComplete: (chatId: String) -> Unit) {

        currentUserDocRef.collection("engagedChatChannels").document(otherUserUid).get().addOnSuccessListener {
            if (it.exists()) {
                onComplete(it["channelId"] as String)
                return@addOnSuccessListener
            }


            val idUsuarioCorrente = Auth.instance.uid()
            val novaCanal = chatChannelsCollectionRef.document()

            novaCanal.set(Canal(mutableListOf(idUsuarioCorrente, otherUserUid)))
            currentUserDocRef
                    .collection("engagedChatChannels")
                    .document(otherUserUid)
                    .set(mapOf("channelId" to novaCanal.id))

            firestoreInstance.collection("usuarios").document(otherUserUid)
                    .collection("engagedChatChannels")
                    .document(idUsuarioCorrente!!)
                    .set(mapOf("channelId" to novaCanal.id))
            onComplete(novaCanal.id)
        }
    }

    fun addChatMensagemListener(idCanal: String,
                                context: Context,
                                onListen: (ArrayList<Mensagem>) -> Unit): ListenerRegistration {

        return chatChannelsCollectionRef.document(idCanal)
                .collection(MENSAGENS)
                .orderBy("dataHora")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    val items = arrayListOf<Mensagem>()
                    querySnapshot!!.documents.forEach {
                        items.add(it.toObject(MensagemTexto::class.java)!!)
                    }
                    onListen(items)
                }
    }

}