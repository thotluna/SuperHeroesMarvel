package ve.com.teeac.mynewapplication.presentations.character_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.Thumbnail
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharacterById
import ve.com.teeac.mynewapplication.domain.use_cases.GetItemsComics
import ve.com.teeac.mynewapplication.domain.use_cases.GetItemsEvents
import ve.com.teeac.mynewapplication.domain.use_cases.GetItemsSeries
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
@Inject
constructor(
    private val useCase: GetCharacterById,
    private val getComicUseCase: GetItemsComics,
    private val getEventUseCase: GetItemsEvents,
    private val getSeriesUseCase: GetItemsSeries,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var jobCharacter: Job? = null
    private var jobComics: Job? = null
    private var jobEvents: Job? = null
    private var jobSeries: Job? = null

    private var _state by mutableStateOf(CharacterDetailState())
    val state: CharacterDetailState
        get() = _state

    init {
        _state = state.copy(
            id = savedStateHandle.get<Int?>("id") ?: -1,
            character = createCharacterInitial(savedStateHandle)
        )
        Timber.d("////Character: ${state.character}")
        getAll()
    }

    private fun createCharacterInitial(savedStateHandle: SavedStateHandle): Character {
        val imageUrl = savedStateHandle.get<String>("imageurl") ?: ""
        val extension = savedStateHandle.get<String>("extension") ?: ""
        return Character(
            id = savedStateHandle.get<Int?>("id") ?: -1,
            name = savedStateHandle.get<String>("name") ?: "",
            thumbnail = if (imageUrl.isNotBlank()) {
                Thumbnail(extension, imageUrl)
            } else {
                Thumbnail("", "")
            },
            "", emptyList(), emptyList(), emptyList(), emptyList()
        )
    }

    private fun getAll(forceUpdate: Boolean = false) {
        getCharacter()
        getComics(forceUpdate)
        getEvents(forceUpdate)
        getSeries(forceUpdate)
    }

    private fun getCharacter() {
        jobCharacter?.cancel()
        jobCharacter = useCase(state.id).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoading = true)
                is Response.Success -> state.copy(
                    isLoading = false,
                    character = state.character.copy(description = it.data!!.description)
                )
                is Response.Error -> state.copy(isLoading = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun getComics(forceUpdate: Boolean = false) {
        jobComics?.cancel()
        jobComics =
            getComicUseCase(
                id = state.id,
                forceUpdate = forceUpdate,
            ).onEach {
                _state = when (it) {
                    is Response.Loading -> state.copy(isLoadingComics = true)
                    is Response.Success -> state.copy(
                        character = state.character.copy(comics = state.character.comics + it.data!!),
                        isLoadingComics = false
                    )
                    is Response.Error -> state.copy(isLoadingComics = false, error = it.message)
                }
            }.launchIn(viewModelScope)
    }

    private fun getEvents(forceUpdate: Boolean = false) {
        jobEvents?.cancel()
        jobEvents =
            getEventUseCase(state.id, forceUpdate = forceUpdate).onEach {
                _state = when (it) {
                    is Response.Loading -> state.copy(isLoadingEvents = true)
                    is Response.Success -> state.copy(
                        character = state.character.copy(events = state.character.events + it.data!!),
                        isLoadingEvents = false
                    )
                    is Response.Error -> state.copy(isLoadingEvents = false, error = it.message)
                }
            }.launchIn(viewModelScope)
    }

    private fun getSeries(forceUpdate: Boolean = false) {
        jobSeries?.cancel()
        jobSeries = getSeriesUseCase(state.id, forceUpdate = forceUpdate).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoadingSeries = true)
                is Response.Success -> state.copy(
                    character = state.character.copy(series = state.character.series + it.data!!),
                    isLoadingSeries = false
                )
                is Response.Error -> state.copy(isLoadingSeries = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.Refresh -> refresh()
            is CharacterDetailEvent.LoadComics -> getComics()
            is CharacterDetailEvent.LoadSeries -> getSeries()
            is CharacterDetailEvent.LoadEvents -> getEvents()
        }
    }

    private fun refresh() {
        getAll(forceUpdate = true)
    }


}