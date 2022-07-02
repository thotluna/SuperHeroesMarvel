package ve.com.teeac.mynewapplication.presentations.character_detail


sealed class CharacterDetailEvent {

    object Refresh : CharacterDetailEvent()

    object LoadComics: CharacterDetailEvent()
    object LoadEvents: CharacterDetailEvent()
    object LoadSeries: CharacterDetailEvent()
}
