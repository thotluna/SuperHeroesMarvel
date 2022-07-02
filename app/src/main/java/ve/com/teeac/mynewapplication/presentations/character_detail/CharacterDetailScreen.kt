package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber
import ve.com.teeac.mynewapplication.core.presentations.LoadingAnimation
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@ExperimentalMaterial3Api
@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    title: (String) -> Unit,
    isLoading: (Boolean) -> Unit,
    navigateImage: (String) -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Timber.d("////Character end: ${state.character}")
    title(state.character.name)
    isLoading(state.isLoading)
    WrapperDetails(modifier = modifier) {
        Header(state.character)
        Spacer(modifier = Modifier.height(16.dp))
        Section(title = "Description") {
            Text(
                text = state.character.description.ifBlank {
                    "Does not descriptions"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Section(title = "Comics", isLoading = state.isLoadingComics) {
            ListItems(
                state.character.comics,
                onClick = { url -> navigateImage(url) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Section(title = "Events", isLoading = state.isLoadingEvents) {
            ListItems(
                state.character.events,
                onClick = { url -> navigateImage(url) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Section(title = "Series", isLoading = state.isLoadingSeries) {
            ListItems(
                state.character.series,
                onClick = { url -> navigateImage(url) }
            )
        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Section(title = "Stories", isLoading = state.isLoadingStories) {
//            ListItems(
//                state.character.stories,
//                onClick = { url -> navigateImage(url) }
//            )
//        }
    }
}

@Composable
fun WrapperDetails(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
            .then(modifier),
    ) {
        content()
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
    CardWrapper(modifier = Modifier.height(200.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(IntrinsicSize.Max)
        ) {
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
        requestOptions = {
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        },
        modifier = Modifier
            .aspectRatio(.7f)
            .then(modifier),
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
    isLoading: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier

            .then(modifier)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Start
            )
            if(isLoading){
                Box(modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd){
                    LoadingAnimation(
                        circleSize = 7.dp,
                        travelDistance = 14.dp
                    )
                }
            }
        }
        HorizontalLine()
        Spacer(modifier = Modifier.height(4.dp))
        content()
    }

}


@ExperimentalMaterial3Api
@Composable
private fun ListItems(
    listItems: List<Item>,
    onClick: (String) -> Unit = {}
) {
    if (listItems.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .width(400.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listItems) { item ->
                item.thumbnail?.let {
                    ItemComic(
                        url = it.getUrl(),
                        title = item.title,
                        onClick = { url -> onClick(url) }
                    )
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
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    CardWrapper(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick(url) }
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



