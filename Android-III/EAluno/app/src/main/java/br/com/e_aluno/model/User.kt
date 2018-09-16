package br.com.e_aluno.model

data class User(
        var id: String,
        var name: String,
        var imageUrl: String,
        var profileUrl: String)


abstract class Update(
        val updateType: String,
        val updateUser: User,
        val updateTime: String) {

    class TYPE {
        companion object {
            val NEW_FRIEND = "friend"
            val COMMENT = "comment"
            val READ_STATUS = "readstatus"
            val USER_STATUS = "userstatus"
            val REVIEW = "review"
        }
    }
}

data class ReviewUpdate(
        val user: User,
        val updatedAt: String,
        val rating: String
) : Update(Update.TYPE.REVIEW, user, updatedAt)

data class NewFriendUpdate(
        val user: User,
        val updatedAt: String,
        val newFriendImageUrl: String,
        val newFriendName: String
) : Update(Update.TYPE.NEW_FRIEND, user, updatedAt)

