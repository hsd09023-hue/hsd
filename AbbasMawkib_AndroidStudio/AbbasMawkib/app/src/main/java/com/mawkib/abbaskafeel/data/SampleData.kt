package com.mawkib.abbaskafeel.data

import com.mawkib.abbaskafeel.model.Comment
import com.mawkib.abbaskafeel.model.Post
import com.mawkib.abbaskafeel.model.UserRole

object SampleData {

    fun initialPosts(): List<Post> = listOf(
        Post(
            id = "p1",
            authorName = "خادم الموكب",
            authorRole = UserRole.SERVANT,
            text = "بالتوفيق لكل الخدام في تجهيز موكب العباس كفيل زينب عليهما السلام هذا الموسم، أجركم عند الله.",
            likeCount = 12,
            comments = listOf(
                Comment("c1", "زائر", "الله يعطيكم العافية على هذا الجهد المبارك")
            )
        ),
        Post(
            id = "p2",
            authorName = "زائر كريم",
            authorRole = UserRole.VISITOR,
            text = "وصلنا إلى العمود 212، استقبال رائع من إخواننا الخدام، جزاهم الله خيراً.",
            likeCount = 8
        )
    )
}
