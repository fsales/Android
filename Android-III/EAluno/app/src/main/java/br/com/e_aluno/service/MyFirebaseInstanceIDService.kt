package br.com.e_aluno.service

import br.com.e_aluno.firebase.firestone.FCMFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val newRegistrationToken = FirebaseInstanceId.getInstance().token

        if (FirebaseAuth.getInstance().currentUser != null)
            addTokenToFirestore(newRegistrationToken)
    }

    companion object {
        fun addTokenToFirestore(newRegistrationToken: String?) {
            if (newRegistrationToken == null) throw NullPointerException("FCM token está nulo.")

            FCMFirebase.instance.getFCMRegistrationTokens { tokens ->
                if (tokens.contains(newRegistrationToken))
                    return@getFCMRegistrationTokens

                tokens.add(newRegistrationToken)
                FCMFirebase.instance.setFCMRegistrationTokens(tokens)
            }
        }
    }
}