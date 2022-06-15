package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterDetailScreen(
    id: Int,
    modifier: Modifier = Modifier
) {

    Text(text = "Character Detail Screen id: $id")

}

