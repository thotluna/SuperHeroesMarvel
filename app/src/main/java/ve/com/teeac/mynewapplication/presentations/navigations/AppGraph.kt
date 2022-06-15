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
                        DestinationScreen.CharacterDetail.withArgs(it.toString())
                    )
                }
            )
        }
        composable(DestinationScreen.CharacterDetail.route + "/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                    nullable = false
                }
            )

        ) { backStackEntry ->
            CharacterDetailScreen(
                modifier = modifier,
                id = backStackEntry.arguments!!.getString("id")!!.toInt(),
            )
        }

    }

}

