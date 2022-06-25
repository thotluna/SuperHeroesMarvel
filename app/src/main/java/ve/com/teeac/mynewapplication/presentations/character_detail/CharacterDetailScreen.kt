package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber
import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    WrapperDetails(
        isLoading = state.isLoading,
        modifier = modifier
    ) {
        Timber.d("Character: ${state.character}")
        state.character?.let{ character ->
            Header(character)
            Spacer(modifier = Modifier.height(16.dp))
            if(character.description.isNotBlank()){
                Text(text = character.description)
            }
        }
    }
}

@Composable
fun WrapperDetails(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(8.dp)
            .background(BlackMarvel)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                content()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(character: Character) {
    Card(
        modifier = Modifier.height(IntrinsicSize.Max),
        shape = CutCornerShape(
            CornerSize(0.dp),
            CornerSize(0.dp),
            CornerSize(16.dp),
            CornerSize(0.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = BlackMarvel
        )

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(IntrinsicSize.Max)
        ) {
            GlideImage(
                imageModel = "${character.thumbnail.path}/portrait_xlarge.${character.thumbnail.extension}",
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                failure = { Text(text = "image request failed.") }
            )
            Box(
                modifier = Modifier
                    .weight(.1f)
                    .background(RedMarvel)
                    .fillMaxHeight(1f)
            )
            CharacterName(
                characterName = character.name,
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(200.dp)
            )
        }
    }
}

