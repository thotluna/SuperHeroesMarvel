@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package ve.com.teeac.mynewapplication.presentations.characteres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.presentations.shared.CharacterName
import ve.com.teeac.mynewapplication.ui.theme.BlackMarvel
import ve.com.teeac.mynewapplication.ui.theme.RedMarvel

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = hiltViewModel(),
    goCharacterDetails: (Int, String, String, String) -> Unit,
    title: (String) -> Unit,
    isLoading: (Boolean) -> Unit
) {

    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)


    title("Characters")
    isLoading(state.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 0.dp)
                .then(modifier)
        ) {
            OutlinedTextField(
                value = state.nameStartsWith ?: "",
                onValueChange = { viewModel.onEvent(CharactersEvent.ChangeNameStart(it)) },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth(1f),
                shape = RoundedCornerShape(CornerSize(64.dp)),
            )
            Spacer(modifier = Modifier.height(8.dp))
            CharactersList(
                list = state.characters,
                loadCharacters = {
                    viewModel.onEvent(CharactersEvent.LoadCharactersEvent)
                },
                navigateToDetails = { id, name, path, ext ->
                    goCharacterDetails(id, name, path, ext)
                }
            )
        }
    }
}

@Composable
fun InfiniteListHandler(
    listState: LazyGridState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            (lastVisibleItemIndex / 3) > ((totalItemsNumber / 3) - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    onLoadMore()
                }
            }
    }
}

@Composable
private fun CharactersList(
    list: List<CharacterItem>,
    modifier: Modifier = Modifier,
    loadCharacters: () -> Unit,
    navigateToDetails: (Int, String, String, String) -> Unit
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        items(list) { item ->
            CharacterCard(
                character = item,
                onClick = { id, name, path, ext ->
                    navigateToDetails(id, name, path, ext)
                }
            )
        }
    }
    InfiniteListHandler(listState = gridState) { loadCharacters() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(
    character: CharacterItem,
    modifier: Modifier = Modifier,
    onClick: (Int, String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .height(350.dp)
            .clickable {
                onClick(
                    character.id,
                    character.name,
                    character.thumbnail.path,
                    character.thumbnail.extension
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
                requestOptions = {
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
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
