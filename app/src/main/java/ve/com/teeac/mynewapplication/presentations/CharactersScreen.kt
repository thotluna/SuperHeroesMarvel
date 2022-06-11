package ve.com.teeac.mynewapplication.presentations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharacterScreen(
    viewModel: CharactersViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.isLoading){
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.displayMedium
            )
        }else{
            if(state.characters.isEmpty()){
                Text(
                    text = "No characters found",
                    style = MaterialTheme.typography.displayMedium
                )
            }else{
                LazyColumn{
                    items(items = state.characters, key = { it.id }) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}