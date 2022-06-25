package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@OptIn(ExperimentalMaterial3Api::class)
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
                if(character.comics.isNotEmpty()){
                    ListItems(character.comics)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Events") {
                if(character.comics.isNotEmpty()){
                    ListItems(character.events)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Series") {
                if(character.comics.isNotEmpty()){
                    ListItems(character.series)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Section(title = "Stories") {
                if(character.comics.isNotEmpty()){
                    ListItems(character.stories)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListItems(listItems: List<Item>) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(color = RedMarvel)
                .padding(vertical = 4.dp)
        )
        content()
    }

}



@ExperimentalMaterial3Api
@Composable
fun ItemComic(
    url: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = BlackMarvel
        ),
        shape = CutCornerShape(
            CornerSize(0.dp),
            CornerSize(0.dp),
            CornerSize(16.dp),
            CornerSize(0.dp)
        )
    ) {
        Box(modifier = Modifier
            .width(150.dp)
            .height(225.dp)
        ){
            GlideImage(
                imageModel = url,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(color = RedMarvel)
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header(character: Character) {
    Card(
        modifier = Modifier.height(200.dp),
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

