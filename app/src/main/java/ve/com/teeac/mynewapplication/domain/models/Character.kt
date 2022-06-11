package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.*

data class Character(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: String,
    val thumbnailExt: String,
    val urls: List<Url>
) {
    companion object {
        fun fromDto(dto: CharacterDTO): Character {
            return Character(
                comics = dto.comics,
                description = dto.description,
                events = dto.events,
                id = dto.id,
                modified = dto.modified,
                name = dto.name,
                resourceURI = dto.resourceURI,
                series = dto.series,
                stories = dto.stories,
                thumbnail = dto.thumbnail.path,
                thumbnailExt = dto.thumbnail.extension,
                urls = dto.urls
            )
        }
    }
}
