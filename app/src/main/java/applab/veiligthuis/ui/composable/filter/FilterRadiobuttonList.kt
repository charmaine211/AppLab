package applab.veiligthuis.ui.composable.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applab.veiligthuis.ui.composable.RadioButtonList
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun FilterRadioButtonList(
    headerText: String,
    items: List<String>,
    selected: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 18.dp)) {
        Divider(modifier = Modifier.padding(bottom = 18.dp))
        Text(
            text = headerText,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.h1
        )
        RadioButtonList(
            items = items,
            selectedItem = selected,
            onItemSelected = onSelected,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterRadioButtonList() {
    AppTheme {
        FilterRadioButtonList(
            headerText = "Datum",
            items = listOf("Vandaag", "Deze Week", "Deze Maand", "Afgelopen Maand"),
            selected = "Vandaag",
            {})
    }
}