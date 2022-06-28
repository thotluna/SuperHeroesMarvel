package ve.com.teeac.mynewapplication.core.presentations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBar(
    title: String,
    isLoading: Boolean,
    back: ()->Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {  back() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
            )
        }
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        if (isLoading) {
            Spacer(modifier = Modifier.width(36.dp))
            LoadingAnimation(
                circleSize = 10.dp,
                travelDistance = 10.dp,
                spaceBetween = 6.dp,
                circleColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}