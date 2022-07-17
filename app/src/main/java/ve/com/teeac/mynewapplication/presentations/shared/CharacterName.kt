package ve.com.teeac.mynewapplication.presentations.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ve.com.teeac.mynewapplication.ui.theme.GrayMarvel

@Composable
fun CharacterName(
    characterName: String,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .then(modifier)
    ) {
        val names = characterName.split("(", ")")
        Text(
            text = names[0].uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)

        )
        if (names.size > 1) {
            Text(
                text = names[1].uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = GrayMarvel,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart)

            )
        }
    }
}
