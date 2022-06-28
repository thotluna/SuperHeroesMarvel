package ve.com.teeac.mynewapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ve.com.teeac.mynewapplication.core.presentations.TopAppBar
import ve.com.teeac.mynewapplication.presentations.navigations.AppGraph
import ve.com.teeac.mynewapplication.ui.theme.MyNewApplicationTheme

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                var title by rememberSaveable { mutableStateOf("") }
                var isLoading by rememberSaveable { mutableStateOf(false) }
                val navController = rememberNavController()
                MyNewApplicationTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = title,
                                isLoading = isLoading,
                                back = {navController.popBackStack()}
                            )
                        },
                    ) {
                        AppGraph(
                            modifier = Modifier.padding(it),
                            title = { string -> title = string },
                            isLoading = {boolean -> isLoading = boolean},
                            navController = navController
                        )
                    }
                }
            }
        }
    }


}

