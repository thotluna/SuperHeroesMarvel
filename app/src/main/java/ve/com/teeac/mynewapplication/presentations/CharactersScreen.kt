package ve.com.teeac.mynewapplication.presentations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.Character

@Composable
fun CharacterScreen(
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)
    val gridState = rememberLazyGridState()


    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(CharactersEvent.Refresh) }) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            //Title
            Title(
                isLoading = state.isLoading,
                modifier = Modifier.align(Alignment.TopStart)
            )

            if (state.characters.isNotEmpty()) {
                CharactersList(
                    list = state.characters,
                    isLoading = state.isLoading,
                    state = gridState,
                    loadCharacters = { viewModel.onEvent(CharactersEvent.LoadCharactersEvent) },
                )
            } else {
                MessageDoesNotElement(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun MessageDoesNotElement(
    modifier: Modifier = Modifier
) {
    Text(
        text = "No Characters",
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun CharactersList(
    list: List<Character>,
    state: LazyGridState,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    loadCharacters: () -> Unit
) {

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(top = 64.dp, bottom = 16.dp)
            .fillMaxWidth()
            .then(modifier)
    ) {
        items(list.size) { index ->
            if(index >= list.size -6 && index < list.size && !isLoading){
                loadCharacters()
            }
            Text(text = list[index].name,
            modifier = Modifier.padding(vertical = 64.dp))
        }
    }
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
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "Characters",
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .then(modifier),
            style = MaterialTheme.typography.headlineLarge
        )
        if(isLoading){
            Timber.d("is loading ...")
            LoadingAnimation(
                circleSize = 10.dp,
                travelDistance = 10.dp,
                spaceBetween = 6.dp
            )
        }
    }
}
