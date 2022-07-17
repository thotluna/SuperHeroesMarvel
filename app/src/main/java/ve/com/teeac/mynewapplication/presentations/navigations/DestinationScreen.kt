package ve.com.teeac.mynewapplication.presentations.navigations

sealed class DestinationScreen(val route: String) {

    object Characters : DestinationScreen("characters_screen")

    object CharacterDetail : DestinationScreen("character_detail_screen") {
        fun createRoute(id: Int = -1, name: String = "", imageurl: String = "", extension: String = ""): String {
            return route + "?id=$id&name=$name&imageurl=$imageurl&extension=$extension"
        }
    }

    object ShowImage : DestinationScreen("show_image_screen") {
        fun createRoute(imageurl: String): String {
            return route + "?imageurl=$imageurl"
        }
    }
}
