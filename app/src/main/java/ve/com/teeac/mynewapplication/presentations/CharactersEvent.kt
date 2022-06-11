package ve.com.teeac.mynewapplication.presentations

import androidx.compose.ui.geometry.Offset

sealed class CharactersEvent{
    data class LoadCharactersEvent(val offset: Int) : CharactersEvent()
}
