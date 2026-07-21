package com.mawkib.abbaskafeel.ui.live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mawkib.abbaskafeel.R
import com.mawkib.abbaskafeel.model.LiveStream

@Composable
fun LiveScreen(
    isAdmin: Boolean = true,
    viewModel: LiveViewModel = viewModel()
) {
    var selectedStream by remember { mutableStateOf<LiveStream?>(null) }
    var showAddForm by remember { mutableStateOf(false) }
    var titleInput by remember { mutableStateOf("") }
    var urlInput by remember { mutableStateOf("") }

    run {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.live_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )

            selectedStream?.let { stream ->
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    VideoPlayer(url = stream.url)
                    Text(
                        text = stream.title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }
            }

            if (isAdmin) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                    if (showAddForm) {
                        OutlinedTextField(
                            value = titleInput,
                            onValueChange = { titleInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(stringResource(R.string.live_title_hint)) }
                        )
                        OutlinedTextField(
                            value = urlInput,
                            onValueChange = { urlInput = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            placeholder = { Text(stringResource(R.string.live_link_hint)) }
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = {
                                viewModel.addStream(titleInput, urlInput)
                                titleInput = ""
                                urlInput = ""
                                showAddForm = false
                            }) {
                                Text(stringResource(R.string.add))
                            }
                        }
                    } else {
                        Button(onClick = { showAddForm = true }) {
                            Text(stringResource(R.string.add_live_link))
                        }
                    }
                }
            }

            if (viewModel.streams.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_live_streams),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(viewModel.streams, key = { it.id }) { stream ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    IconButton(onClick = { selectedStream = stream }) {
                                        Icon(
                                            imageVector = Icons.Filled.PlayCircle,
                                            contentDescription = stringResource(R.string.watch_live),
                                            tint = MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                    Column {
                                        Text(text = stream.title, style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            text = stream.addedBy,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                                if (isAdmin) {
                                    IconButton(onClick = {
                                        if (selectedStream?.id == stream.id) selectedStream = null
                                        viewModel.removeStream(stream.id)
                                    }) {
                                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
