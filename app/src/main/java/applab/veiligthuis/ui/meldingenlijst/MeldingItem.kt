package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.domain.model.melding.MeldingStatus
import applab.veiligthuis.ui.common.MeldingStatusDisplay
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun MeldingItem(
    onCardClick: () -> Unit,
    datum: String,
    meldingStatus: MeldingStatus = MeldingStatus.ONBEHANDELD,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 0.dp)
            .requiredHeight(80.dp)
            .clickable(onClick = onCardClick)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = datum, fontWeight = FontWeight.Bold)
                Text(text = description ,fontSize = 12.sp, maxLines = 3)
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                MeldingStatusDisplay(meldingStatus = meldingStatus)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingItem(){
    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    AppTheme {
        MeldingItem(onCardClick = {}, datum = "1-11-1111 11:11", description = description)
    }

}

