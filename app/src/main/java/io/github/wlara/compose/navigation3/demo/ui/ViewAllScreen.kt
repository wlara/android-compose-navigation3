package io.github.wlara.compose.navigation3.demo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.wlara.compose.navigation3.demo.R
import io.github.wlara.compose.navigation3.demo.ui.theme.ComposeNavigation3DemoTheme

@Composable
fun ViewAllScreen(
    onNavigateUp: () -> Unit,
    onViewDetails: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopBar(onNavigateUp) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            items(20) { index ->
                StoryItem(onViewDetails, index + 1)
            }
            item {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.view_all_title),
                style = MaterialTheme.typography.headlineLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun StoryItem(onViewDetails: (Int) -> Unit, storyId: Int) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clickable { onViewDetails(storyId) },
    ) {
        Text(
            text = stringResource(R.string.view_all_header, storyId),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(R.string.view_all_paragraph),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ViewAllScreenPreview() {
    ComposeNavigation3DemoTheme {
        ViewAllScreen(
            onNavigateUp = {},
            onViewDetails = {}
        )
    }
}
