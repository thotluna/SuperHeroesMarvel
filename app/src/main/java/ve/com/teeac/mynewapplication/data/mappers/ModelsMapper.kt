package ve.com.teeac.mynewapplication.data.mappers

import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.Thumbnail

object ModelsMapper {

    fun characterDtoToCharacter( dto: CharacterDto): Character{
        return Character(
            description = dto.description,
            id = dto.id,
            name = dto.name,
            thumbnail = ItemMapper.thumbnailToDto(dto.thumbnail),
            comics =  emptyList(),
            events =  emptyList(),
            series = emptyList(),
            stories = emptyList(),
        )
    }
}