package ve.com.teeac.mynewapplication.presentations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterScreen(
    viewModel: CharactersViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.displayMedium
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if (state.characters.isEmpty()) {
                Text(
                    text = "No characters found",
                    style = MaterialTheme.typography.displayMedium
                )
            } else {
                LazyColumn {
                    stickyHeader {
                        HeaderCharacters()
                    }
                    items(items = state.characters, key = { it.id }) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }


            }
            Button(onClick = { viewModel.onEvent(CharactersEvent.LoadCharactersEvent) }) {
                Text(text = "Load more", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Preview
@Composable
fun HeaderCharacters() {
    Text(
        text = "Characters",
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        style = MaterialTheme.typography.headlineLarge
    )
}