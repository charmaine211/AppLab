package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import applab.veiligthuis.domain.model.melding.InkomendeMelding
import applab.veiligthuis.domain.model.melding.Melding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun MeldingList(
    list: List<Melding?>,
    onCardClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = list) {
            melding ->
            if(melding != null){
            val datumParsed = LocalDateTime.ofEpochSecond(melding.datum!!, 0, ZoneOffset.UTC)
                MeldingItem(
                    onCardClick = { onCardClick("inkomend", melding.key!!) },
                    datum = "" + datumParsed.dayOfMonth + "-" + datumParsed.monthValue + "-" + datumParsed.year + " " + datumParsed.hour + ":" + datumParsed.minute,
                    description = melding.beschrijving!!,
                    meldingStatus = melding.status!!
                )
            }
        }
    }
}

private fun parseDate(datum: String) {
    val parsed = LocalDateTime.parse(datum)
}

@Preview
@Composable
fun PreviewMeldingList() {
//    val list = List(30) { InkomendeMelding(datum = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) }
//    MeldingList(
//        list = list
//    )
}

