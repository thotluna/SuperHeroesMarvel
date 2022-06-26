package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@ExperimentalMaterial3Api
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
        state.character?.let { character ->
            Header(character)
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Description") {
                Text(
                    text = character.description.ifBlank {
                        "Does not descriptions"
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Comics") {
                ListItems(character.comics)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Events") {
                ListItems(character.events)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Series") {
                ListItems(character.series)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Stories") {
                ListItems(character.stories)
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
            .verticalScroll(rememberScrollState())
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.TopCenter)
            ) {
                content()
            }
        }
    }

}

@ExperimentalMaterial3Api
@Composable
fun CardWrapper(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.then(modifier),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = BlackMarvel
        ),
    ) {
        content()
    }
}

@ExperimentalMaterial3Api
@Composable
private fun Header(character: Character) {
    CardWrapper( modifier = Modifier.height(200.dp) ) {
        Row( modifier = Modifier
            .fillMaxWidth(1f)
            .height(IntrinsicSize.Max) ) {
            Image(
                imageUrl = character.thumbnail.getUrl(),
                modifier = Modifier.weight(1f)
            )
            VerticalLine()
            CharacterName(
                characterName = character.name,
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun VerticalLine(with: Dp = 4.dp) {
    Box(
        modifier = Modifier
            .background(RedMarvel)
            .fillMaxHeight(1f)
            .width(with)
    )
}

@Composable
fun HorizontalLine(height: Dp = 4.dp) {
    Box(
        modifier = Modifier
            .background(RedMarvel)
            .fillMaxWidth(1f)
            .height(height)
    )
}

@Composable
private fun Image(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    GlideImage(
        imageModel = imageUrl,
        modifier = modifier,
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
}

@Composable
fun Section(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        HorizontalLine()
        Spacer(modifier = Modifier.height(4.dp))
        content()
    }

}


@ExperimentalMaterial3Api
@Composable
private fun ListItems(listItems: List<Item>) {
    if (listItems.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .width(400.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listItems) { item ->
                item.thumbnail?.let {
                    ItemComic(url = it.getUrl(), title = item.title)
                }
            }
        }
    }
}




@ExperimentalMaterial3Api
@Composable
fun ItemComic(
    url: String,
    title: String,
    modifier: Modifier = Modifier
) {
    CardWrapper(
        modifier = Modifier
            .width(150.dp)
            .then(modifier)
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(225.dp)
        ) {
            Image(imageUrl = url)
        }
        HorizontalLine()
        Box(
            modifier = Modifier
                .height(80.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(1f)
            )
        }
    }

}



