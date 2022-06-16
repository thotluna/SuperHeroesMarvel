package ve.com.teeac.mynewapplication.presentations.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ve.com.teeac.mynewapplication.presentations.character_detail.CharacterDetailScreen
import ve.com.teeac.mynewapplication.presentations.characteres.CharactersScreen

@Composable
fun AppGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Characters.route,
    ){
        composable(DestinationScreen.Characters.route) {
            CharactersScreen(
                modifier = modifier,
                goCharacterDetails = {
                    navController.navigate(
                        DestinationScreen.CharacterDetail.createRoute(it)
                    )
                }
            )
        }
        composable(DestinationScreen.CharacterDetail.route + "?id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    nullable = false
                }
            )

        ) {
            CharacterDetailScreen(
                modifier = modifier
            )
        }

    }

}

