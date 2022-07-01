package ve.com.teeac.mynewapplication.data.mappers

import timber.log.Timber
import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.models.Thumbnail

object ItemMapper {

    fun characterDtoToCharacterItem(characterDTO: CharacterDto): CharacterItem {
        return CharacterItem(
            id = characterDTO.id,
            name = characterDTO.name,
            thumbnail = thumbnailToDto(characterDTO.thumbnail)
        )
    }

    fun comicToDto(dto: ComicDto): Item{
        try{
            return Item(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                thumbnail = thumbnailToDto(dto.thumbnail)
            )
        }catch (e: Exception){
            Timber.e("Error in comic to dto")
            throw e
        }
    }

    fun seriesToDto(dto: SeriesDto): Item{
        try {
            return Item(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                thumbnail = thumbnailToDto(dto.thumbnail)
            )
        }catch (e: Exception){
            Timber.e("Error in series to dto")
            throw e
        }
    }

    fun eventToDto(dto: EventDto): Item{
        try {
            return Item(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                thumbnail = dto.thumbnail?.let { thumbnailToDto(it) }
            )
        }catch (e: Exception){
            Timber.e("Error in event to dto")
            throw e
        }
    }

    fun storyToDto(dto: StoryDto): Item{
        try {
            return Item(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                thumbnail = thumbnailToDto(dto.thumbnail)
            )
        }catch (e: Exception){
            Timber.e("Error in story to dto")
            throw e
        }
    }

    fun thumbnailToDto(dto: ThumbnailDto): Thumbnail{
        return Thumbnail(dto.extension, dto.path)
    }

}