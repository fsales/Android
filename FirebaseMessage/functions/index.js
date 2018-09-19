const functions = require('firebase-functions');
 
const admin = require('firebase-admin');
admin.initializeApp();
 
exports.notifyNewMessage = functions.firestore
    .document('canaisChat/{canais}/mensagens/{mensagens}')
    .onCreate((docSnapshot, context) => {
        const message = docSnapshot.data();
        const recipientId = message['recipientId'];
        const senderName = message['nome'];
 
        return admin.firestore().doc('usuarios/' + recipientId).get().then(userDoc => {
            const registrationTokens = userDoc.get('registrationTokens')
 
            const notificationBody = (message['type'] === "TEXTO") ? message['texto'] : "You received a new image message."
            const payload = {
                notification: {
                    title: senderName + " sent you a message.",
                    body: notificationBody,
                    clickAction: "ChatActivity"
                },
                data: {
                    USER_NAME: senderName,
                    USER_ID: message['senderId']
                }
            }
 
            return admin.messaging().sendToDevice(registrationTokens, payload).then( response => {
                const stillRegisteredTokens = registrationTokens
 
                response.results.forEach((result, index) => {
                    const error = result.error
                    if (error) {
                        const failedRegistrationToken = registrationTokens[index]
                        console.error('blah', failedRegistrationToken, error)
                        if (error.code === 'messaging/invalid-registration-token'
                            || error.code === 'messaging/registration-token-not-registered') {
                                const failedIndex = stillRegisteredTokens.indexOf(failedRegistrationToken)
                                if (failedIndex > -1) {
                                    stillRegisteredTokens.splice(failedIndex, 1)
                                }
                            }
                    }
                })
 
                return admin.firestore().doc("usuarios/" + recipientId).update({
                    registrationTokens: stillRegisteredTokens
                })
            })
        })
    })