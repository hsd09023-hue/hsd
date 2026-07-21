package com.mawkib.abbaskafeel.model

data class Comment(
    val id: String,
    val authorName: String,
    val text: String
)

data class Post(
    val id: String,
    val authorName: String,
    val authorRole: UserRole,
    val text: String,
    val imageUrl: String? = null,
    val likeCount: Int = 0,
    val likedByMe: Boolean = false,
    val comments: List<Comment> = emptyList()
)

enum class UserRole {
    VISITOR, SERVANT, ADMIN
}
