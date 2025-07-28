package io.github.wlara.compose.navigation3.demo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.wlara.compose.navigation3.demo.R
import io.github.wlara.compose.navigation3.demo.ui.theme.ComposeNavigation3DemoTheme

@Composable
fun HomeScreen(
    onViewAll: () -> Unit,
    onViewDetails: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopBar(onViewAll) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            items(5) { index ->
                StoryItem(onViewDetails, index + 1)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onViewAll: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.home_title),
                style = MaterialTheme.typography.headlineLarge
            )
        },
        actions = {
            Button(
                onClick = onViewAll,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(R.string.home_view_all_button),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun StoryItem(onViewDetails: (Int) -> Unit, storyId: Int) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clickable(onClick = { onViewDetails(storyId) }),
    ) {
        Text(
            text = stringResource(R.string.home_header, storyId),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.home_paragraph),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ComposeNavigation3DemoTheme {
        HomeScreen(
            onViewAll = {},
            onViewDetails = {}
        )
    }
}
