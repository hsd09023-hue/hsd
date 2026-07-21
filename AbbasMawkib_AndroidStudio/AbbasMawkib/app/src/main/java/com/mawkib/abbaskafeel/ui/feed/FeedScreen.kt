package com.mawkib.abbaskafeel.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mawkib.abbaskafeel.R
import com.mawkib.abbaskafeel.model.Post

@Composable
fun FeedScreen(
    isAdmin: Boolean = false,
    viewModel: FeedViewModel = viewModel()
) {
    var newPostText by remember { mutableStateOf("") }
    var activeCommentsPost by remember { mutableStateOf<Post?>(null) }

    run {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.feed_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = newPostText,
                    onValueChange = { newPostText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.new_post_hint)) },
                    minLines = 2
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        viewModel.addPost(newPostText)
                        newPostText = ""
                    }) {
                        Text(stringResource(R.string.publish))
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.posts, key = { it.id }) { post ->
                    PostCard(
                        post = post,
                        isAdmin = isAdmin,
                        onLikeClick = { viewModel.toggleLike(post.id) },
                        onCommentsClick = { activeCommentsPost = post },
                        onDeleteClick = { viewModel.deletePost(post.id) }
                    )
                }
                item { Box(modifier = Modifier.padding(bottom = 24.dp)) }
            }
        }
    }

    val currentPost = activeCommentsPost
    if (currentPost != null) {
        val liveVersion = viewModel.posts.firstOrNull { it.id == currentPost.id } ?: currentPost
        CommentsSheet(
            post = liveVersion,
            onDismiss = { activeCommentsPost = null },
            onSendComment = { text -> viewModel.addComment(liveVersion.id, text) }
        )
    }
}

@Composable
private fun CommentsSheet(
    post: Post,
    onDismiss: () -> Unit,
    onSendComment: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var commentText by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.comments),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(post.comments) { comment ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = comment.authorName,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = comment.text, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(stringResource(R.string.add_comment_hint)) }
                )
                Button(
                    onClick = {
                        onSendComment(commentText)
                        commentText = ""
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(stringResource(R.string.send))
                }
            }
        }
    }
}
