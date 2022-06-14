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
        }
    }

    private fun getCharacters() {
        job?.cancel()
        job = useCase(state.offset).onEach {
            when (it) {
                is Response.Loading -> {
                    _state = _state.copy(
                        isLoading = true
                    )
                }

                is Response.Success -> {
                    it.data?.let { list ->

                        if(list.isNotEmpty()) {
                            _state = if(state.offset == 0) {
                                _state.copy(
                                    isLoading = false,
                                    characters = list.filter { characters ->
                                        characters.thumbnail.path != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"
                                    }
                                )
                            }else{
                                _state.copy(
                                    isLoading = false,
                                    characters = state.characters + list.filter { characters ->
                                        characters.thumbnail.path != "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"
                                    }
                                )
                            }
                        }else{
                            _state = _state.copy(
                                isLoading = false,
                                characters = emptyList()
                            )
                        }
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