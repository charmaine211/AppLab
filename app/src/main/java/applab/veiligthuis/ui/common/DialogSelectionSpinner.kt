package applab.veiligthuis.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
fun<E> listDialogSpinner(
    textValueDefault: String,
    textValueState: String?,
    label: String,
    items: List<E>,
    itemSelectedOnClick: (String) -> Unit,
    modifier : Modifier = Modifier
) {
    var dialogOpen by remember { mutableStateOf(false) }
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        var textValue: TextFieldValue
        if(textValueState == null){
            textValue = TextFieldValue(text = textValueDefault)
        } else {
            textValue = TextFieldValue(text = textValueState)
        }
        OutlinedTextField(
            value = textValue,
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            onValueChange = { }
        )

        Surface(modifier = Modifier
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
    ){
        dialogOpen = false
        if (itemSelectedTextValue != null) {
            itemSelectedOnClick(itemSelectedTextValue)
        }
    }

    if(dialogOpen){
        Dialog(onDismissRequest = {dialogOpen = false}) {
            Surface(
                shape = RoundedCornerShape(12.dp)
            ){
                LazyColumn(){
                    items(items) {
                            item -> dialogListItem(itemText = item.toString(), onClickItem = { callBackCloseDialog(item.toString())} )
                    }
                }
            }
        }
    }
}

@Composable
private fun dialogListItem(
    itemText: String,
    onClickItem: () -> Unit
) {
    Card(
        border=null,
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
fun previewDialogListItem() {
    dialogListItem("Amsterdam", {})
}


@Preview(showBackground = true)
@Composable
fun previewListDialogSpinner() {
    var locaties = listOf("Amsterdam", "Eindhoven","Groningen", "Utrecht", "Rotterdam")
    listDialogSpinner(textValueDefault = "Geen Locatie geselecteerd", textValueState = null, label = "Locaties", items = locaties, {})
}


