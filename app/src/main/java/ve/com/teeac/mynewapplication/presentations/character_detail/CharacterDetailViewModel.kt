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
import ve.com.teeac.mynewapplication.domain.models.Thumbnail
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharacterByIdUseCase
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
@Inject
constructor(
    private val useCase: GetCharacterByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var job: Job? = null

    private var _state by mutableStateOf(CharacterDetailState())
    val state: CharacterDetailState
        get() = _state

    init {
        val id = savedStateHandle.get<Int?>("id") ?: -1
        val name = savedStateHandle.get<String>("name") ?: ""
        val imageUrl = savedStateHandle.get<String>("imageurl")?.split("portrait_xlarge")
        val thumbnail = imageUrl?.let {
            Thumbnail(imageUrl[1],"${ imageUrl[0] }portrait_xlarge" )
        }?: Thumbnail("","")
        Timber.d("thumbnail: $thumbnail, $imageUrl")
        val newCharacter = Character(id, name, thumbnail, "", emptyList(), emptyList(), emptyList(), emptyList())

        _state = state.copy(
            id = id,
            character = newCharacter
        )

        getCharacter(state.id)
    }

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.LoadCharactersEvent -> loadCharacter()
            is CharacterDetailEvent.Refresh -> refresh()
        }
    }

    private fun refresh() {
        getCharacter(state.id)
    }

    private fun loadCharacter() {
        getCharacter(state.id)
    }

    private fun getCharacter(id: Int) {
       job?.cancel()
       job = useCase(id).onEach {
           Timber.d("Intro of getCharacter - Character for UseCase:")
           _state = when(it){
               is Response.Loading -> state.copy(isLoading = true)
               is Response.Success -> state.copy(isLoading = false, character = it.data!!)
               is Response.Error -> state.copy(isLoading = false, error = it.message)
           }
           Timber.d("Intro of getCharacter - Character for UseCase: $state, $it")
       }.launchIn(viewModelScope)
    }

}