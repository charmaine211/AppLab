package applab.veiligthuis.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun <E> ListDialogSpinner(
    textValueDefault: String,
    textValueState: String?,
    label: String,
    items: List<E>,
    itemSelectedOnClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var dialogOpen by remember { mutableStateOf(false) }
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        val textValue: TextFieldValue = if (textValueState == null) {
            TextFieldValue(text = textValueDefault)
        } else {
            TextFieldValue(text = textValueState)
        }
        OutlinedTextField(
            value = textValue,
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            onValueChange = { }
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clickable(enabled = true) {
                    dialogOpen = true
                },
            color = Color.Transparent
        ) {

        }
    }

    fun callBackCloseDialog(
        itemSelectedTextValue: String?
    ) {
        dialogOpen = false
        if (itemSelectedTextValue != null) {
            itemSelectedOnClick(itemSelectedTextValue)
        }
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            Surface(
                shape = RoundedCornerShape(12.dp)
            ) {
                LazyColumn {
                    items(items) { item ->
                        DialogListItem(
                            itemText = item.toString(),
                            onClickItem = { callBackCloseDialog(item.toString()) })
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogListItem(
    itemText: String,
    onClickItem: () -> Unit
) {
    Card(
        border = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable(onClick = { onClickItem() })
    ) {
        Text(
            text = itemText,
            modifier = Modifier
                .padding(4.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun PreviewDialogListItem() {
    DialogListItem("Amsterdam") {}
}


@Preview(showBackground = true)
@Composable
fun PreviewListDialogSpinner() {
    val locaties = listOf("Amsterdam", "Eindhoven", "Groningen", "Utrecht", "Rotterdam")
    ListDialogSpinner(
        textValueDefault = "Geen Locatie geselecteerd",
        textValueState = null,
        label = "Locaties",
        items = locaties,
        itemSelectedOnClick = {}
    )
}


