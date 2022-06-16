package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Text(text = "Character Detail Screen id: ${state.id}")

}

