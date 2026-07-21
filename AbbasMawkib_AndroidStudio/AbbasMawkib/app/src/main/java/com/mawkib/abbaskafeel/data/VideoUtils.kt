package com.mawkib.abbaskafeel.data

object VideoUtils {

    /**
     * يحوّل رابط يوتيوب عادي أو مختصر إلى رابط embed قابل للعرض داخل WebView.
     * إن لم يكن الرابط رابط يوتيوب، تُعاد نفس القيمة لعرضه كرابط ويب عام.
     */
    fun toEmbeddableUrl(rawUrl: String): String {
        val url = rawUrl.trim()
        val videoId = extractYoutubeId(url)
        return if (videoId != null) {
            "https://www.youtube.com/embed/$videoId?autoplay=1&playsinline=1"
        } else {
            url
        }
    }

    private fun extractYoutubeId(url: String): String? {
        val patterns = listOf(
            Regex("youtu\\.be/([A-Za-z0-9_-]{6,})"),
            Regex("youtube\\.com/watch\\?v=([A-Za-z0-9_-]{6,})"),
            Regex("youtube\\.com/live/([A-Za-z0-9_-]{6,})"),
            Regex("youtube\\.com/embed/([A-Za-z0-9_-]{6,})")
        )
        for (pattern in patterns) {
            val match = pattern.find(url)
            if (match != null) {
                return match.groupValues[1]
            }
        }
        return null
    }
}
