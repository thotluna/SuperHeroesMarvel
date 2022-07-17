package ve.com.teeac.mynewapplication.presentations.image

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowImageViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _urlImage by mutableStateOf("")
    val urlImage get() = _urlImage

    init {
        savedStateHandle.get<String>("imageurl")?.let {
            _urlImage = it
        }
    }
}
