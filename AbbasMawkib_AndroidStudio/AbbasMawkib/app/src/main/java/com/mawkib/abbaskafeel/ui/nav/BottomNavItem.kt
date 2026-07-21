package com.mawkib.abbaskafeel.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.ui.graphics.vector.ImageVector
import com.mawkib.abbaskafeel.R

sealed class BottomNavItem(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", R.string.tab_home, Icons.Filled.Home)
    object Feed : BottomNavItem("feed", R.string.tab_feed, Icons.Filled.Article)
    object Live : BottomNavItem("live", R.string.tab_live, Icons.Filled.LiveTv)

    companion object {
        val items = listOf(Home, Feed, Live)
    }
}
