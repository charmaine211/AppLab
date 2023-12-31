package applab.veiligthuis.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun CheckboxItem(
    displayText: String,
    isChecked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(25.dp)
            .toggleable(
                value = isChecked,
                onValueChange = { toggled -> onChecked(toggled) },
                role = Role.Checkbox
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(checkedColor = Color.Black)
            )
            Text(text = displayText, modifier = modifier.padding(start = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxItem() {
    AppTheme {
        CheckboxItem(displayText = "Ongecategoriseerd", isChecked = false, onChecked = {})
    }
}


@Composable
fun CheckBoxList(
    items: List<String>,
    checkedItems: List<String>,
    onChecked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        items.forEach { item ->
            CheckboxItem(displayText = item, isChecked = checkedItems.contains(item), onChecked = {
                onChecked(item)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxList() {
    AppTheme {
        CheckBoxList(
            items = listOf(
                "Ongecategoriseerd", "Stalking"
            ),
            checkedItems = listOf("Stalking"),
            onChecked = { _ -> })
    }
}
