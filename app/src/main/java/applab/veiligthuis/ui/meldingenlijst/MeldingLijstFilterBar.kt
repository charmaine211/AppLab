package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.theme.filter_grey
import applab.veiligthuis.ui.common.listDialogSpinner

@Composable
fun filterButtonsBar (
    filterInkomendSelected: Boolean,
    expandedFilter: Boolean,
    onClickExpandFilter: () -> Unit,
    onClickFilterChange: () -> Unit,
    updateSelectedLocatie: (String) -> Unit,
    selectedLocatie: String?,
    resetFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow))) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(11.dp, 4.dp, 11.dp, 0.dp)
        ){
            meldingFilterButtons(inkomendSelected = filterInkomendSelected, onClick = onClickFilterChange)
            Button(
                onClick = onClickExpandFilter,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                modifier = modifier.height(30.dp)
            ){
                Text(
                    text = "Filter",
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
        if(expandedFilter){
            Column(
                modifier = Modifier
                    .padding(11.dp)
            ) {
                listDialogSpinner(
                    textValueDefault = "Selecteer een locatie",
                    textValueState = selectedLocatie,
                    label = "Locatie",
                    items = listOf("Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam","Amsterdam", "Groningen", "Eindhoven", "Rotterdam"),
                    itemSelectedOnClick = updateSelectedLocatie,
                    modifier = Modifier.padding(bottom=4.dp)
                )
                Button(
                    onClick =  resetFilter,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                    modifier = modifier.height(30.dp)
                ){
                    Text(
                        text = "Reset filter",
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }

            }
        }
    }
}


@Composable
private fun meldingFilterButtons(
    inkomendSelected: Boolean,
    onClick: () -> Unit
){
    Row(
    ){
        meldingFilterButton("Inkomend", inkomendSelected, onClick )
        meldingFilterButton("Afgesloten", !inkomendSelected, onClick )
    }
}

@Composable
private fun meldingFilterButton(
    buttonText: String,
    enabledButton: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(45),
        colors = ButtonDefaults.buttonColors(disabledBackgroundColor = filter_blue, backgroundColor = filter_grey),
        modifier = Modifier.minimumInteractiveComponentSize(),
        enabled = !enabledButton
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewExpandedBar() {
    filterButtonsBar(true, true, {}, {}, {}, null, { })
}

@Preview(showBackground = true)
@Composable
fun previewUnexpandedBar() {
    filterButtonsBar(true, false, {}, {}, {}, null, { })
}