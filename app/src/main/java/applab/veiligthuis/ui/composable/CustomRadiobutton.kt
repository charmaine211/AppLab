package applab.veiligthuis.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun RadioButtonList(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedItem: Any? = null,
) {
    Column(
        modifier = modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .selectableGroup()
    ) {
        items.forEach { item ->
            RadioButtonItem(
                text = item,
                selected = item == selectedItem,
                onSelected = { onItemSelected(item) })
        }
    }
}


@Composable
fun RadioButtonItem(
    text: String,
    selected: Boolean,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)
            .height(25.dp)
            .selectable(
                selected = selected,
                onClick = { onSelected(text) },
                role = Role.RadioButton
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
            Text(text = text, modifier = Modifier.padding(start = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRadioButtonItem() {
    AppTheme {
        RadioButtonItem("Vandaag", false, {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRadioButtonList() {
    AppTheme {
        RadioButtonList(
            items = listOf("Vandaag", "Deze Week", "Deze Maand"),
            selectedItem = null,
            onItemSelected = {}
        )
    }
}