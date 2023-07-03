package applab.veiligthuis.ui.composable.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.theme.veilig_thuis_oranje


@Composable
fun FilterHeader(
    filterCount: Int,
    headerTitle: String,
    closeFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = veilig_thuis_oranje)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(text = filterCount.toString(), color = Color.White, fontSize = 18.sp)
            }
            Text(text = headerTitle, modifier = Modifier.padding(start = 20.dp), fontSize = 18.sp)
        }
        IconButton(onClick = closeFilter) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Sluit filter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    AppTheme {
        FilterHeader(filterCount = 1, headerTitle = "filters", closeFilter = {  })
    }

}