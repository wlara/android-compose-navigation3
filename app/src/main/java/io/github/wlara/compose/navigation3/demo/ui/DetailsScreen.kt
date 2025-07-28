package io.github.wlara.compose.navigation3.demo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun DetailsScreen(
    storyId: Int,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.details_title, storyId), onNavigateUp)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Text(
                text = stringResource(R.string.details_paragraph),
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineLarge) },
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

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    ComposeNavigation3DemoTheme {
        DetailsScreen(
            storyId = 1,
            onNavigateUp = {}
        )
    }
}
