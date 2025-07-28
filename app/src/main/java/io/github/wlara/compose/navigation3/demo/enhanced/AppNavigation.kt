package io.github.wlara.compose.navigation3.demo.enhanced

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.wlara.compose.navigation3.demo.ui.DetailsScreen
import io.github.wlara.compose.navigation3.demo.ui.HomeScreen
import io.github.wlara.compose.navigation3.demo.ui.ViewAllScreen
import kotlinx.serialization.Serializable

@Serializable
private data object HomeKey : NavKey

@Serializable
private data object ViewAllKey : NavKey

@Serializable
private data class DetailsKey(val id: Int) : NavKey

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(HomeKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<HomeKey> {
                HomeScreen(
                    onViewAll = { backStack.add(ViewAllKey) },
                    onViewDetails = { id -> backStack.add(DetailsKey(id)) }
                )
            }

            entry<ViewAllKey> {
                ViewAllScreen(
                    onNavigateUp = { backStack.removeLastOrNull() },
                    onViewDetails = { id -> backStack.add(DetailsKey(id)) }
                )
            }

            entry<DetailsKey> { key ->
                DetailsScreen(
                    storyId = key.id,
                    onNavigateUp = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
