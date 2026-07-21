package com.mawkib.abbaskafeel.ui.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mawkib.abbaskafeel.data.SampleData
import com.mawkib.abbaskafeel.model.Comment
import com.mawkib.abbaskafeel.model.Post
import com.mawkib.abbaskafeel.model.UserRole
import java.util.UUID

class FeedViewModel : ViewModel() {

    val posts = mutableStateListOf<Post>().apply {
        addAll(SampleData.initialPosts())
    }

    fun addPost(text: String, authorName: String = "زائر", role: UserRole = UserRole.VISITOR) {
        if (text.isBlank()) return
        posts.add(
            0,
            Post(
                id = UUID.randomUUID().toString(),
                authorName = authorName,
                authorRole = role,
                text = text.trim()
            )
        )
    }

    fun toggleLike(postId: String) {
        val index = posts.indexOfFirst { it.id == postId }
        if (index == -1) return
        val current = posts[index]
        posts[index] = current.copy(
            likedByMe = !current.likedByMe,
            likeCount = if (current.likedByMe) current.likeCount - 1 else current.likeCount + 1
        )
    }

    fun addComment(postId: String, text: String, authorName: String = "زائر") {
        if (text.isBlank()) return
        val index = posts.indexOfFirst { it.id == postId }
        if (index == -1) return
        val current = posts[index]
        val updatedComments = current.comments + Comment(
            id = UUID.randomUUID().toString(),
            authorName = authorName,
            text = text.trim()
        )
        posts[index] = current.copy(comments = updatedComments)
    }

    fun deletePost(postId: String) {
        posts.removeAll { it.id == postId }
    }
}
