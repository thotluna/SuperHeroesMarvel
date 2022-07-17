package ve.com.teeac.mynewapplication.presentations.characteres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharacters
import ve.com.teeac.mynewapplication.utils.Response

@HiltViewModel
class CharactersViewModel
@Inject
constructor(
    private val useCase: GetCharacters
) : ViewModel() {

    private var _state by mutableStateOf(CharactersState())
    val state: CharactersState
        get() = _state

    private var job: Job? = null

    fun onEvent(event: CharactersEvent) {
        when (event) {
            is CharactersEvent.LoadCharactersEvent -> { getCharacters() }
            is CharactersEvent.Refresh -> { getCharacters(forceUpdate = true) }
            is CharactersEvent.ChangeNameStart -> {
                _state = state.copy(
                    nameStartsWith = event.name,
                    characters = emptyList()
                )
                if (event.name.isNotEmpty() && event.name.length >= 3) {
                    getCharacters()
                }
            }
        }
    }

    private fun getCharacters(forceUpdate: Boolean = false) {
        job?.cancel()
        job = useCase(nameStartsWith = state.nameStartsWith, forceUpdate = forceUpdate).onEach {
            _state = when (it) {
                is Response.Loading -> {
                    _state.copy(isLoading = true)
                }
                is Response.Success -> {
                    if (it.data != null) {
                        _state.copy(isLoading = false, characters = state.characters + it.data)
                    } else {
                        _state.copy(isLoading = false, characters = emptyList())
                    }
                }
                is Response.Error -> {
                    _state.copy(isLoading = false, error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
