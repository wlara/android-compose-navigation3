package io.github.wlara.compose.navigation3.demo.animations

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
private data object LandingKey : NavKey

@Serializable
private data object ViewAllKey : NavKey

@Serializable
private data class DetailsKey(val id: Int) : NavKey

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(LandingKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<LandingKey> {
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

            entry<DetailsKey>(
                // TODO: uncomment to override the transition spec for this NavEntry only
//                metadata = NavDisplay.transitionSpec {
//                    // Slide new content up, keeping the old content in place underneath
//                    slideInVertically(
//                        initialOffsetY = { it },
//                        animationSpec = tween(1000)
//                    ) togetherWith ExitTransition.KeepUntilTransitionsFinished
//                } + NavDisplay.popTransitionSpec {
//                    // Slide old content down, revealing the new content in place underneath
//                    EnterTransition.None togetherWith
//                            slideOutVertically(
//                                targetOffsetY = { it },
//                                animationSpec = tween(1000)
//                            )
//                } + NavDisplay.predictivePopTransitionSpec {
//                    // Slide old content down, revealing the new content in place underneath
//                    EnterTransition.None togetherWith
//                            slideOutVertically(
//                                targetOffsetY = { it },
//                                animationSpec = tween(1000)
//                            )
//                }
            ) { key ->
                DetailsScreen(
                    storyId = key.id,
                    onNavigateUp = { backStack.removeLastOrNull() }
                )
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
