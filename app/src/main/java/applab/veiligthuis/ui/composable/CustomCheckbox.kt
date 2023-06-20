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


data class CheckBoxItemState(
    val id: Int = 0,
    val checked: Boolean = false,
    val text: String = "",
)

@Composable
fun CheckboxItem(
    item: CheckBoxItemState,
    onChecked:(Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)
        .height(25.dp)
        .toggleable(
            value = item.checked,
            onValueChange = {toggled -> onChecked(toggled) },
            role = Role.Checkbox
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = item.checked, onCheckedChange = null, colors = CheckboxDefaults.colors(checkedColor = Color.Black))
            Text(text = item.text, modifier = modifier.padding(start = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxItem() {
    AppTheme {
        CheckboxItem(item = CheckBoxItemState(1,false,"Ongecategoriseerd"), onChecked = {})
    }
}


@Composable
fun CheckBoxList(
    items: List<CheckBoxItemState>,
    onChecked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column( modifier = modifier) {
        items.forEach { item ->
            CheckboxItem(item = item, onChecked = {
                onChecked(item.id, it)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckBoxList() {
    AppTheme {
        CheckBoxList(items = listOf(CheckBoxItemState(1, false,"Ongecategoriseerd"),CheckBoxItemState(2,false,"Stalking")), { a, b ->})
    }
}
