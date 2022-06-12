package ve.com.teeac.mynewapplication.presentations

import androidx.compose.ui.geometry.Offset

sealed class CharactersEvent{
    object LoadCharactersEvent : CharactersEvent()
    object Refresh : CharactersEvent()
}
