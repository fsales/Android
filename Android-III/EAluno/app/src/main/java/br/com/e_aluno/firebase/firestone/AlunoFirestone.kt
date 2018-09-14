package br.com.e_aluno.firebase.firestone

import br.com.e_aluno.firebase.Auth
import br.com.e_aluno.model.Aluno
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.lang.NullPointerException
import java.util.*

class AlunoFirestone {

    companion object {
        val instance: AlunoFirestone by lazy {
            AlunoFirestone()
        }
    }

    private val PATH_ALUNOS: String by lazy {
        "alunos"
    }

    private val instance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    private val currentDocRef: DocumentReference
        get() = instance.document("${PATH_ALUNOS}/${Auth.instance.uid()}")
                ?: throw  NullPointerException("UUID null")


    fun criar(aluno: Aluno,
              onComplete: () -> Unit,
              onError: (exception: Exception?) -> Unit) {
        currentDocRef.get().addOnSuccessListener { documentSnapshot ->
            aluno.uuid = UUID.randomUUID().toString()
            currentDocRef.set(aluno, SetOptions.merge()).addOnCompleteListener { onComplete() }

        }.addOnFailureListener { exception ->
            onError(exception)
        }

    }


    fun getAluno(onComplete: (Aluno) -> Unit,
                     onError: (exception: Exception?) -> Unit) {
        currentDocRef.get()
                .addOnSuccessListener {
                    it.toObject(Aluno::class.java)?.let {
                        onComplete(it)
                    }
                }.addOnFailureListener {
                    onError(it)
                }
    }

}