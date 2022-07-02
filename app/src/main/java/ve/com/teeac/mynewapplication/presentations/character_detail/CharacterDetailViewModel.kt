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
import ve.com.teeac.mynewapplication.domain.models.Thumbnail
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharacterByIdUseCase
import ve.com.teeac.mynewapplication.domain.use_cases.GetItemsUseCase
import ve.com.teeac.mynewapplication.domain.use_cases.TypeItem
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
@Inject
constructor(
    private val useCase: GetCharacterByIdUseCase,
    private val useCaseItems: GetItemsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var jobCharacter: Job? = null
    private var jobComics: Job? = null
    private var jobEvents: Job? = null
    private var jobSeries: Job? = null
//    private var jobStories: Job? = null

    private var _state by mutableStateOf(CharacterDetailState())
    val state: CharacterDetailState
        get() = _state

    init {
        _state = state.copy(
            id = savedStateHandle.get<Int?>("id") ?: -1,
            character = createCharacterInitial(savedStateHandle)
        )
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
            "", emptyList(), emptyList(), emptyList(), emptyList())
    }

    private fun getAll() {
        getCharacter()
//        getStories()
        getComics()
        getEvents()
        getSeries()
    }

    private fun getComics() {
        jobComics?.cancel()
        jobComics = useCaseItems(state.id, TypeItem.COMICS).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoadingComics = true)
                is Response.Success -> state.copy(
                    character = state.character.copy(comics = it.data!!),
                    isLoadingComics = false
                )
                is Response.Error -> state.copy(isLoadingComics = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun getEvents() {
        jobEvents?.cancel()
        jobEvents = useCaseItems(state.id, TypeItem.EVENTS).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoadingEvents = true)
                is Response.Success -> state.copy(
                    character = state.character.copy(events = it.data!!),
                    isLoadingEvents = false
                )
                is Response.Error -> state.copy(isLoadingEvents = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

    private fun getSeries() {
        jobSeries?.cancel()
        jobSeries = useCaseItems(state.id, TypeItem.SERIES).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoadingSeries = true)
                is Response.Success -> state.copy(
                    character = state.character.copy(series = it.data!!),
                    isLoadingSeries = false
                )
                is Response.Error -> state.copy(isLoadingSeries = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

//    private fun getStories() {
//        jobStories?.cancel()
//        jobStories = useCaseItems(state.id, TypeItem.STORIES).onEach {
//            _state = when (it) {
//                is Response.Loading -> state.copy(isLoadingStories = true)
//                is Response.Success -> state.copy(
//                    character = state.character.copy(stories = it.data!!),
//                    isLoadingStories = false
//                )
//                is Response.Error -> state.copy(isLoadingStories = false, error = it.message)
//            }
//        }.launchIn(viewModelScope)
//    }


    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.LoadCharactersEvent -> loadCharacter()
            is CharacterDetailEvent.Refresh -> refresh()
        }
    }

    private fun refresh() {
        getCharacter()
    }

    private fun loadCharacter() {
        getCharacter()
    }

    private fun getCharacter() {
        jobCharacter?.cancel()
        jobCharacter = useCase(state.id).onEach {
            _state = when (it) {
                is Response.Loading -> state.copy(isLoading = true)
                is Response.Success -> state.copy(isLoading = false, character = state.character.copy(description = it.data!!.description))
                is Response.Error -> state.copy(isLoading = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }

}