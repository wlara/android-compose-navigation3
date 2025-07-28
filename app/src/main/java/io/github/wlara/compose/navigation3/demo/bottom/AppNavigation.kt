package io.github.wlara.compose.navigation3.demo.bottom

import android.annotation.SuppressLint
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import io.github.wlara.compose.navigation3.demo.ui.DetailsScreen
import io.github.wlara.compose.navigation3.demo.ui.HomeScreen
import io.github.wlara.compose.navigation3.demo.ui.ProfileScreen
import io.github.wlara.compose.navigation3.demo.ui.SettingsScreen
import io.github.wlara.compose.navigation3.demo.ui.ViewAllScreen
import kotlinx.serialization.Serializable

private sealed interface TopLevelNavKey : NavKey {
    val icon: ImageVector
}

@Serializable
private data object HomeKey : TopLevelNavKey {
    override val icon = Icons.Default.Home
}

@Serializable
private data object ProfileKey : TopLevelNavKey {
    override val icon = Icons.Default.Person
}

@Serializable
private data object SettingsKey : TopLevelNavKey {
    override val icon = Icons.Default.Settings
}

@Serializable
private data object ViewAllKey : NavKey

@Serializable
private data class DetailsKey(val id: Int) : NavKey

private val TOP_LEVEL_KEYS: List<TopLevelNavKey> = listOf(HomeKey, ProfileKey, SettingsKey)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val topLevelBackStack = remember { TopLevelBackStack<NavKey>(HomeKey) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_KEYS.forEach { key ->
                    val isSelected = key == topLevelBackStack.topLevelKey
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            topLevelBackStack.addTopLevel(key)
                        },
                        icon = {
                            Icon(
                                imageVector = key.icon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        },
    ) {
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<HomeKey> {
                    HomeScreen(
                        onViewAll = { topLevelBackStack.add(ViewAllKey) },
                        onViewDetails = { id -> topLevelBackStack.add(DetailsKey(id)) }
                    )
                }

                entry<ViewAllKey> {
                    ViewAllScreen(
                        onNavigateUp = { topLevelBackStack.removeLast() },
                        onViewDetails = { id -> topLevelBackStack.add(DetailsKey(id)) }
                    )
                }

                entry<DetailsKey> { key ->
                    DetailsScreen(
                        storyId = key.id,
                        onNavigateUp = { topLevelBackStack.removeLast() }
                    )
                }

                entry<ProfileKey> {
                    ProfileScreen()
                }

                entry(SettingsKey) {
                    SettingsScreen()
                }
            },
            transitionSpec = {
                // Slide in from right when navigating forward
                slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            predictivePopTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            }
        )
    }
}

// from: https://github.com/android/nav3-recipes/blob/main/app/src/main/java/com/example/nav3recipes/commonui/CommonUiActivity.kt
private class TopLevelBackStack<T : Any>(startKey: T) {

    // Maintain a stack for each top level route
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}
