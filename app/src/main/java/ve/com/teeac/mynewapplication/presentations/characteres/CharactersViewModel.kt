package ve.com.teeac.mynewapplication.presentations.characteres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharactersUseCase
import ve.com.teeac.mynewapplication.utils.Constants
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject
constructor(
    private val useCase: GetCharactersUseCase
) : ViewModel() {

    private var _state by mutableStateOf(CharactersState())
    val state: CharactersState
        get() = _state

    private var job: Job? = null

    init {
        getCharacters()

    }

    fun onEvent(event: CharactersEvent) {
        when (event) {
            is CharactersEvent.LoadCharactersEvent -> {
                _state = state.copy(offset = state.offset + Constants.limit.toInt())
                getCharacters()
            }
            is CharactersEvent.Refresh -> {
                _state = state.copy(offset = 0)
                getCharacters()
            }
            is CharactersEvent.ChangeNameStart -> {
                _state = state.copy(
                    nameStartsWith = event.name,
                    offset = 0,
                    characters = emptyList()
                )
                if (event.name.isNotEmpty()) {
                    getCharacters()
                }
            }
        }
    }

    private fun getCharacters() {
        job?.cancel()
        job = useCase(state.offset, state.nameStartsWith).onEach {
            when (it) {
                is Response.Loading -> {
                    _state = _state.copy(
                        isLoading = true
                    )
                }

                is Response.Success -> {
                    _state = if(it.data != null) {
                                _state.copy(
                                    isLoading = false,
                                    characters = state.characters + it.data
                                )
                            }else{
                                _state.copy(
                                    isLoading = false,
                                    characters = emptyList()
                                )
                            }
                }

                is Response.Error -> {
                    _state = _state.copy(
                        isLoading = false,
                        error = it.message ?: "Error"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}