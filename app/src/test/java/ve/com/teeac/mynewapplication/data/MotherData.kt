package ve.com.teeac.mynewapplication.data

import ve.com.teeac.mynewapplication.data.dtos.*

object MotherData {

    val oneCharacter = CharacterDto(
        id = 1,
        name = "name 1",
        description = "description 1",
        thumbnail = ThumbnailDto("extension 1", "path 1")
    )

    val twoCharacter = CharacterDto(
        id = 2,
        name = "name 2",
        description = "description 2",
        thumbnail = ThumbnailDto("extension 2", "path 2")
    )

    val characterWithoutThumbnail = CharacterDto(
        id = 3,
        name = "name 3",
        description = "description 3",
        thumbnail = ThumbnailDto("jpg", "image_not_found")
    )

    val fourCharacter = CharacterDto(
        id = 4,
        name = "name 4",
        description = "description 4",
        thumbnail = ThumbnailDto("extension 4", "path 4")
    )

    val fiveCharacter = CharacterDto(
        id = 5,
        name = "name 5",
        description = "description 5",
        thumbnail = ThumbnailDto("extension 5", "path 5")
    )

    val sixCharacter = CharacterDto(
        id = 6,
        name = "name 6",
        description = "description 6",
        thumbnail = ThumbnailDto("extension 6", "path 6")
    )

    val sevenCharacter = CharacterDto(
        id = 7,
        name = "name 7",
        description = "description 7",
        thumbnail = ThumbnailDto("extension 7", "path 7")
    )

    val eightCharacter = CharacterDto(
        id = 8,
        name = "name 8",
        description = "description 8",
        thumbnail = ThumbnailDto("extension 8", "path 8")
    )

    val nineCharacter = CharacterDto(
        id = 9,
        name = "name 9",
        description = "description 9",
        thumbnail = ThumbnailDto("extension 9", "path 9")
    )

    val tenCharacter = CharacterDto(
        id = 10,
        name = "name 10",
        description = "description 10",
        thumbnail = ThumbnailDto("extension 10", "path 10")
    )

    val listCharacter = listOf(oneCharacter, twoCharacter, characterWithoutThumbnail)
    val fullListCharacter = listOf(
        oneCharacter,
        characterWithoutThumbnail,
        twoCharacter,
        fourCharacter,
        fiveCharacter,
        sixCharacter,
        sevenCharacter,
        eightCharacter,
        nineCharacter,
        tenCharacter,
        oneCharacter,
        characterWithoutThumbnail,
        twoCharacter,
        fourCharacter,
        fiveCharacter,
        sixCharacter,
        sevenCharacter,
        eightCharacter,
        nineCharacter,
        tenCharacter,
        oneCharacter,
        characterWithoutThumbnail,
        twoCharacter,
        fourCharacter,
        fiveCharacter,
        sixCharacter,
        sevenCharacter,
        eightCharacter,
        nineCharacter,
        tenCharacter,
    )

    val dataWrapper = DataWrapper(
        DataContainer(
            total = 3,
            count = 1,
            results = listCharacter
        )
    )

    val dtoRemoteHandlerInitial = DtoRemoteRequestHandler()
    val dtoRemoteHandlerFirst = DtoRemoteRequestHandler(1, 1, 1, 3)

    val oneItem = ItemDto(
        id = 1,
        idCharacter = 1,
        title = "title 1",
        description = "description 1 ",
        thumbnail = ThumbnailDto("path 1", "extension 1 ")
    )

    val twoItem = ItemDto(
        id = 2,
        idCharacter = 1,
        title = "title 2",
        description = "description 2 ",
        thumbnail = ThumbnailDto("path 2", "extension 2 ")
    )


    val itemWithoutThumbnail = ItemDto(
        id = 3,
        idCharacter = 1,
        title = "title 3",
        description = "description 3 ",
        thumbnail = ThumbnailDto("image_not_valid", "jpg")
    )

    val listItem = listOf(oneItem, twoItem, itemWithoutThumbnail)

    val dataWrapperItem = DataWrapper(
        DataContainer(
            total = 3,
            count = 1,
            results = listItem
        )
    )
}