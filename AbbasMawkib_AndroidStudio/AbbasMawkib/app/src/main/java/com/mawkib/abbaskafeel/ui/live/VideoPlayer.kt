package com.mawkib.abbaskafeel.ui.live

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mawkib.abbaskafeel.data.VideoUtils

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayer(url: String, modifier: Modifier = Modifier) {
    val embedUrl = VideoUtils.toEmbeddableUrl(url)

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl(embedUrl)
            }
        },
        update = { webView ->
            webView.loadUrl(embedUrl)
        }
    )
}
