package applab.veiligthuis.ui.composable.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun SelectedItem(
    strValue: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color.LightGray)
        ) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Verwijdert een geselecteerd item.",
                    tint = Color.White
                )
            }
            Text(
                text = strValue,
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedItem() {
    AppTheme {
        SelectedItem(strValue = "Amsterdam", {})
    }
}
