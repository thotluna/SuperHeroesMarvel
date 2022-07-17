package ve.com.teeac.mynewapplication.presentations.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ShowImageScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowImageViewModel = hiltViewModel()
) {
    val imageUrl = viewModel.urlImage
    Box(Modifier.fillMaxSize(1f)) {
        GlideImage(
            imageModel = imageUrl,
            modifier = Modifier
                .then(modifier),
            contentScale = ContentScale.FillWidth,
            loading = {
                Box(modifier = Modifier.matchParentSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            },
            failure = { Text(text = "image request failed.") }
        )
    }
}
