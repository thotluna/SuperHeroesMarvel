package ve.com.teeac.mynewapplication.presentations.navigations

sealed class DestinationScreen(val route: String){
    object Characters : DestinationScreen("characters_screen")
    object CharacterDetail : DestinationScreen("character_detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/")
                append(arg)
            }
        }
    }
}