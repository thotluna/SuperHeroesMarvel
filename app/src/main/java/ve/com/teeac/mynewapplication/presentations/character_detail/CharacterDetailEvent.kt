package ve.com.teeac.mynewapplication.presentations.character_detail


sealed class CharacterDetailEvent {
    object LoadCharactersEvent : CharacterDetailEvent()
    object Refresh : CharacterDetailEvent()
}
