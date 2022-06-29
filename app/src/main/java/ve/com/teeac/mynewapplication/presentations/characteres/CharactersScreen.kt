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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel(),
    goCharacterDetails: (Int, String, String) -> Unit,
    title: (String) -> Unit,
    isLoading: (Boolean) -> Unit
) {

    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)
    val gridState = rememberLazyGridState()

    title("Characters")
    isLoading(state.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 0.dp )
                .then(modifier)
        ) {
            CharactersList(
                list = state.characters,
                isLoading = state.isLoading,
                state = gridState,
                loadCharacters = { viewModel.onEvent(CharactersEvent.LoadCharactersEvent) },
                navigateToDetails = { id, name, url ->
                    goCharacterDetails(id, name, url)
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
    navigateToDetails: (Int, String, String) -> Unit
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
                onClick = { id, name, url ->
                    navigateToDetails(id, name, url)
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
    onClick: (Int, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .height(350.dp)
            .clickable {
                onClick(
                    character.id,
                    character.name,
                    character.thumbnail.getUrl()
                )
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
                requestBuilder = {
                    Glide
                        .with(LocalContext.current)
                        .asDrawable()
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .thumbnail(0.6f)
                        .transition(DrawableTransitionOptions.withCrossFade())
                },
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
