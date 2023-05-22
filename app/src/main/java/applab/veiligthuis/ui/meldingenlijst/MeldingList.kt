package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import applab.veiligthuis.domain.model.melding.Melding
import java.time.LocalDateTime

@Composable
fun MeldingList(
    list: List<Melding>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = list) {
            melding ->
            MeldingItem(
                datum = melding.datum.toString(),
                description = melding.description,
                meldingStatus = melding.status
            )
        }
    }
}

@Preview
@Composable
fun PreviewMeldingList() {
    val list = List(30) { Melding(datum = LocalDateTime.now()) }
    MeldingList(
        list = list
    )
}

