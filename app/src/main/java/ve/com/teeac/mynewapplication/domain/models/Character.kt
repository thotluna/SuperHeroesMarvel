package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.*

data class Character(
    val comics: Comics,
    val events: Events,
    val modified: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val urls: List<Url>,
    val description: String,
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
) {
    companion object {
        fun fromDto(dto: CharacterDTO): Character {
            return Character(
                description = dto.description,
                id = dto.id,
                name = dto.name,
                thumbnail = dto.thumbnail,
                comics =  dto.comics,
                events =  dto.events,
                modified = dto.modified,
                resourceURI = dto.resourceURI,
                series = dto.series,
                stories = dto.stories,
                urls =  dto.urls,
            )
        }
    }
}
