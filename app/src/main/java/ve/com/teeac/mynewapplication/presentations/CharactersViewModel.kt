package ve.com.teeac.mynewapplication.presentations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.use_cases.GetCharactersUseCase
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
                    _state = _state.copy(
                        isLoading = false,
                        characters = it.data ?: emptyList(),
                    )
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