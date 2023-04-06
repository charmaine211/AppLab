package applab.veiligthuis.ui.meldingenlijst

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import applab.veiligthuis.model.Melding
import applab.veiligthuis.model.MeldingStatus
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MeldingBekijkenScreen(
    melding: Melding?,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler() {
        onBackButtonClicked
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
       item {
           MeldingBekijkenNavBar(onBackButtonClicked = onBackButtonClicked)
           MeldingBekijkenCard(melding = melding)
       }

    }
}

@Composable
private fun MeldingBekijkenCard(
    melding: Melding?,
    modifier: Modifier = Modifier
    ) {
    if (melding != null) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if(melding.datum != null){
                    Text(
                        text = melding.datum!!
                    )
                }
                if(melding.locatie != null){
                    Text(
                        text = melding.locatie!!
                    )
                }
                if(melding.status != null){
                    Text(
                        text = melding.status!!.status
                    )
                }
                if(melding.info != null){
                    Text(
                        text = melding.info!!
                    )
                }
            }
        }
    }
}

@Composable
private fun MeldingBekijkenNavBar(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
       modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onBackButtonClicked,
            shape = CircleShape
        ) {
            Text(
                text = "Terug"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun previewMeldingBekijkenCard() {
    MeldingBekijkenCard(melding = Melding(datum = "1-1-1111, 11:11", locatie = "Nederland", info = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ", status= MeldingStatus.ONBEHANDELD, anoniem = true))
}

@Preview(showBackground = true)
@Composable
private fun previewMeldingBekijkenNavBar() {
    MeldingBekijkenNavBar(onBackButtonClicked = { /*TODO*/ })
}

@Preview(showBackground = true)
@Composable
private fun previewMeldingBekijkenScreen() {
    MeldingBekijkenScreen(melding = Melding(datum = "1-1-1111, 11:11", locatie = "Nederland", info = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. ", status= MeldingStatus.ONBEHANDELD, anoniem = true), onBackButtonClicked = { /*TODO*/ })
}