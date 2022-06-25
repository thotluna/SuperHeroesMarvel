@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package ve.com.teeac.mynewapplication.presentations.characteres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber
import ve.com.teeac.mynewapplication.core.presentations.LoadingAnimation
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel(),
    goCharacterDetails: (Int) -> Unit = {}
) {

    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)
    val gridState = rememberLazyGridState()

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .then(modifier)
        ) {

            Title(
                isLoading = state.isLoading,
                modifier = Modifier
            )

            CharactersList(
                list = state.characters,
                isLoading = state.isLoading,
                state = gridState,
                loadCharacters = { viewModel.onEvent(CharactersEvent.LoadCharactersEvent) },
                navigateToDetails = {
                    goCharacterDetails(it)
                }
            )
        }
    }
}

@Composable
private fun CharactersList(
    list: List<CharacterItem>,
    state: LazyGridState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    loadCharacters: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        items(list.size) { index ->
            if (index >= list.size - 3 && index < list.size && !isLoading) {
                loadCharacters()
            }
            CharacterCard(
                character = list[index],
                onClick = {
                    navigateToDetails(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(
    character: CharacterItem,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .height(350.dp)
            .clickable {
                onClick(character.id)
            }
            .then(modifier),
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
        Column(
            modifier = modifier
        ) {
            GlideImage(
                imageModel = "${character.thumbnail.path}/portrait_xlarge.${character.thumbnail.extension}",
                modifier = modifier
                    .aspectRatio(.7f),
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

            LineComponent()

            CharacterName(
                character.name,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun LineComponent(height: Dp = 4.dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(RedMarvel)
    )
}




@Composable
private fun Title(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Characters",
            modifier = Modifier
                .then(modifier),
            style = MaterialTheme.typography.headlineLarge
        )
        if (isLoading) {
            Timber.d("is loading ...")
            LoadingAnimation(
                circleSize = 10.dp,
                travelDistance = 10.dp,
                spaceBetween = 6.dp
            )
        }
    }
}
