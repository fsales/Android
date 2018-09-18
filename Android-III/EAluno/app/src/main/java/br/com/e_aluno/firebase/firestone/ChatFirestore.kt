package br.com.e_aluno.firebase.firestone

import android.content.Context
import android.util.Log
import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ChatFirestore {

    companion object {
        val instance: ChatFirestore by lazy {
            ChatFirestore()
        }
    }

    private val USUARIOS = "usuarios"
    private val MENSAGENS = "mensagens"
    private val CANAIS_CHAT = "canaisChat"
    private val CANAIS_CHAT_PARTICIPANTES = "canaisChatParticipantes"

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("${USUARIOS}/${Auth.instance.uid()
                ?: throw NullPointerException("UID is null.")}")

    private val chatChannelsCollectionRef = firestoreInstance.collection(CANAIS_CHAT)


    fun sendMessage(mensagem: IMensagem,
                    idCanal: String,
                    onComplete: () -> Unit,
                    onErro: (Exception?) -> Unit?) {
        chatChannelsCollectionRef.document(idCanal)
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

        currentUserDocRef.collection(CANAIS_CHAT_PARTICIPANTES).document(otherUserUid).get().addOnSuccessListener {
            if (it.exists()) {
                onComplete(it["idCanal"] as String)
                return@addOnSuccessListener
            }


            val idUsuarioCorrente = Auth.instance.uid()
            val novaCanal = chatChannelsCollectionRef.document()

            novaCanal.set(Canal(mutableListOf(idUsuarioCorrente, otherUserUid)))
            currentUserDocRef
                    .collection(CANAIS_CHAT_PARTICIPANTES)
                    .document(otherUserUid)
                    .set(mapOf("idCanal" to novaCanal.id))

            firestoreInstance.collection(USUARIOS).document(otherUserUid)
                    .collection(CANAIS_CHAT_PARTICIPANTES)
                    .document(idUsuarioCorrente!!)
                    .set(mapOf("idCanal" to novaCanal.id))
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
                        items.add(if (it["type"] == TipoMensagem.TEXTO) {
                            it.toObject(MensagemTexto::class.java)!!
                        } else {
                            it.toObject(MensagemImagem::class.java)!!
                        })

                    }
                    onListen(items)
                }
    }

}