package ve.com.teeac.mynewapplication.presentations.navigations

sealed class DestinationScreen(val route: String){

    object Characters : DestinationScreen("characters_screen")

    object CharacterDetail : DestinationScreen("character_detail_screen"){
        fun createRoute(id: Int = -1): String {
            return route +"?id=${id}"
        }
    }
}