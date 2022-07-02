package ve.com.teeac.mynewapplication.presentations.navigations

import androidx.compose.material3.ExperimentalMaterial3Api
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
import ve.com.teeac.mynewapplication.presentations.image.ShowImageScreen

@ExperimentalMaterial3Api
@Composable
fun AppGraph(
    modifier: Modifier = Modifier,
    title: (String) -> Unit,
    isLoading: (Boolean) -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Characters.route,
    ){
        composable(DestinationScreen.Characters.route) {
            CharactersScreen(
                modifier = modifier,
                goCharacterDetails = { id, name, path, ext ->
                    navController.navigate(
                        DestinationScreen.CharacterDetail.createRoute(id, name, path, ext)
                    )
                },
                title = { title(it) },
                isLoading = { isLoading(it) }
            )
        }
        composable(DestinationScreen.CharacterDetail.route + "?id={id}&name={name}&imageurl={imageurl}&extension={extension}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument(name = "name"){
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(name = "imageurl"){
                    type = NavType.StringType
                    nullable = false
                },
                navArgument(name = "extension"){
                    type = NavType.StringType
                    nullable = false
                }
            )

        ) {
            CharacterDetailScreen(
                title = { title(it) },
                isLoading = { isLoading(it) },
                modifier = modifier,
                navigateImage = { url ->
                    navController.navigate(
                        DestinationScreen.ShowImage.createRoute(url)
                    )
                }
            )
        }
        composable(DestinationScreen.ShowImage.route + "?imageurl={imageurl}",
            arguments = listOf(
                navArgument(name = "imageurl") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            ShowImageScreen(modifier = modifier)
        }


    }

}

