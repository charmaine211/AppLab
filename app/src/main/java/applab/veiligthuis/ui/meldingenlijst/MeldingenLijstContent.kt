package applab.veiligthuis.ui.meldingenlijst

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.domain.model.MeldingData
import applab.veiligthuis.domain.model.MeldingStatus
import applab.veiligthuis.ui.theme.AppTheme


@Composable
fun meldingList(
    meldingen: List<MeldingData?>,
    onCardClick: (MeldingData) -> Unit,
    modifier: Modifier = Modifier
){
    Log.i("ACT", "Lijst aanmaken")
    LazyColumn() {
        items(meldingen){ melding ->
            if(melding != null) {
                meldingCard(melding, onCardClick = { onCardClick(melding)})
                Log.i("ACT", "Kaart gemaakt")
            }
        }
    }
}

@Composable
private fun meldingCard(
    meldingData: MeldingData,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(horizontal = 22.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, hoveredElevation = 10.dp),
    ) {
        Column(modifier = modifier.padding(10.dp)){
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                Column(modifier = modifier.fillMaxWidth(0.6F)){
                    if (meldingData.datum != null) {
                        Text(
                            text = meldingData.datum.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Anoniem",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    if (meldingData.beschrijving != null) {
                        Text(
                            text = meldingData.beschrijving,
                            fontSize = 12.sp,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)){
                    statusMelding(meldingData.status)
                }
            }
        }
    }
}

@Composable
private fun statusMelding(meldingStatus: MeldingStatus?){
    if (meldingStatus != null) {
        Text (
            text = meldingStatus.status,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .background(color = meldingStatus.color, shape = RoundedCornerShape(45))
                .padding(8.dp, 2.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingCard() {
    AppTheme {
        meldingCard(MeldingData(datum = null, plaatsnaam = "Nederland", beschrijving = "Lorem ipsum dolor sit amet", status= MeldingStatus.ONBEHANDELD), {})
    }
}
