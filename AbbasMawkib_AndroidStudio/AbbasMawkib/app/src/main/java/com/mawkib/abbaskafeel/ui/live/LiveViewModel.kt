package com.mawkib.abbaskafeel.ui.live

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.mawkib.abbaskafeel.model.LiveStream
import java.util.UUID

class LiveViewModel : ViewModel() {

    val streams = mutableStateListOf<LiveStream>()

    fun addStream(title: String, url: String, addedBy: String = "خادم") {
        if (title.isBlank() || url.isBlank()) return
        streams.add(
            0,
            LiveStream(
                id = UUID.randomUUID().toString(),
                title = title.trim(),
                url = url.trim(),
                addedBy = addedBy
            )
        )
    }

    fun removeStream(id: String) {
        streams.removeAll { it.id == id }
    }
}
