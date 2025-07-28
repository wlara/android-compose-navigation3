package io.github.wlara.compose.navigation3.demo.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import io.github.wlara.compose.navigation3.demo.ui.DetailsScreen
import io.github.wlara.compose.navigation3.demo.ui.HomeScreen
import io.github.wlara.compose.navigation3.demo.ui.ViewAllScreen

private data object HomeKey

private data object ViewAllKey

private data class DetailsKey(val id: Int)

@Composable
fun AppNavigation() {
    val backStack = remember { mutableStateListOf<Any>(HomeKey) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is HomeKey -> NavEntry(key) {
                    HomeScreen(
                        onViewAll = { backStack.add(ViewAllKey) },
                        onViewDetails = { id -> backStack.add(DetailsKey(id)) }
                    )
                }

                is ViewAllKey -> NavEntry(key) {
                    ViewAllScreen(
                        onNavigateUp = { backStack.removeLastOrNull() },
                        onViewDetails = { id -> backStack.add(DetailsKey(id)) }
                    )
                }

                is DetailsKey -> NavEntry(key) {
                    DetailsScreen(
                        storyId = key.id,
                        onNavigateUp = { backStack.removeLastOrNull() }
                    )
                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )
}
