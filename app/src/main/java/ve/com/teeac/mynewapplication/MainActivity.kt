package ve.com.teeac.mynewapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ve.com.teeac.mynewapplication.presentations.characteres.CharactersScreen
import ve.com.teeac.mynewapplication.presentations.navigations.AppGraph
import ve.com.teeac.mynewapplication.ui.theme.MyNewApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MyNewApplicationTheme {
                    AppGraph()
                }
            }
        }
    }
}

