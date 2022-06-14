package ve.com.teeac.mynewapplication.presentations.characteres

sealed class CharactersEvent{
    object LoadCharactersEvent : CharactersEvent()
    object Refresh : CharactersEvent()
}
